package com.xsx.ncd.ncd_manager.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.xsx.ncd.ncd_manager.entity.BaseEntity;
import com.xsx.ncd.ncd_manager.entity.TestData;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T extends BaseEntity> {
    protected Class<T> clazz;

    protected Dao<T, Integer> dao;

    public BaseDao(Context context) {
        Class clazz = getClass();
        Type t = clazz.getGenericSuperclass();
        Type[] args = ((ParameterizedType) t).getActualTypeArguments();
        this.clazz = (Class<T>) args[0];

        try {

            dao = SqliteDataBaseHelper.getInstance(context).getDao(this.clazz);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(T t) throws SQLException {
        dao.create(t);
    }

    public void delete(T t) throws SQLException {
        dao.delete(t);
    }

    public void update(T t) throws SQLException {
        dao.update(t);
    }

    public T queryById(Integer id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<T> all() throws SQLException {
        return dao.queryForAll();
    }

    public List<T> queryByColumn(String columnName, Object columnValue) {
        try {
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq(columnName, columnValue);
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public QueryBuilder getDaoQueryBuilder(){
        return dao.queryBuilder();
    }

    public List<T> daoQueryBuilderQuery(QueryBuilder<T, Integer> queryBuilder) throws SQLException {
        return dao.query(queryBuilder.prepare());
    }

    public Page<T> queryAllByPage(long startPage, long pageSize) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDaoQueryBuilder();
        Page<T> page = new Page<>();

        page.setCurrentPageIndex(startPage);
        page.setTotalElements(queryBuilder.countOf());
        if(page.getTotalElements() % pageSize != 0)
            page.setTotalPages(queryBuilder.countOf() / pageSize + 1l);
        else
            page.setTotalPages(queryBuilder.countOf() / pageSize);

        queryBuilder.offset(startPage*pageSize);
        queryBuilder.limit(pageSize);

        List<T> testDataList = daoQueryBuilderQuery(queryBuilder);
        long startIndex = startPage*pageSize;
        for (T data : testDataList){
            data.setIndex(startIndex);
            startIndex++;
        }

        page.setContent(testDataList);

        return page;
    }
}

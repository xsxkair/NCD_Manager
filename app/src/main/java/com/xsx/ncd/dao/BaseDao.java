package com.xsx.ncd.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
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

    public void add(T t) {
        try {
            dao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(T t) {
        try {
            dao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T t) {
        try {
            dao.update(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> all() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
}

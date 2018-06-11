package com.xsx.ncd.ncd_manager.Dao;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.xsx.ncd.ncd_manager.entity.TestData;
import com.xsx.ncd.ncd_manager.entity.User;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


public class TestDataDao extends BaseDao<TestData> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");

    public TestDataDao(Context context) {
        super(context);
    }

    public Page<TestData> queryTestDataByPage(long startPage, long pageSize, String testTime, String testItem, String sampleId, Boolean isChecked) throws SQLException {
        QueryBuilder<TestData, Integer> queryBuilder = getDaoQueryBuilder();
        Where<TestData, Integer> where = queryBuilder.where();
        Page<TestData> page = new Page<>();
        boolean iseq = false;

        if (isChecked != null) {
            where.eq("check", isChecked);
            iseq = true;
        }

        if(testItem != null)
        {
            if(iseq)
                where.and().eq("item", testItem);
            else{
                where.eq("item", testItem);
                iseq = true;
            }
        }

        if(sampleId != null)
        {
            if(iseq)
                where.and().like("sampleid", sampleId);
            else{
                where.like("sampleid", sampleId);
                iseq = true;
            }
        }

        if(testTime != null)
        {
            Timestamp begin = Timestamp.valueOf(String.format("%s 00:00:00.000", testTime));

            Timestamp end = Timestamp.valueOf(String.format("%s 23:59:59.999", testTime));

            if(iseq)
                where.and().between("testtime", begin, end);
            else{
                where.between("testtime", begin, end);
                iseq = true;
            }
        }

        page.setCurrentPageIndex(startPage);
        page.setTotalElements(queryBuilder.countOf());
        if(page.getTotalElements() % pageSize != 0)
            page.setTotalPages(queryBuilder.countOf() / pageSize + 1l);
        else
            page.setTotalPages(queryBuilder.countOf() / pageSize);

        queryBuilder.offset(startPage*pageSize);
        queryBuilder.limit(pageSize);

        List<TestData> testDataList = daoQueryBuilderQuery(queryBuilder);
        long startIndex = startPage*pageSize;
        for (TestData testData : testDataList){
            testData.setIndex(startIndex);
            startIndex++;
        }

        page.setContent(testDataList);

        return page;
    }
}

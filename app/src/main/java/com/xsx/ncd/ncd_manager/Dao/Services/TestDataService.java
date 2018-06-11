package com.xsx.ncd.ncd_manager.Dao.Services;

import android.content.Context;

import com.xsx.ncd.ncd_manager.Dao.Page;
import com.xsx.ncd.ncd_manager.Dao.TestDataDao;
import com.xsx.ncd.ncd_manager.entity.TestData;
import com.xsx.ncd.ncd_manager.entity.User;

import java.sql.Timestamp;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TestDataService {

    private TestDataDao testDataDao = null;

    public TestDataService(Context context){
        testDataDao = new TestDataDao(context);
    }

    public Observable<Boolean> saveNewTestDataService(TestData testData){
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                testDataDao.add(testData);

                emitter.onNext(true);
                emitter.onComplete();
            }
        });

        return observable;
    }

    public Observable<List<TestData>> queryAllTestDataService(){
        Observable<List<TestData>> observable = Observable.create(new ObservableOnSubscribe<List<TestData>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TestData>> emitter) throws Exception {

                List<TestData> testDataList = testDataDao.all();

                emitter.onNext(testDataList);
                emitter.onComplete();
            }
        });

        return observable;
    }


    public Observable<Page<TestData>> queryTestDataByPageService(long startPage, long pageSize, String testTime, String testItem, String sampleId, Boolean isChecked){
        Observable<Page<TestData>> observable = Observable.create(new ObservableOnSubscribe<Page<TestData>>() {
            @Override
            public void subscribe(ObservableEmitter<Page<TestData>> emitter) throws Exception {
                Page<TestData> testDataPage = null;
                if(testTime == null && testItem == null && sampleId == null && isChecked == null)
                    testDataPage = testDataDao.queryAllByPage(startPage, pageSize);
                else
                    testDataPage = testDataDao.queryTestDataByPage(startPage, pageSize, testTime, testItem, sampleId, isChecked);

                emitter.onNext(testDataPage);
                emitter.onComplete();
            }
        });

        return observable;
    }
}

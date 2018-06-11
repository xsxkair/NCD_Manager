package com.xsx.ncd.ncd_manager.Dao;


import android.content.Context;

import com.xsx.ncd.ncd_manager.Dao.Services.CardService;
import com.xsx.ncd.ncd_manager.Dao.Services.TestDataService;
import com.xsx.ncd.ncd_manager.Dao.Services.UserService;
import com.xsx.ncd.ncd_manager.entity.Card;
import com.xsx.ncd.ncd_manager.entity.TestData;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataBaseMethods {

    private UserService userService;
    private TestDataService testDataService;
    private CardService cardService;

    private DataBaseMethods(){

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final DataBaseMethods INSTANCE = new DataBaseMethods();
    }

    //获取单例
    public static DataBaseMethods getInstance(){
        return DataBaseMethods.SingletonHolder.INSTANCE;
    }

    public void DataBaseMethodsInit(Context context){

        userService = new UserService(context);
        testDataService = new TestDataService(context);
        cardService = new CardService(context);
    }

    public UserService getUserService() {
        return userService;
    }

    public TestDataService getTestDataService() {
        return testDataService;
    }

    public CardService getCardService() {
        return cardService;
    }


    /*
    *   methods
     */


    public void addNewUser(Observer<Boolean> resultObserver, User user){
        userService.addNewUserService(user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void deleteUser(Observer<Boolean> resultObserver, User user){
        userService.deleteUserService(user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void updateUser(Observer<Boolean> resultObserver, User user){
        userService.updateUserService(user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void queryAllUser(Observer<List<User>> resultObserver){
        userService.queryAllUserService()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void saveNewCardData(Observer<Boolean> resultObserver, Card card){
        cardService.saveNewCardService(card)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void queryCardById(Observer<Card> resultObserver, int id){
        cardService.queryCardService(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void saveNewTestData(Observer<Boolean> resultObserver, TestData testData){
        testDataService.saveNewTestDataService(testData)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void queryAllTestData(Observer<List<TestData>> resultObserver){
        testDataService.queryAllTestDataService()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }

    public void queryTestDataByPage(Observer<Page<TestData>> resultObserver, long startPage, long pageSize, String testTime, String testItem, String sampleId, Boolean isChecked){
        testDataService.queryTestDataByPageService(startPage, pageSize, testTime, testItem, sampleId, isChecked)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultObserver);
    }
}

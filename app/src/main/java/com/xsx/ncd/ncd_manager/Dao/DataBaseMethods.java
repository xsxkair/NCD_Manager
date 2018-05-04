package com.xsx.ncd.ncd_manager.Dao;


import android.content.Context;
import android.util.Log;

import com.xsx.ncd.ncd_manager.Dao.Services.UserService;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialEntity;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialResultFunction;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataBaseMethods {

    UserService userService;

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
    }

    public void addNewUser(Observer<Boolean> resultObserver, User user){
        userService.addNewUserService(user)
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
}

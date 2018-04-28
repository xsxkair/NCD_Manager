package com.xsx.ncd.ncd_manager.SerialDriver;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.ncd.ncd_manager.Http.HttpMethods;
import com.xsx.ncd.ncd_manager.Http.HttpResultFunction;
import com.xsx.ncd.ncd_manager.Http.HttpServices.UserService;
import com.xsx.ncd.ncd_manager.Http.HttpUrlDefine;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SerialMethods {

    private int serialDeviceFile = -1;

    private SerialService serialService;

    private SerialMethods(){

        serialService = new SerialService();

        //open serial
        this.openSerial();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final SerialMethods INSTANCE = new SerialMethods();
    }

    //获取单例
    public static SerialMethods getInstance(){
        return SerialMethods.SingletonHolder.INSTANCE;
    }

    private void openSerial(){
        serialDeviceFile = HardwareControler.openSerialPort(SerialDefine.SERIAL_FILE_NAME, SerialDefine.SERIAL_BAUD, 8, 1);
    }

    public boolean isSerialOpened(){
        if(serialDeviceFile > 0)
            return  true;
        else
            return  false;
    }

    //user service
   /* public void login(Observer<User> userObserver, String account, String password){
        userService.loginService2(account, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(userObserver);
    }*/
    public void checkControlBordIsReady(Observer<Boolean> bordStatusObserver){
        serialService.checkControlBordIsReady(serialDeviceFile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<SerialEntity, String>() {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bordStatusObserver);
    }
}

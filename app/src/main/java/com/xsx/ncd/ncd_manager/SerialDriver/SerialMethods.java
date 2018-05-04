package com.xsx.ncd.ncd_manager.SerialDriver;

import android.util.Log;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

    //services
    public void checkControlBordIsReady(Observer<Boolean> bordStatusObserver){
        serialService.checkControlBordIsReady(serialDeviceFile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new SerialResultFunction<SerialEntity, Boolean>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bordStatusObserver);
    }

    //true 01 03 80 01 00 04 74 72 75 65 49
    //false 01 03 80 01 00 05 66 61 6c 73 65 95
}

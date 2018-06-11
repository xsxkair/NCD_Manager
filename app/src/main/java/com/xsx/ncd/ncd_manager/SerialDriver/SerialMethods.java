package com.xsx.ncd.ncd_manager.SerialDriver;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.ncd.ncd_manager.SerialDriver.DeviceSerial.DeviceSerialDefine;
import com.xsx.ncd.ncd_manager.SerialDriver.DeviceSerial.DeviceSerialEntity;
import com.xsx.ncd.ncd_manager.SerialDriver.DeviceSerial.DeviceSerialResultFunction;
import com.xsx.ncd.ncd_manager.SerialDriver.DeviceSerial.DeviceSerialService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SerialMethods {



    private DeviceSerialService deviceSerialService;

    private SerialMethods(){

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final SerialMethods INSTANCE = new SerialMethods();
    }

    //获取单例
    public static SerialMethods getInstance(){
        return SerialMethods.SingletonHolder.INSTANCE;
    }

    public void serialMethodInit() throws Exception {
        deviceSerialService = new DeviceSerialService();
        deviceSerialService.DeviceSerialInit();
    }

    public DeviceSerialService getDeviceSerialService() {
        return deviceSerialService;
    }


//true 01 03 80 01 00 04 74 72 75 65 49
    //false 01 03 80 01 00 05 66 61 6c 73 65 95
}

package com.xsx.ncd.ncd_manager.SerialDriver.GPRSSerial;

import android.util.Log;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.ncd.ncd_manager.Tools.CheckSum;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GprsSerialService {

    private int serialDeviceFile = -1;
    private byte[] recvBuf = new byte[GprsSerialDefine.SERIAL_READBUF_LEN];

    public void GprsSerialInit() throws Exception {
        openSerial();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //hardware control mathods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    打开与控制板的通信串口
     */
    public void openSerial() throws Exception {

        serialDeviceFile = HardwareControler.openSerialPort(GprsSerialDefine.SERIAL_FILE_NAME, GprsSerialDefine.SERIAL_BAUD, 8, 1);

        if(serialDeviceFile < 0)
            throw new Exception("open gprs Serial Fail");
    }

    /*
    读取串口状态
     */
    public boolean isSerialOpened(){
        if(serialDeviceFile > 0)
            return  true;
        else
            return  false;
    }

    /*
     *   serialRequest -- 请求数据
     *   return : 通信失败 返回null
     */
    private String serialCommunicationFunction(String serialRequest) {
        int errorCnt = 5;
        int readDataSize = 0;

        while (errorCnt > 0){

            if (HardwareControler.write(serialDeviceFile, serialRequest.getBytes()) > 0){

                if(HardwareControler.select(serialDeviceFile, 0, GprsSerialDefine.SERIAL_READ_WAIT_TIME) > 0){

                    readDataSize = HardwareControler.read(serialDeviceFile, recvBuf, GprsSerialDefine.SERIAL_READBUF_LEN);

                    return new String(recvBuf, 0, readDataSize);
                }
            }

            errorCnt--;
        }

        return "there is no msg!";
    }

    public boolean checkGprsIsOn(){
        String respone = serialCommunicationFunction(GprsSerialDefine.GPRS_AT_CMD);
        Log.d("xsx", "gprs recv data : "+respone);
        return false;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //services
    ////////////////////////////////////////////////////////////////////////////////////////////////

}

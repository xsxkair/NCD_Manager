package com.xsx.ncd.ncd_manager.SerialDriver.DeviceSerial;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.ncd.ncd_manager.Tools.CheckSum;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeviceSerialService {

    private int serialDeviceFile = -1;
    private boolean serialIsBusy = false;
    private byte[] recvBuf = new byte[DeviceSerialDefine.SERIAL_READBUF_LEN];

    public void DeviceSerialInit() throws Exception {
        openSerial();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //hardware control mathods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    打开与控制板的通信串口
     */
    public void openSerial() throws Exception {

        serialDeviceFile = HardwareControler.openSerialPort(DeviceSerialDefine.SERIAL_FILE_NAME, DeviceSerialDefine.SERIAL_BAUD, 8, 1);

        if(serialDeviceFile < 0)
            throw new Exception("open device Serial Fail");
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
    private DeviceSerialEntity serialCommunicationFunction(DeviceSerialEntity serialRequest, boolean wait) throws InterruptedException {
        int errorCnt = 5;
        int readDataSize = 0;
        int checkSum = 0;

        if(serialIsBusy){
            if(wait){
                while(serialIsBusy)
                    Thread.sleep(100);
            }
            else
                return null;
        }
        else
            serialIsBusy = true;

        while (errorCnt > 0){

            if (HardwareControler.write(serialDeviceFile, serialRequest.changeToByteForSend()) > 0){

                if(HardwareControler.select(serialDeviceFile, 0, DeviceSerialDefine.SERIAL_READ_WAIT_TIME) > 0){

                    readDataSize = HardwareControler.read(serialDeviceFile, recvBuf, DeviceSerialDefine.SERIAL_READBUF_LEN);

                    //接收数据必须大于7字节才是正确的
                    if(readDataSize > 7)
                    {
                        checkSum = CheckSum.checkSum(recvBuf, readDataSize-1);

                        if(checkSum == recvBuf[readDataSize - 1])
                        {
                            serialIsBusy = false;
                            return DeviceSerialEntity.build(recvBuf, readDataSize);
                        }
                    }
                }
            }

            errorCnt--;
        }

        serialIsBusy = false;

        return null;
    }

    public boolean checkControlBordIsReady() {

        try{
            DeviceSerialEntity serialRequest = new DeviceSerialEntity(DeviceSerialDefine.CONTROL_BORD_ADDR, DeviceSerialDefine.FUNCTION_READ,
                    DeviceSerialDefine.CHECK_CONTROL_BORD_IS_READY, (byte) 0x01);

            DeviceSerialEntity serialRespone = serialCommunicationFunction(serialRequest, false);

            DeviceSerialResultFunction<DeviceSerialEntity, Boolean> deviceSerialResultFunction = new DeviceSerialResultFunction<>();

            return deviceSerialResultFunction.apply(serialRespone);
        }
        catch (Exception e){

            return false;

        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //services
    ////////////////////////////////////////////////////////////////////////////////////////////////

}

package com.xsx.ncd.ncd_manager.SerialDriver;

import android.util.Log;

import com.friendlyarm.AndroidSDK.HardwareControler;
import com.xsx.ncd.ncd_manager.Tools.CheckSum;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;

public class SerialService {



    /*
    *   serialRequest -- 请求数据
    *   return : 未响应 返回null
     */
    private SerialEntity communicateWithControlBord(int serialDeviceFile, SerialEntity serialRequest){
        int errorCnt = 5;
        int readDataSize = 0;
        int checkSum = 0;
        byte[] recvBuf = new byte[SerialDefine.SERIAL_READBUF_LEN];
        while (errorCnt > 0){

            if (HardwareControler.write(serialDeviceFile, serialRequest.changeToByteForSend()) > 0){

                if(HardwareControler.select(serialDeviceFile, 0, SerialDefine.SERIAL_READ_WAIT_TIME) > 0){

                    readDataSize = HardwareControler.read(serialDeviceFile, recvBuf, SerialDefine.SERIAL_READBUF_LEN);

                    //接收数据必须大于7字节才是正确的
                    if(readDataSize > 7)
                    {
                        checkSum = CheckSum.checkSum(recvBuf, readDataSize-1);

                        if(checkSum == recvBuf[readDataSize - 1])
                            return SerialEntity.build(recvBuf, readDataSize);
                    }
                }
            }

            errorCnt--;
        }

        return null;
    }

    public Observable<SerialEntity> checkControlBordIsReady(int serialDeviceFile){
        Observable<SerialEntity> observable = Observable.create(new ObservableOnSubscribe<SerialEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SerialEntity> emitter) throws Exception {
                SerialEntity serialRequest = new SerialEntity(SerialDefine.CONTROL_BORD_ADDR, SerialDefine.FUNCTION_READ,
                        SerialDefine.CHECK_CONTROL_BORD_IS_READY, (byte) 0x01);

                SerialEntity serialRespone = communicateWithControlBord(serialDeviceFile, serialRequest);
                if (serialRespone != null)
                    emitter.onNext(serialRespone);
                else
                    emitter.onError(new Exception("发送失败！"));
            }
        });

        return observable;
    }
}

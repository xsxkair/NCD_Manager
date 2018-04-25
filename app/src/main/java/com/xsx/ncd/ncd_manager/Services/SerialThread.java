package com.xsx.ncd.ncd_manager.Services;

import android.util.Log;

import com.xsx.ncd.ncd_manager.EventBus.MessageEvent;

import de.greenrobot.event.EventBus;

public class SerialThread extends Thread {

    private boolean threadControlFlag = true;

    public SerialThread(){
        threadControlFlag = true;
    }

    public SerialThread(boolean status){
        threadControlFlag = status;
    }

    public void stopSerialThread(){
        threadControlFlag = false;
    }

    @Override
    public void run() {
        while(threadControlFlag){
            Log.d("xsx", "read data from serial");
            EventBus.getDefault().post(new MessageEvent("xsx"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

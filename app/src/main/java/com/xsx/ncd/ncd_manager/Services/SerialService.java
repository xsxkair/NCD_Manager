package com.xsx.ncd.ncd_manager.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.xsx.ncd.ncd_manager.R;

public class SerialService extends Service{

    private SerialThread serialThread;

    @Override
    public void onCreate() {
        super.onCreate();

        Notification.Builder localBuilder = new Notification.Builder(this);
        localBuilder.setAutoCancel(false);
        localBuilder.setOngoing(true); //禁止滑动删除
        localBuilder.setShowWhen(true);//右上角的时间显示
        localBuilder.setSmallIcon(R.mipmap.notification_l);
        localBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_l));
        localBuilder.setContentTitle("串口通信服务");
        localBuilder.setContentText("正在运行...");
        startForeground(2, localBuilder.build());

        serialThread = new SerialThread(true);
        serialThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        serialThread.stopSerialThread();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

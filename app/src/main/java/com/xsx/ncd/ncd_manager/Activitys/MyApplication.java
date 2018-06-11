package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Application;

import com.xsx.ncd.ncd_manager.Logger.LoggerUnits;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LoggerUnits.LoggerUnitsInit(this);
    }
}

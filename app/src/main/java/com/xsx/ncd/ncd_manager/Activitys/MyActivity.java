package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public abstract class MyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("activity state: ", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("activity state: ", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("activity state: ", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("activity state: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("activity state: ", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("activity state: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("activity state: ", "onDestroy");
    }
}

package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.xsx.ncd.ncd_manager.EventBus.MessageEvent;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.Services.SerialService;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class LunchActivity extends Activity {

    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ButterKnife.bind(this);

        viewFlipper.startFlipping();

        //创建启动Service的Intent,以及Intent属性
        final Intent intent = new Intent(this, SerialService.class);
        intent.setAction("com.xsx.ncd.ncd_manager.Services.SerialService");
        startService(intent);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(MessageEvent event){
        Log.d("xsx", "recv msg: "+event.getMsg());
    }

}

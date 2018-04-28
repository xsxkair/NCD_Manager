package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.xsx.ncd.ncd_manager.EventBus.MessageEvent;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialMethods;
import com.xsx.ncd.ncd_manager.Services.SerialService;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class LunchActivity extends Activity {

    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private Timer timer = new Timer();

    private Observer<Boolean> bordIsReadyStatusObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ButterKnife.bind(this);

        viewFlipper.startFlipping();

        //创建启动Service的Intent,以及Intent属性
        /*final Intent intent = new Intent(this, SerialService.class);
        intent.setAction("com.xsx.ncd.ncd_manager.Services.SerialService");
        startService(intent);*/

        bordIsReadyStatusObserver = new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                startActivity(new Intent(LunchActivity.this, MainActivity.class));
            }

            @Override
            public void onError(Throwable e) {
                Log.d("xsx", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("xsx", "end");
            }
        };

        timer.schedule(task, 0, 10000);
    }

    @Override
    protected void onPause() {
        timer.cancel();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private TimerTask task = new TimerTask() {
        public void run() {
            SerialMethods.getInstance().checkControlBordIsReady(bordIsReadyStatusObserver);
        }
    };
}

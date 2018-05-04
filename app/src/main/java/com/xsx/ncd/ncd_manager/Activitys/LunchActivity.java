package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        viewFlipper.setOnClickListener(v -> {
            startActivity(new Intent(LunchActivity.this, MainActivity.class));
        });

        bordIsReadyStatusObserver = new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if(aBoolean){
                    Log.d("xsx", "recv: true");
                    startActivity(new Intent(LunchActivity.this, MainActivity.class));
                }
                else
                    Log.d("xsx", "recv: false");

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

        DataBaseMethods.getInstance().DataBaseMethodsInit(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

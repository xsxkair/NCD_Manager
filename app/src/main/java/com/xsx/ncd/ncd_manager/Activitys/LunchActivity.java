package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.Logger.LoggerUnits;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialMethods;
import com.xsx.ncd.ncd_manager.Tools.SystemSetSaveBundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LunchActivity extends Activity {

    @BindView(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.systeminitTextView)
    TextView systeminitTextView;

    private Observer<String> systemInitObserver = null;
    private Observable<String> systemInitObservable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ButterKnife.bind(this);

        viewFlipper.startFlipping();
        viewFlipper.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(LunchActivity.this, MainActivity.class));
        });

        systemInitObserver = new Observer<String>() {
            Disposable disposable = null;

            @Override
            public void onSubscribe(Disposable disposable) {
                this.disposable = disposable;
            }

            @Override
            public void onNext(String str) {
                systeminitTextView.setText(str);
                LoggerUnits.info(str);
            }

            @Override
            public void onError(Throwable throwable) {
                systeminitTextView.setText(throwable.getMessage());
                LoggerUnits.error("系统初始化错误", throwable);
                this.disposable.dispose();
            }

            @Override
            public void onComplete() {

                LoggerUnits.info("系统初始化完成");

                startActivity(new Intent(LunchActivity.this, MainActivity.class));

                this.disposable.dispose();
            }
        };

        systemInitObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                DataBaseMethods.getInstance().DataBaseMethodsInit(LunchActivity.this);
                emitter.onNext(" 数据库初始化完成");
                Thread.sleep(100);

                SystemSetSaveBundle.getInstance().systemSetSaveBundleInit();

                SerialMethods.getInstance().serialMethodInit();
                emitter.onNext(" 通信端口初始化完成");
                Thread.sleep(100);

                while (SerialMethods.getInstance().getDeviceSerialService().checkControlBordIsReady() == false) {
                    emitter.onNext(" 运动系统准备中");
                    Thread.sleep(1000);
                }

                emitter.onNext(" 初始化完成");
                Thread.sleep(100);

                emitter.onComplete();
            }
        });

        systemInitObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(systemInitObserver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("xsx", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("xsx", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("xsx", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("xsx", "onDestroy");
    }

}

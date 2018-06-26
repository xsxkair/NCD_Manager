package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.Defines.EthernetConfig;
import com.xsx.ncd.ncd_manager.Defines.PublicStringDefine;
import com.xsx.ncd.ncd_manager.Logger.LoggerUnits;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.SerialDriver.SerialMethods;
import com.xsx.ncd.ncd_manager.Services.SerialService;
import com.xsx.ncd.ncd_manager.Tools.EthernetSet;
import com.xsx.ncd.ncd_manager.Tools.MySdcardSharedPreferences;

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

    private Disposable systemInitDisposable = null;
    private Observer<String> systemInitObserver = null;
    private Observable<String> systemInitObservable = null;
    private EthernetConfig ethernetConfig = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ButterKnife.bind(this);

        ethernetConfig = new EthernetConfig();

        viewFlipper.startFlipping();
        viewFlipper.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(LunchActivity.this, MainActivity.class));
        });

        systemInitObserver = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable disposable) {
                systemInitDisposable = disposable;
            }

            @Override
            public void onNext(String str) {
                systeminitTextView.setText(str);
                LoggerUnits.info(str);

                if(str.equals("read ethernet ok")){
                    try {
                        EthernetSet.setSystemEthernet(LunchActivity.this, ethernetConfig);
                    } catch (Exception e) {
                        Toast.makeText(LunchActivity.this, "设置系统ethernet失败", Toast.LENGTH_SHORT).show();
                        LoggerUnits.error("设置系统ethernet失败", e);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                systeminitTextView.setText(throwable.getMessage());
                LoggerUnits.error("系统初始化错误", throwable);

                if(systemInitDisposable != null && !systemInitDisposable.isDisposed())
                    systemInitDisposable.dispose();
            }

            @Override
            public void onComplete() {

                LoggerUnits.info("系统初始化完成");

                startActivity(new Intent(LunchActivity.this, MainActivity.class));

                if(systemInitDisposable != null && !systemInitDisposable.isDisposed())
                    systemInitDisposable.dispose();
            }
        };

        systemInitObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                DataBaseMethods.getInstance().DataBaseMethodsInit(LunchActivity.this);
                emitter.onNext(" 数据库初始化完成");
                Thread.sleep(100);

                MySdcardSharedPreferences.getInstance().mySdcardSharedPreferencesInit(LunchActivity.this);
                emitter.onNext(" 系统参数加载完成");
                Thread.sleep(100);

                ethernetConfig.setDhcp(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_DHCP_KEY, true));
                ethernetConfig.setIpv4(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_IPV4_KEY, PublicStringDefine.EMPTY_STRING));
                ethernetConfig.setDns(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_DNS_KEY, PublicStringDefine.EMPTY_STRING));
                ethernetConfig.setGateway(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_GATEWAY_KEY, PublicStringDefine.EMPTY_STRING));
                ethernetConfig.setNetmask(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_MASK_KEY, PublicStringDefine.EMPTY_STRING));
                emitter.onNext("read ethernet ok");

                /*Intent serialServiceIntent = new Intent(LunchActivity.this, SerialService.class);
                startService(serialServiceIntent);

                SerialMethods.getInstance().serialMethodInit();
                emitter.onNext(" 通信端口初始化完成");
                Thread.sleep(100);

                while(false == SerialMethods.getInstance().getGprsSerialService().checkGprsIsOn()){
                    emitter.onNext(" GPRS准备中");
                    Thread.sleep(1000);
                }

                while (SerialMethods.getInstance().getDeviceSerialService().checkControlBordIsReady() == false) {
                    emitter.onNext(" 运动系统准备中");
                    Thread.sleep(1000);
                }*/

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

        ethernetConfig = null;

        systemInitDisposable = null;
        systemInitObserver = null;
        systemInitObservable = null;
    }

}

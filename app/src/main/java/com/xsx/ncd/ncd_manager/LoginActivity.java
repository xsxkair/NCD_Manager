package com.xsx.ncd.ncd_manager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xsx.ncd.entity.User;
import com.xsx.ncd.services.UserService;
import com.xsxkair.perportylibrary.Perportys.StringPerporty;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    @BindView(R.id.account_edittext)
    EditText accountEdittext;
    @BindView(R.id.password_edittext)
    EditText passwordEdittext;
    @BindView(R.id.login_rememberpassword)
    CheckBox loginRememberpassword;
    @BindView(R.id.login_autologin)
    CheckBox loginAutologin;
    @BindView(R.id.submite_button)
    Button submiteButton;
    @BindView(R.id.info_textview)
    TextView infoTextview;

    private User user;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        accountEdittext.setEnabled(true);
        passwordEdittext.setEnabled(false);
        submiteButton.setEnabled(true);
        accountEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (accountEdittext.getText().length() > 0)
                    passwordEdittext.setEnabled(true);
                else
                    passwordEdittext.setEnabled(false);
            }
        });

        passwordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (passwordEdittext.getText().length() > 0)
                    submiteButton.setEnabled(true);
                else
                    submiteButton.setEnabled(false);
            }
        });
        passwordEdittext.setOnEditorActionListener((v, actionid, event)->{
            switch (actionid){
                case EditorInfo.IME_ACTION_DONE:
                    doLoginActionInRetrofitAndRxjava();
                    break;

                default: break;
            }
            return true;
        });
    }

    @OnClick({R.id.login_rememberpassword, R.id.login_autologin, R.id.submite_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_rememberpassword:
                Toast.makeText(this, "记住密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_autologin:
                Toast.makeText(this, "自动登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.submite_button:
                doLoginActionInRetrofitAndRxjava();
                break;
        }
    }

    private void doLoginActionInRetrofit(){
        String baseUrl = "http://172.20.10.2:8080/NCD_Server/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.loginService("xsx", "xsx127");
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("xsx", response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("xsx", t.getMessage());
            }
        });

    }

    private void doLoginActionInRetrofitAndRxjava() {
        String baseUrl = "http://116.62.108.201:8080/NCD_Server/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        userService.loginService2("xsx", "xsx127")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Log.i("xsx", "save user: "+user.getName());
                    }
                })
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Log.i("xsx", "save user2: "+user.getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.i("xsx", user.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            infoTextview.setText(msg.obj.toString());
            Log.i("xsx", (String) msg.obj);
            switch (msg.what) {
                case 1:

                    break;
            }

            super.handleMessage(msg);
        }
    };
}

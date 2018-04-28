package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xsx.ncd.ncd_manager.EventBus.MessageEvent;
import com.xsx.ncd.ncd_manager.Http.HttpMethods;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.User;
import com.xsx.ncd.ncd_manager.Http.HttpServices.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
    Observer<String> userObserver;

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

//        EventBus.getDefault().register(this);

        userObserver = new Observer<String>(){

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String user) {
                Log.d("xsx", user);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("xsx", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onDestroy() {
     //   EventBus.getDefault().unregister(this);
        super.onDestroy();
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
        HttpMethods.getInstance().login(userObserver, accountEdittext.getText().toString(), passwordEdittext.getText().toString());
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

package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.xsx.ncd.ncd_manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends Activity {

    @BindView(R.id.userManagerLinearLayout)
    LinearLayout userManagerLinearLayout;
    @BindView(R.id.dataQueryLinerLayout)
    LinearLayout dataQueryLinerLayout;
    @BindView(R.id.deviceInfoLinerLayout)
    LinearLayout deviceInfoLinerLayout;
    @BindView(R.id.netSetLinerLayout)
    LinearLayout netSetLinerLayout;
    @BindView(R.id.otherSetLinerLayout)
    LinearLayout otherSetLinerLayout;
    @BindView(R.id.aboutUsLinerLayout)
    LinearLayout aboutUsLinerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);


        deviceInfoLinerLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, DeviceInfoActivity.class));
        });

        userManagerLinearLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, UserManagementActivity.class));
        });

        dataQueryLinerLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, DataQueryActivity.class));
        });

        aboutUsLinerLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
        });
    }
}

package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.xsx.ncd.ncd_manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends Activity {

    @BindView(R.id.userManagerLinearLayout)
    LinearLayout userManagerLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        userManagerLinearLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, UserManagementActivity.class));
        });

    }
}

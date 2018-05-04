package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.startTestButton)
    Button startTestButton;
    @BindView(R.id.queryDataButton)
    Button queryDataButton;
    @BindView(R.id.setImageView)
    ImageView setImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setImageView.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        });

    }
}

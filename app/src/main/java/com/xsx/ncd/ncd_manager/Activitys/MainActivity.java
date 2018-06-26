package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

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

        queryDataButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DataQueryActivity.class));
        });

        startTestButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SelectUserActivity.class));
        });

        hideVirtualKey();

        getScreenWidth();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    /** * 隐藏Android底部的虚拟按键 */
    private void hideVirtualKey(){
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
    }

    public void getScreenWidth() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        Log.d("xsx",String.format("( %d, %d)", dm.widthPixels, dm.heightPixels));
    }

}

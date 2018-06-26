package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.LinearLayout;

import com.xsx.ncd.ncd_manager.Activitys.Dialogs.SelectDialog;
import com.xsx.ncd.ncd_manager.Activitys.Listeners.DialogItemSelectListener;
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

    private String[] netSetMenuItems = null;

    private SelectDialog selectDialog = null;
    private static final String selectDialogTag = "net_set_select_dialog";
    private FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        netSetMenuItems = getResources().getStringArray(R.array.net_set_menu_items);

        selectDialog = SelectDialog.newInstance(this);
        selectDialog.addDialogItemSelectListener(new DialogItemSelectListener<String>() {
            @Override
            public void onItemSelectted(String s, String userValue) {

                if(s.equals(netSetMenuItems[0]))
                    startActivity(new Intent(SettingActivity.this, WifiSetActivity.class));
                else if(s.equals(netSetMenuItems[1]))
                    startActivity(new Intent(SettingActivity.this, EthernetSettingActivity.class));
                else if(s.equals(netSetMenuItems[2]))
                    startActivity(new Intent(SettingActivity.this, ServerSetActivity.class));
            }
        });
        fragmentManager = getFragmentManager();

        deviceInfoLinerLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, DeviceInfoActivity.class));
        });

        userManagerLinearLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, UserManagementActivity.class));
        });

        netSetLinerLayout.setOnClickListener(v -> {
            selectDialog.showDialog(fragmentManager, SettingActivity.selectDialogTag,
                    getResources().getString(R.string.setting_menu_net_select_dialog_title), netSetMenuItems, null);
        });

        dataQueryLinerLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, DataQueryActivity.class));
        });

        aboutUsLinerLayout.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        netSetMenuItems = null;

        selectDialog = null;
    }
}

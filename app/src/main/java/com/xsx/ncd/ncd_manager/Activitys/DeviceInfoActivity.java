package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.Activitys.Dialogs.ComfirmDialog;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.Tools.SystemSetSaveBundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceInfoActivity extends FragmentActivity {

    @BindView(R.id.deviceInfoSetImageView)
    ImageView deviceInfoSetImageView;
    @BindView(R.id.deviceIdValueTextView)
    TextView deviceIdValueTextView;
    @BindView(R.id.deviceAddrEditText)
    EditText deviceAddrEditText;
    @BindView(R.id.deviceUserEditText)
    EditText deviceUserEditText;
    @BindView(R.id.devicePhoneEditText)
    EditText devicePhoneEditText;
    @BindView(R.id.modifyDeviceInfoSwitch)
    Switch modifyDeviceInfoSwitch;

    private ComfirmDialog comfirmDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);

        deviceAddrEditText.setEnabled(false);
        deviceUserEditText.setEnabled(false);
        devicePhoneEditText.setEnabled(false);

        modifyDeviceInfoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    deviceAddrEditText.setEnabled(true);
                    deviceUserEditText.setEnabled(true);
                    devicePhoneEditText.setEnabled(true);
                }
                else
                {
                    deviceAddrEditText.setEnabled(false);
                    deviceUserEditText.setEnabled(false);
                    devicePhoneEditText.setEnabled(false);

                    saveAndUpdateDeviceInfo();
                }
            }
        });

        comfirmDialog = new ComfirmDialog();
    }

    private void saveAndUpdateDeviceInfo(){
        try {
            SystemSetSaveBundle.getInstance().putString(getResources().getString(R.string.system_device_addr_key), deviceAddrEditText.getText().toString())
                    .putString(getResources().getString(R.string.system_device_user_key), deviceUserEditText.getText().toString())
                    .putString(getResources().getString(R.string.system_device_phone_key), devicePhoneEditText.getText().toString()).commit();
            comfirmDialog.showComfirmDialog(getFragmentManager(), "xsx", "信息", "成功");
        }catch (Exception e){
            comfirmDialog.showComfirmDialog(getFragmentManager(), "xsx", "告警", "失败");
        }

    }

}

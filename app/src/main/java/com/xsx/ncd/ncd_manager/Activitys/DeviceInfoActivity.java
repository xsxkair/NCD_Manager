package com.xsx.ncd.ncd_manager.Activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.Activitys.Dialogs.ComfirmDialog;
import com.xsx.ncd.ncd_manager.Activitys.Dialogs.InputDialog;
import com.xsx.ncd.ncd_manager.Logger.LoggerUnits;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.Tools.MySdcardSharedPreferences;
import com.xsx.ncd.ncd_manager.Defines.PublicStringDefine;

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

    private String superPassword = null;
    private String comfirmDialogTag = null;
    private String InputDialogTag = null;
    private ComfirmDialog comfirmDialog = null;
    private InputDialog inputDialog = null;

    private static final int Input_Dialog_Password_User_Value = 0x9001;
    private static final int Input_Dialog_DeviceId_User_Value = 0x9002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);

        superPassword = getResources().getString(R.string.SuperAdminPassword);
        comfirmDialogTag = getResources().getString(R.string.ConfirmDialogTag);
        InputDialogTag = getResources().getString(R.string.InputDialogTag);

        comfirmDialog = ComfirmDialog.newInstance();

        inputDialog = InputDialog.newInstance();
        inputDialog.setOnInputDialogSubmit(new InputDialog.InputDialogSubmitListener() {
            @Override
            public void onSubmit(String msg, int userValue) {

                if(userValue == Input_Dialog_Password_User_Value){
                    if(superPassword.equals(msg))
                        inputDialog.showInputDialog(getFragmentManager(), InputDialogTag, "信息录入", "请输入设备编号", false,
                                Input_Dialog_DeviceId_User_Value);
                    else
                        comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_error_text),
                                getResources().getString(R.string.dialog_content_password_error_text),
                                getResources().getString(R.string.button_confirm_text), null, null, null);
                }
                else{
                    saveAndUpdateDeviceId(msg);
                }
            }
        });

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

        deviceInfoSetImageView.setOnLongClickListener(v -> {

            inputDialog.showInputDialog(getFragmentManager(), InputDialogTag, getResources().getString(R.string.CheckPermissionText),
                    getResources().getString(R.string.PleaseInputPassword), true, Input_Dialog_Password_User_Value);

            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        deviceIdValueTextView.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_ID_KEY, PublicStringDefine.EMPTY_STRING));
        deviceAddrEditText.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_ADDR_KEY, PublicStringDefine.EMPTY_STRING));
        deviceUserEditText.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_USER_KEY, PublicStringDefine.EMPTY_STRING));
        devicePhoneEditText.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_PHONE_KEY, PublicStringDefine.EMPTY_STRING));
    }

    private void saveAndUpdateDeviceId(String deviceid){

        if(deviceid == null || deviceid.length() <= 0)
            comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_warn_text),
                    getResources().getString(R.string.dialog_content_parm_error_text),
                    getResources().getString(R.string.button_confirm_text), null, null, null);
        else {
            try {

                MySdcardSharedPreferences.getInstance().getEditor().putString(MySdcardSharedPreferences.Keys.DEVICE_ID_KEY, deviceid)
                        .putBoolean(MySdcardSharedPreferences.Keys.DEVICE_STATE_KEY, true)
                        .commit();

                deviceIdValueTextView.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_ID_KEY, PublicStringDefine.EMPTY_STRING));

            }catch (Exception e){
                comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_warn_text),
                        getResources().getString(R.string.dialog_content_save_fail_text),
                        getResources().getString(R.string.button_confirm_text), null, null, null);
                LoggerUnits.error("保存设备编号失败", e);
            }
        }

    }

    private void saveAndUpdateDeviceInfo(){
        try {

            MySdcardSharedPreferences.getInstance().getEditor().putString(MySdcardSharedPreferences.Keys.DEVICE_ADDR_KEY, deviceAddrEditText.getText().toString())
                    .putString(MySdcardSharedPreferences.Keys.DEVICE_USER_KEY, deviceUserEditText.getText().toString())
                    .putString(MySdcardSharedPreferences.Keys.DEVICE_PHONE_KEY, devicePhoneEditText.getText().toString())
                    .putBoolean(MySdcardSharedPreferences.Keys.DEVICE_STATE_KEY, true).commit();

            deviceAddrEditText.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_ADDR_KEY, PublicStringDefine.EMPTY_STRING));
            deviceUserEditText.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_USER_KEY, PublicStringDefine.EMPTY_STRING));
            devicePhoneEditText.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.DEVICE_PHONE_KEY, PublicStringDefine.EMPTY_STRING));

        }catch (Exception e){
            comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_warn_text),
                    getResources().getString(R.string.dialog_content_save_fail_text),
                    getResources().getString(R.string.button_confirm_text), null, null, null);
            LoggerUnits.error("保存设备信息失败", e);
        }

    }

}

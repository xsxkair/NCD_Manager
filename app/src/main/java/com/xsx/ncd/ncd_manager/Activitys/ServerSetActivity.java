package com.xsx.ncd.ncd_manager.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xsx.ncd.ncd_manager.Activitys.Dialogs.ComfirmDialog;
import com.xsx.ncd.ncd_manager.Activitys.Listeners.DialogItemSelectListener;
import com.xsx.ncd.ncd_manager.Defines.PublicStringDefine;
import com.xsx.ncd.ncd_manager.Logger.LoggerUnits;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.Tools.MySdcardSharedPreferences;
import com.xsx.ncd.ncd_manager.Tools.NetUtils;
import com.xsx.ncd.ncd_manager.Tools.WifiTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServerSetActivity extends MyActivity {

    @BindView(R.id.server_ip_editview)
    EditText serverIpEditview;
    @BindView(R.id.server_port_editview)
    EditText serverPortEditview;
    @BindView(R.id.server_save_button)
    Button serverSaveButton;
    @BindView(R.id.server_back_button)
    Button serverBackButton;

    private ComfirmDialog comfirmDialog = null;
    private static final String comfirmDialogTag = "comfirm";
    private String comfirmDialogSubmitButtonText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_set);
        ButterKnife.bind(this);

        comfirmDialog = ComfirmDialog.newInstance();
        comfirmDialogSubmitButtonText = getResources().getString(R.string.button_confirm_text);

        serverSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndUpdateSetValues();
            }
        });

        serverBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        serverIpEditview.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.SERVER_IP_KEY, PublicStringDefine.EMPTY_STRING));
        serverPortEditview.setText(String.format("%d", MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.SERVER_PORT_KEY, 0)));
    }

    private void saveAndUpdateSetValues(){

        String serverIp = serverIpEditview.getText().toString();
        Integer serverPort = null;
        try {
            serverPort = Integer.valueOf(serverPortEditview.getText().toString());
        }catch (NumberFormatException e) {
            serverPort = null;
        }

        if(NetUtils.getIPv4Address(serverIp) == null){
            comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_error_text),
                    getResources().getString(R.string.dialog_content_server_ip_error_text),
                    comfirmDialogSubmitButtonText, null, null, null);
        }
        else if(serverPort == null){
            comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_error_text),
                    getResources().getString(R.string.dialog_content_server_port_error_text),
                    comfirmDialogSubmitButtonText, null, null, null);
        }
        else {
            try {

                MySdcardSharedPreferences.getInstance().getEditor().putString(MySdcardSharedPreferences.Keys.SERVER_IP_KEY, serverIp)
                        .putInt(MySdcardSharedPreferences.Keys.SERVER_PORT_KEY, serverPort)
                        .commit();

                serverIpEditview.setText(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.SERVER_IP_KEY, PublicStringDefine.EMPTY_STRING));
                serverPortEditview.setText(String.format("%d", MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.SERVER_PORT_KEY, 0)));

                Toast.makeText(this, R.string.msg_save_success, Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_error_text),
                        getResources().getString(R.string.dialog_content_save_fail_text),
                        comfirmDialogSubmitButtonText, null, null, null);
                LoggerUnits.error("保存服务器信息失败", e);
            }
        }
    }

}

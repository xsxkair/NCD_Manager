package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.net.EthernetManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.xsx.ncd.ncd_manager.Activitys.Dialogs.ComfirmDialog;
import com.xsx.ncd.ncd_manager.Activitys.Layout.MyExpandLayout;
import com.xsx.ncd.ncd_manager.Defines.EthernetConfig;
import com.xsx.ncd.ncd_manager.Defines.PublicStringDefine;
import com.xsx.ncd.ncd_manager.Logger.LoggerUnits;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.Tools.EthernetSet;
import com.xsx.ncd.ncd_manager.Tools.MySdcardSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EthernetSettingActivity extends Activity {


    @BindView(R.id.eth_switch_switch)
    Switch ethSwitchSwitch;
    @BindView(R.id.eth_connect_type_switch)
    Switch ethConnectTypeSwitch;
    @BindView(R.id.ipaddr_edit)
    EditText ipaddrEdit;
    @BindView(R.id.netmask_edit)
    EditText netmaskEdit;
    @BindView(R.id.eth_dns_edit)
    EditText ethDnsEdit;
    @BindView(R.id.eth_gw_edit)
    EditText ethGwEdit;
    @BindView(R.id.ethCancelConfigButton)
    Button ethCancelConfigButton;
    @BindView(R.id.ethSaveConfigButton)
    Button ethSaveConfigButton;
    @BindView(R.id.eth_config_myexpandlayout)
    MyExpandLayout ethConfigMyexpandlayout;

    private EthernetManager mEthManager = null;
    private EthernetConfig ethernetConfig = null;
    private ComfirmDialog comfirmDialog = null;
    private static final String comfirmDialogTag = "EthernetSettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethernet_setting);
        ButterKnife.bind(this);

  /*      mEthManager = (EthernetManager)this.getSystemService("ethernet");

        ethernetConfig = new EthernetConfig();
        comfirmDialog = ComfirmDialog.newInstance("", "");

        ethSwitchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ethConfigMyexpandlayout.toggleExpand();
                Settings.Global.putInt(EthernetSettingActivity.this.getContentResolver(), Settings.ETHERNET_ON,
                        isChecked ? EthernetManager.ETH_STATE_ENABLED : EthernetManager.ETH_STATE_DISABLED);

                if(isChecked) {
                    showEthernetConfig();
                    if (mEthManager != null)
                        mEthManager.start();
                }
                else{
                    if (mEthManager != null)
                        mEthManager.stop();
                }
            }
        });

        conType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dhcp_radio:
                        ethernetConfig.setDhcp(true);
                        break;
                    case R.id.manual_radio:
                        ethernetConfig.setDhcp(false);
                        break;
                }

                showEthernetConfig();
            }
        });

*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        ethernetConfig.setDhcp(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_DHCP_KEY, true));
        ethernetConfig.setIpv4(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_IPV4_KEY, PublicStringDefine.EMPTY_STRING));
        ethernetConfig.setDns(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_DNS_KEY, PublicStringDefine.EMPTY_STRING));
        ethernetConfig.setGateway(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_GATEWAY_KEY, PublicStringDefine.EMPTY_STRING));
        ethernetConfig.setNetmask(MySdcardSharedPreferences.getInstance().getValue(MySdcardSharedPreferences.Keys.ETHERNET_MASK_KEY, PublicStringDefine.EMPTY_STRING));

        showEthernetConfig();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ethernetConfig = null;
        comfirmDialog = null;
    }

    @OnClick({R.id.ethCancelConfigButton, R.id.ethSaveConfigButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ethCancelConfigButton:
                this.finish();
                break;
            case R.id.ethSaveConfigButton:
                int error = ipValidate();
                if (error == 0) {

                    try {

                        MySdcardSharedPreferences.getInstance().getEditor().putBoolean(MySdcardSharedPreferences.Keys.ETHERNET_DHCP_KEY, ethernetConfig.isDhcp())
                                .putString(MySdcardSharedPreferences.Keys.ETHERNET_IPV4_KEY, ethernetConfig.getIpv4())
                                .putString(MySdcardSharedPreferences.Keys.ETHERNET_DNS_KEY, ethernetConfig.getDns())
                                .putString(MySdcardSharedPreferences.Keys.ETHERNET_GATEWAY_KEY, ethernetConfig.getGateway())
                                .putString(MySdcardSharedPreferences.Keys.ETHERNET_MASK_KEY, ethernetConfig.getNetmask())
                                .commit();

                        EthernetSet.setSystemEthernet(this, ethernetConfig);
                        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        comfirmDialog.showComfirmDialog(getFragmentManager(), comfirmDialogTag, getResources().getString(R.string.dialog_title_warn_text),
                                getResources().getString(R.string.dialog_content_save_fail_text), getResources().getString(R.string.button_confirm_text),
                                null, null, null);
                        LoggerUnits.error("保存Ethernet失败", e);
                    }

                } else if (error == 1)
                    Toast.makeText(this, "请正确输入 ip", Toast.LENGTH_SHORT).show();
                else if (error == 2)
                    Toast.makeText(this, "请正确输入 dns", Toast.LENGTH_SHORT).show();
                else if (error == 3)
                    Toast.makeText(this, "请正确输入 Gateway", Toast.LENGTH_SHORT).show();
                else if (error == 4)
                    Toast.makeText(this, "请正确输入 网关", Toast.LENGTH_SHORT).show();
                else if (error == 5)
                    Toast.makeText(this, "请检查", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showEthernetConfig() {
        boolean isDhcp = ethernetConfig.isDhcp();

        ipaddrEdit.setEnabled(!isDhcp);
        ethDnsEdit.setEnabled(!isDhcp);
        ethGwEdit.setEnabled(!isDhcp);

        ipaddrEdit.setText(ethernetConfig.getIpv4());
        ethDnsEdit.setText(ethernetConfig.getDns());
        ethGwEdit.setText(ethernetConfig.getGateway());
        netmaskEdit.setText(ethernetConfig.getNetmask());
    }

    private int ipValidate() {
        if (ethernetConfig.isDhcp())
            return 0;
        else {

            ethernetConfig.setIpv4(ipaddrEdit.getText().toString());
            ethernetConfig.setDns(ethDnsEdit.getText().toString());
            ethernetConfig.setGateway(ethGwEdit.getText().toString());
            ethernetConfig.setNetmask(netmaskEdit.getText().toString());
            if (isIpAddress(ipaddrEdit.getText().toString()) && isIpAddress(ethDnsEdit.getText().toString()) && isIpAddress(ethGwEdit.getText().toString()))
                return 0;
            else if (!isIpAddress(ipaddrEdit.getText().toString()))
                return 1;
            else if (!isIpAddress(ethDnsEdit.getText().toString()))
                return 2;
            else if (!isIpAddress(ethGwEdit.getText().toString()))
                return 3;
            else if (!isIpAddress(netmaskEdit.getText().toString()))
                return 4;
            else
                return 5;
        }
    }

    private boolean isIpAddress(String value) {
        int start = 0;
        int end = value.indexOf('.');
        int numBlocks = 0;

        while (start < value.length()) {
            if (end == -1) {
                end = value.length();
            }

            try {
                int block = Integer.parseInt(value.substring(start, end));
                if ((block > 255) || (block < 0)) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

            numBlocks++;

            start = end + 1;
            end = value.indexOf('.', start);
        }
        return numBlocks == 4;
    }
}

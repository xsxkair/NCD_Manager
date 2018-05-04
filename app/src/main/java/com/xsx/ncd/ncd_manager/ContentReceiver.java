package com.xsx.ncd.ncd_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xsx.ncd.ncd_manager.Activitys.LunchActivity;

public class ContentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it=new Intent(context,LunchActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        Toast.makeText(context,"我自启动成功了哈",Toast.LENGTH_LONG).show();
    }
}

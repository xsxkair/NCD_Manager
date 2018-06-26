package com.xsx.ncd.ncd_manager.Activitys.Listeners;

import android.view.View;

public interface OnViewItemClickListener<T> {

    void onItemClick(View view, int position, T t);
}

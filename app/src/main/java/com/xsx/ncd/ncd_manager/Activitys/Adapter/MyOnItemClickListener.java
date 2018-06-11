package com.xsx.ncd.ncd_manager.Activitys.Adapter;

import android.view.MotionEvent;
import android.view.View;

public interface MyOnItemClickListener<T> {

    void onItemClick(View view, int position, T t);
}

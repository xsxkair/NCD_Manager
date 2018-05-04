package com.xsx.ncd.ncd_manager.Activitys.Adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class UserDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public UserDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}

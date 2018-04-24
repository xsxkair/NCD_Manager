package com.xsx.ncd.ncd_manager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsx.ncd.Adapter.LunchViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


public class LunchActivity extends Activity {

    @BindView(R.id.lunch_pagerview)
    ViewPager lunchPagerview;

    @BindArray(R.array.lunch_view_pager_item_ids)
    int lunchViewItemIds[];

    public static final int pageCnt = 4;
    private ArrayList<View> pageList = new ArrayList<>(pageCnt);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ButterKnife.bind(this);

        for(int i=0; i<pageCnt; i++){
            ImageView pageItem = new ImageView(this);
            pageItem.setMaxWidth(1000);
            pageItem.setImageResource(R.drawable.lunch_item2);
            pageList.add(pageItem);
        }

        LunchViewPagerAdapter lunchViewPagerAdapter = new LunchViewPagerAdapter(pageList);
        lunchPagerview.setAdapter(lunchViewPagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

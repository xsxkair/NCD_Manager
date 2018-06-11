package com.xsx.ncd.ncd_manager.Activitys.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.TestData;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TestDataViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    TextView testDataIndexTextView;
    TextView testDataItemTextView;
    TextView testDataSampleTextView;
    TextView testDataResultTextView;
    TextView testDataDateTextView;
    TextView testDataTesterTextView;
    ImageView testDataReportImageView;
    private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public TestDataViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        testDataIndexTextView = itemView.findViewById(R.id.testDataIndexTextView);
        testDataItemTextView = itemView.findViewById(R.id.testDataItemTextView);
        testDataSampleTextView = itemView.findViewById(R.id.testDataSampleTextView);
        testDataResultTextView = itemView.findViewById(R.id.testDataResultTextView);
        testDataDateTextView = itemView.findViewById(R.id.testDataDateTextView);
        testDataTesterTextView = itemView.findViewById(R.id.testDataTesterTextView);
        testDataReportImageView = itemView.findViewById(R.id.testDataReportImageView);
    }

    public void fillViewHolderContent(TestData testData){

        testDataIndexTextView.setText(testData.getIndex().toString());
        testDataItemTextView.setText(testData.getCard().getItem());
        testDataSampleTextView.setText(testData.getSampleid());

        numberFormat.setMaximumFractionDigits(testData.getCard().getPointnum());
        if(!testData.getResultok())
            testDataResultTextView.setText(R.string.TestResultErrorText);
        else if(testData.getTestv() < testData.getCard().getLowestresult())
            testDataResultTextView.setText(String.format("<%s %s", numberFormat.format(testData.getCard().getLowestresult()), testData.getCard().getMeasure()));
        else
            testDataResultTextView.setText(String.format("%s %s", numberFormat.format(testData.getTestv()), testData.getCard().getMeasure()));

        testDataDateTextView.setText(sdf.format(testData.getTesttime()));
        testDataTesterTextView.setText(testData.getTester());

        if(testData.getCheck() == null)
            testDataReportImageView.setImageResource(R.mipmap.record);
        else if (testData.getCheck())
            testDataReportImageView.setImageResource(R.mipmap.recordchecked);
        else
            testDataReportImageView.setImageResource(R.mipmap.recordnocheck);

    }

    public void userHolderSelectedEvent(){
        this.itemView.setBackgroundResource(R.color.button_pressed);
    }

    public void userHolderUnSelectedEvent(){
        this.itemView.setBackgroundResource(R.color.white);
    }

    public View getItemView() {
        return itemView;
    }
}

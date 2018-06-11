package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.TestData;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestDetailActivity extends Activity {

    @BindView(R.id.testDataLineChart)
    LineChart testDataLineChart;
    @BindView(R.id.TestItemContentTextView)
    TextView TestItemContentTextView;
    @BindView(R.id.TestSampleContentTextView)
    TextView TestSampleContentTextView;
    @BindView(R.id.TestCardContentTextView)
    TextView TestCardContentTextView;
    @BindView(R.id.TestTesterContentTextView)
    TextView TestTesterContentTextView;
    @BindView(R.id.TestTimeContentTextView)
    TextView TestTimeContentTextView;
    @BindView(R.id.TestResultContentTextView)
    TextView TestResultContentTextView;
    @BindView(R.id.TestNormalContentTextView)
    TextView TestNormalContentTextView;
    @BindView(R.id.reportPassRadioButton)
    RadioButton reportPassRadioButton;
    @BindView(R.id.reportNoPassRadioButton)
    RadioButton reportNoPassRadioButton;
    @BindView(R.id.reportCheckRadioGroup)
    RadioGroup reportCheckRadioGroup;
    @BindView(R.id.uploadReportButton)
    Button uploadReportButton;
    @BindView(R.id.printfReportButton)
    Button printfReportButton;

    private XAxis xAxis = null;
    private YAxis leftYAxis;
    private YAxis rightYAxis;
    private LineChartMarkerView myLineChartMarkerView = null;

    private TestData testData = null;

    private SimpleDateFormat sf = null;
    private NumberFormat numberFormat = null;

    private Gson gson = null;
    private Type type = null;
    private List<Integer> seriasData = null;
    private List<Integer> seriasColor = null;
    private int seriasDataSize;
    private List<Entry> entries = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detail);
        ButterKnife.bind(this);

        testData = (TestData) getIntent().getSerializableExtra(getResources().getResourceName(R.string.TestDataIntentNameText));

        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        numberFormat = NumberFormat.getNumberInstance();
        gson = new Gson();
        type = new TypeToken<List<Integer>>(){}.getType();
        entries = new ArrayList<>();
        seriasColor = new ArrayList<>();

        //获取坐标轴
        xAxis = testDataLineChart.getXAxis();
        leftYAxis = testDataLineChart.getAxisLeft();
        rightYAxis = testDataLineChart.getAxisRight();
        testDataLineChart.getLegend().setEnabled(false);
        testDataLineChart.getDescription().setEnabled(false);

        testDataLineChart.setBackgroundResource(R.color.LineChartBackgroundColor);
        myLineChartMarkerView = new LineChartMarkerView(this);
        testDataLineChart.setMarker(myLineChartMarkerView);

        //X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
       // xAxis.setGranularity(20);
        xAxis.setLabelCount(16, true);
        xAxis.setGridColor(R.color.LineChartGridLineColor);
        xAxis.enableGridDashedLine(5f, 5f, 0f);

        //Y轴
        rightYAxis.setEnabled(false);
        leftYAxis.setAxisMinimum(0);
        xAxis.setLabelCount(10, true);

        leftYAxis.setGridColor(R.color.LineChartGridLineColor);
        leftYAxis.enableGridDashedLine(5f, 5f, 0f);

        showReportInfo();
    }

    private void showReportInfo(){
        TestItemContentTextView.setText(testData.getItem());
        TestSampleContentTextView.setText(testData.getSampleid());
        TestCardContentTextView.setText(String.format("%s-%s", testData.getCard().getPihao(), testData.getCardnum()));
        TestTesterContentTextView.setText(testData.getTester());
        TestTimeContentTextView.setText(sf.format(testData.getTesttime()));

        numberFormat.setMaximumFractionDigits(testData.getCard().getPointnum());
        if(!testData.getResultok())
            TestResultContentTextView.setText(R.string.TestResultErrorText);
        else if(testData.getTestv() < testData.getCard().getLowestresult())
            TestResultContentTextView.setText(String.format("<%s %s", numberFormat.format(testData.getCard().getLowestresult()), testData.getCard().getMeasure()));
        else
            TestResultContentTextView.setText(String.format("%s %s", numberFormat.format(testData.getTestv()), testData.getCard().getMeasure()));

        TestNormalContentTextView.setText(testData.getCard().getNormalresult());

        if(testData.getCheck() == null)
        {
            reportNoPassRadioButton.setSelected(false);
            reportPassRadioButton.setSelected(false);
        }
        else if(testData.getCheck()){
            reportNoPassRadioButton.setSelected(false);
            reportPassRadioButton.setSelected(true);
        }
        else {
            reportNoPassRadioButton.setSelected(true);
            reportPassRadioButton.setSelected(false);
        }


        seriasData = gson.fromJson(testData.getSeries(), type);
        seriasDataSize = seriasData.size();

        Log.d("xsx", " t:"+testData.getTline()+"  c:"+testData.getCline()+" b:"+testData.getBline());

        for (int i = 0; i < seriasDataSize; i++) {
            if(i == testData.getBline().intValue() || i == testData.getCline().intValue() || i == testData.getTline().intValue())
                seriasColor.add(getResources().getColor(R.color.colorRed));
            else
                seriasColor.add(getResources().getColor(R.color.lightgreen));

            entries.add(new Entry(i, seriasData.get(i)));
        }

        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "测试曲线");

        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setCircleColors(seriasColor);
        lineDataSet.setDrawValues(false);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setColor(getResources().getColor(R.color.LineChartSeriaColor));

        LineData lineData = new LineData(lineDataSet);

        testDataLineChart.setData(lineData);

        testDataLineChart.getLineData().getDataSetByIndex(0).setLabel("xsxsxsx");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        sf = null;
        numberFormat = null;

        gson = null;
        type = null;
        seriasData = null;
        entries = null;
        myLineChartMarkerView = null;
    }
}

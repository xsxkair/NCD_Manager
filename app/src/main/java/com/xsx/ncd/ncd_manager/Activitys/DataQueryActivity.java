package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.MyOnItemClickListener;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.TestDataAdapter;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.UserDecoration;
import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.Dao.Page;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.Card;
import com.xsx.ncd.ncd_manager.entity.TestData;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DataQueryActivity extends Activity {


    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.testDateQueryContentTextView)
    TextView testDateQueryTextView;
    @BindView(R.id.testItemQuerySpinner)
    Spinner testItemQuerySpinner;
    @BindView(R.id.testSampleIdQueryEditText)
    EditText testSampleIdQueryEditText;
    @BindView(R.id.createTestDataButton)
    Button createTestDataButton;
    @BindView(R.id.queryDataButton)
    Button queryDataButton;
    @BindView(R.id.dataRecycerView)
    RecyclerView dataRecycerView;
    @BindView(R.id.currentPageIndexTextView)
    TextView currentPageIndexTextView;
    @BindView(R.id.clearTestDateQueryImageView)
    ImageView clearTestDateQueryImageView;
    @BindView(R.id.clearTestItemQueryImageView)
    ImageView clearTestItemQueryImageView;
    @BindView(R.id.clearTestSampleQueryImageView)
    ImageView clearTestSampleQueryImageView;
    @BindView(R.id.reportCheckQueryImageView)
    ImageView reportCheckQueryImageView;
    @BindView(R.id.prePageImageView)
    ImageView prePageImageView;
    @BindView(R.id.nextPageImageView)
    ImageView nextPageImageView;

    private String[] itemArray;

    private TimePickerView testTimePickerView;
    private OnTimeSelectListener myOnTimeSelectListener;

    private TestDataAdapter testDataAdapter;
    private Observer<Page<TestData>> testDataPageObserver;
    private Observer<Boolean> testDataActionObserver;

    private long currentPageIndex = 0;
    private long totalPageSize = 0;
    private String queryTime = null;
    private String queryItem = null;
    private String querySampleId = null;
    private Boolean queryChecked = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_query);
        ButterKnife.bind(this);

        itemArray = getResources().getStringArray(R.array.NCD_Test_Item_Array);

        //测试日期
        testDateQueryTextView.setText(null);
        testDateQueryTextView.setOnClickListener(v -> {
            testTimePickerView.show();
        });
        testDateQueryTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (string.length() > 0) {
                    queryTime = string;
                    clearTestDateQueryImageView.setVisibility(View.VISIBLE);
                } else {
                    queryTime = null;
                    clearTestDateQueryImageView.setVisibility(View.INVISIBLE);
                }
            }
        });
        clearTestDateQueryImageView.setOnClickListener(v -> {
            testDateQueryTextView.setText(null);
        });

        myOnTimeSelectListener = new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                testDateQueryTextView.setText(sf.format(date));
            }
        };
        testTimePickerView = new TimePickerBuilder(this, myOnTimeSelectListener)
                .setContentTextSize(20)//滚轮文字大小
                .setTitleSize(32)//标题文字大小
                .setTitleText("选择测试日期")
                .build();

        //测试项目

        testItemQuerySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    queryItem = null;
                    clearTestItemQueryImageView.setVisibility(View.INVISIBLE);
                } else {
                    queryItem = itemArray[position];
                    clearTestItemQueryImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        clearTestItemQueryImageView.setOnClickListener(v -> {
            testItemQuerySpinner.setSelection(0);
        });

        //样本编号
        testSampleIdQueryEditText.setText(null);
        testSampleIdQueryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                if (string.length() > 0) {
                    querySampleId = String.format("%%%s%%", string);
                    clearTestSampleQueryImageView.setVisibility(View.VISIBLE);
                } else {
                    querySampleId = null;
                    clearTestSampleQueryImageView.setVisibility(View.INVISIBLE);
                }
            }
        });
        clearTestSampleQueryImageView.setOnClickListener(v -> {
            testSampleIdQueryEditText.setText(null);
        });

        //报告审核
        reportCheckQueryImageView.setOnClickListener(v -> {
            if (queryChecked == null) {
                queryChecked = true;
                reportCheckQueryImageView.setImageResource(R.mipmap.recordchecked);
            } else if (queryChecked) {
                queryChecked = false;
                reportCheckQueryImageView.setImageResource(R.mipmap.recordnocheck);
            } else {
                queryChecked = null;
                reportCheckQueryImageView.setImageResource(R.mipmap.record);
            }
        });


        //测试数据
        testDataAdapter = new TestDataAdapter(null, this, R.layout.layout_test_data_item_view);

        testDataAdapter.setOnItemClickListener(new MyOnItemClickListener<TestData>() {
            @Override
            public void onItemClick(View view, int position, TestData testData) {

                Log.d("xsx", "click: " + position);
                Intent intent = new Intent(DataQueryActivity.this, TestDetailActivity.class);
                intent.putExtra(getResources().getResourceName(R.string.TestDataIntentNameText), testData);
                startActivity(intent);
            }
        });

        dataRecycerView.setAdapter(testDataAdapter);
        dataRecycerView.setLayoutManager(new LinearLayoutManager(this));
        dataRecycerView.addItemDecoration(new UserDecoration(2));

        //页码切换
        prePageImageView.setOnClickListener(v -> {
            currentPageIndex--;
            if(currentPageIndex < 0)
                currentPageIndex = totalPageSize-1;

            if(currentPageIndex < 0)
                currentPageIndex = 0;

            startQueryTestDataPages();
        });

        nextPageImageView.setOnClickListener(v -> {
            currentPageIndex++;
            if(currentPageIndex >= totalPageSize)
                currentPageIndex = 0;

            startQueryTestDataPages();
        });

        testDataPageObserver = new Observer<Page<TestData>>() {
            private Disposable disposable = null;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Page<TestData> testDataPage) {
                showQueiedTestData(testDataPage);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("xsx", e.getMessage());
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };

        testDataActionObserver = new Observer<Boolean>() {
            private Disposable disposable = null;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Toast.makeText(DataQueryActivity.this, R.string.actionSuccessText, Toast.LENGTH_SHORT).show();
                startQueryTestDataPages();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(DataQueryActivity.this, R.string.ActionFailText, Toast.LENGTH_SHORT).show();
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };

        createTestDataButton.setOnClickListener(v -> {
            createNewTestData();
        });

        queryDataButton.setOnClickListener(v -> {
            startQueryTestDataPages();
        });

        currentPageIndex = 0;
        startQueryTestDataPages();
        Log.d("xsx", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("xsx", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("xsx", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("xsx", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("xsx", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("xsx", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("xsx", "onDestroy");
    }

    private void createNewTestData() {
        Card card = new Card();
        TestData testData = new TestData();

        card.setItem(testItemQuerySpinner.getSelectedItem().toString());
        card.setMeasure("pg/mL");
        card.setPointnum(2);
        card.setLowestresult(0.3f);


        testData.setCard(card);
        testData.setItem(card.getItem());
        testData.setResultok(false);
        testData.setTestv((float) (Math.random()));
        testData.setTester("zx");
        testData.setSampleid(String.valueOf(System.currentTimeMillis()));
        testData.setTesttime(new Timestamp(System.currentTimeMillis()));

        DataBaseMethods.getInstance().saveNewTestData(testDataActionObserver, testData);
    }

    private void showQueiedTestData(Page<TestData> testDataPage) {

        totalPageSize = testDataPage.getTotalPages();
        currentPageIndexTextView.setText(String.format("%d / %d", testDataPage.getCurrentPageIndex(), testDataPage.getTotalPages()));

        testDataAdapter.updateTestData(testDataPage.getContent());
        dataRecycerView.callOnClick();
    }

    private void startQueryTestDataPages() {

        DataBaseMethods.getInstance().queryTestDataByPage(testDataPageObserver, currentPageIndex, 30l,
                queryTime, queryItem, querySampleId, queryChecked);
    }
}

package com.zice.xz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.zice.xz.database.ColumnName;
import com.zice.xz.extraview.ExtHistogramView;
import com.zice.xz.mvp.contract.IHistogramActivityView;
import com.zice.xz.mvp.mode.DataMode;
import com.zice.xz.mvp.presenter.HistogramPresenter;
import com.zice.xz.network.Net;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistogramActivity extends BaseActivity implements IHistogramActivityView {

    private HistogramPresenter presenter;
    private Button btnQuery;
    private ExtHistogramView extHvConsume;

    private Button btnTestPost;
    private Net net;
    private HashMap<String, String> params;
    private Button btnTestGet;
    private CheckBox chbYear;
    private CheckBox chbMonth;
    private CheckBox chbDay;
    private EditText etYear;
    private EditText etMonth;
    private EditText etDay;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        handler = new Handler(Looper.getMainLooper());
        net = new Net();
        presenter = new HistogramPresenter(this);
        initView();
        initDefault();
        setListener();
    }

    private void initDefault() {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH)+1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        etYear.setText(year + "");
        etMonth.setText(month + "");
        etDay.setText(day + "");
    }

    private static final String TAG = "HistogramActivity";
    private void setListener() {
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chbYear.isChecked() && !chbMonth.isChecked() && !chbDay.isChecked()) {
                    showToast("请选择一个分类");
                    return;
                }
                String dateType = null;
                String year = null;
                String month = null;
                String day = null;
                if (chbYear.isChecked()) {
                    dateType = ColumnName.COLUMN_YEAR;
                    year = getEtContent(etYear);
                    if ("".equals(year)) {
                        showToast("年不能为空");
                        return;
                    }
                }
                if (chbMonth.isChecked()) {
                    dateType = ColumnName.COLUMN_MONTH;
                    month = getEtContent(etMonth);
                    if ("".equals(month)) {
                        showToast("月不能为空");
                        return;
                    }
                }
                if (chbDay.isChecked()) {
                    dateType = ColumnName.COLUMN_DAY;
                    day = getEtContent(etDay);
                    if ("".equals(day)) {
                        showToast("日不能为空");
                        return;
                    }
                }
                presenter.queryConsumeDate(dateType, year, month,day);
//                presenter.queryConsumeDate(ColumnName.COLUMN_MONTH, "2017", "7", "");
            }
        });
        btnTestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = new HashMap<>();
                params.put("money", "12.00");
                params.put("name", "Test");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        net.requestGet(params);
//                        net.requestPost(params);
                        net.fileUpload(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i(TAG, "onFailure: call-->" + call);
                                Log.i(TAG, "onFailure: IOException --> "+e);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(HistogramActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(HistogramActivity.this, "恭喜，成功！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).start();
            }
        });
        btnTestGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net.fileDownload();
            }
        });
        chbYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etYear.setVisibility(View.VISIBLE);
                }else {
                    etYear.setVisibility(View.GONE);
                }
            }
        });
        chbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etMonth.setVisibility(View.VISIBLE);
                    chbYear.setChecked(true);
                    chbYear.setEnabled(false);
                }else {
                    etMonth.setVisibility(View.GONE);
                    chbYear.setEnabled(true);
                }
            }
        });
        chbDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etDay.setVisibility(View.VISIBLE);
                    chbMonth.setChecked(true);
                    chbMonth.setEnabled(false);
                }else {
                    etDay.setVisibility(View.GONE);
                    chbMonth.setEnabled(true);
                }
            }
        });
    }

    private void showToast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HistogramActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getEtContent(EditText editText) {
        if (editText.getVisibility() == View.VISIBLE) {
            return String.valueOf(editText.getText());
        }else {
            return null;
        }
    }

    private void initView() {
        btnQuery = (Button) findViewById(R.id.btn_query);
        extHvConsume = (ExtHistogramView) findViewById(R.id.ext_hv_consume);
        btnTestPost = (Button) findViewById(R.id.btn_test_post);
        btnTestGet = (Button) findViewById(R.id.btn_test_get);
        chbYear = (CheckBox) findViewById(R.id.chb_year);
        chbMonth = (CheckBox) findViewById(R.id.chb_month);
        chbDay = (CheckBox) findViewById(R.id.chb_day);
        etYear = (EditText) findViewById(R.id.et_year);
        etMonth = (EditText) findViewById(R.id.et_month);
        etDay = (EditText) findViewById(R.id.et_day);

    }

    @Override
    public void onFetchConsumeData(String date, List<DataMode.ConsumeData> datas) {
        int sumMoney = 0;
        for (DataMode.ConsumeData consumeData : datas) {
            sumMoney += consumeData.getMoney();
        }
        String title = date + "消费 " + sumMoney;
        extHvConsume.refreshDate(title, datas);
    }
}

package com.zice.xz;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.zice.xz.database.ColumnName;
import com.zice.xz.mvp.contract.IMainActivityView;
import com.zice.xz.mvp.presenter.DBPresenter;
import com.zice.xz.utils.DBUtils;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements IMainActivityView {
    private static final String TAG = "MainActivity";
    private Spinner spCategory;
    private Spinner spType;
    private EditText etMoney;
    private Button btnInsert;
    private DBPresenter dbPresenter;
//    private Button btnInitDb;
    private Button btnSearch;
    private ListView lvConsume;
    private EditText etSearch;
    private EditText etDesc;
    private TextView tvDayConsume;
    private TextView tvMonthConsume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        dbPresenter = new DBPresenter(this);
        dbPresenter.queryConsumeCategory();
        Log.i(TAG, "onCreate() savedInstanceState -> " + savedInstanceState);
        updateTopConsume();
    }

    private void updateTopConsume() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        String money = dbPresenter.queryMoneyByDate(ColumnName.COLUMN_DAY, year + "", month + "", day + "");
        tvDayConsume.setText(money);
        money = dbPresenter.queryMoneyByDate(ColumnName.COLUMN_MONTH, year + "", month + "", null);
        tvMonthConsume.setText(money);
    }

    private void initView() {
        spCategory = (Spinner) findViewById(R.id.sp_category);
        spType = (Spinner) findViewById(R.id.sp_type);
        etMoney = (EditText) findViewById(R.id.et_money);
        btnInsert = (Button) findViewById(R.id.ok_insert);
        btnSearch = (Button) findViewById(R.id.btn_search);
        lvConsume = (ListView) findViewById(R.id.lv_consume);
        etSearch = (EditText) findViewById(R.id.et_search);
        etDesc = (EditText) findViewById(R.id.et_desc);
        tvDayConsume = (TextView) findViewById(R.id.tv_day_consume);
        tvMonthConsume = (TextView) findViewById(R.id.tv_month_consume);
    }

    private void setListener() {
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) spCategory.getSelectedItem();
                dbPresenter.queryConsumeType( selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> categoryItem = (HashMap<String, String>) spCategory.getSelectedItem();
                HashMap<String, String> typeItem = (HashMap<String, String>) spType.getSelectedItem();
                String money = String.valueOf(etMoney.getText());
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(MainActivity.this, "金额不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbPresenter.insertConsume(categoryItem, typeItem, money, String.valueOf(etDesc.getText()));
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbPresenter.queryConsume(DBPresenter.PR_TIME, etSearch.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBUtils.syncDataBase();
        if (dbPresenter != null) {
            dbPresenter.detach();
        }
    }

    @Override
    public void onFetchConsumeCategory(List<HashMap<String, String>> hashMapList) {
        spCategory.setAdapter(new SimpleAdapter(this, hashMapList, R.layout.class_type, new String[]{"name"}, new int[]{R.id.item}));
    }

    @Override
    public void onFetchConsumeType(List<HashMap<String, String>> hashMapList) {
        spType.setAdapter(new SimpleAdapter(MainActivity.this, hashMapList, R.layout.class_type, new String[]{"name"}, new int[]{R.id.item}));
    }

    @Override
    public void onFetchInsertSuccess() {
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        updateTopConsume();
    }

    @Override
    public void onFetchInsertFailed() {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchUpdateConsume(List<HashMap<String, String>> hashMapList) {
        if (hashMapList == null || hashMapList.size() == 0) {
            Toast.makeText(this, "无消费记录", Toast.LENGTH_SHORT).show();
            return;
        }
        lvConsume.setAdapter(new SimpleAdapter(this,hashMapList,R.layout.layout_consume,new String[]{"time","money"},new int[]{R.id.txt_time,R.id.txt_money}));
    }
}

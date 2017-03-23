package com.zice.xz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.mvp.contract.IMainActivityView;
import com.zice.xz.mvp.presenter.DBPresenter;
import com.zice.xz.utils.DBUtils;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements IMainActivityView {
    private static final String TAG = "MainActivity";
    private Spinner spCategory;
    private DataBaseHelper dbh;
    private Spinner spType;
    private EditText etMoney;
    private Button okInsert;
    private DBPresenter dbPresenter;
    private Button btnInitDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = new DataBaseHelper(this, DataBaseTable.DB_NAME, null, DataBaseHelper.DB_VERSION_INIT);
        dbPresenter = new DBPresenter(this);
        initView();
        Log.i(TAG, "onCreate() savedInstanceState -> " + savedInstanceState);
        setListener();
    }

    private void initView() {
        spCategory = (Spinner) findViewById(R.id.sp_category);
        spType = (Spinner) findViewById(R.id.sp_type);
        etMoney = (EditText) findViewById(R.id.et_money);
        okInsert = (Button) findViewById(R.id.ok_insert);
        btnInitDb = (Button) findViewById(R.id.init_db);
    }

    private void setListener() {
        btnInitDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbPresenter.initConsumeCategory(dbh);
            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) spCategory.getSelectedItem();
                dbPresenter.initConsumeType(dbh, selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        okInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> categotyItem = (HashMap<String, String>) spCategory.getSelectedItem();
                HashMap<String, String> typeItem = (HashMap<String, String>) spType.getSelectedItem();
                dbPresenter.insertConsume(dbh, categotyItem, typeItem, String.valueOf(etMoney.getText()));
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
        spCategory.setAdapter(new SimpleAdapter(this, hashMapList, R.layout.test, new String[]{"name"}, new int[]{R.id.item}));
    }

    @Override
    public void onFetchConsumeType(List<HashMap<String, String>> hashMapList) {
        spType.setAdapter(new SimpleAdapter(MainActivity.this, hashMapList, R.layout.test, new String[]{"name"}, new int[]{R.id.item}));
    }

    @Override
    public void onFetchInsertSuccess() {
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchInsertFailed() {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }
}

package com.zice.xz;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.TableColumn;
import com.zice.xz.mvp.contract.IMainActivityView;
import com.zice.xz.mvp.presenter.DBPresenter;
import com.zice.xz.utils.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements IMainActivityView {
private static final String TAG = "MainActivity";
    private Spinner spClass;
    private DataBaseHelper dbh;
    private Spinner spType;
    private EditText money;
    private Button okInsert;
    private DBPresenter dbPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.i(TAG, "onCreate() savedInstanceState -> " + savedInstanceState);
        setListener();
        dbPresenter = new DBPresenter(this);
    }

    private void initView() {
        spClass = (Spinner) findViewById(R.id.sp_class);
        spType = (Spinner) findViewById(R.id.sp_type);
        money = (EditText) findViewById(R.id.money);
        okInsert = (Button) findViewById(R.id.ok_insert);
    }

    public void click(View view) {
        dbh = new DataBaseHelper(this, DataBaseTable.DB_NAME, null, DataBaseHelper.DB_VERSION_INIT);
        dbPresenter.initConsumeClass(dbh);
    }
    
    private void setListener(){
        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) spClass.getSelectedItem();
                String class_id = selectedItem.get("class_id");
                Cursor query = dbh.query(DataBaseTable.TABLE_CONSUME_TYPE, TableColumn.COLUMN_CLASS_ID, class_id);
                List<HashMap<String, String>> listems = new ArrayList<>();
                while (query.moveToNext()){
                    HashMap<String, String> map = new HashMap<>();
                    String name = query.getString(query.getColumnIndex(TableColumn.COLUMN_NAME));
                    String typeId = query.getString(query.getColumnIndex(TableColumn.COLUMN_TYPE_ID));
                    map.put("type_id", typeId);
                    map.put("name",name);
                    listems.add(map);
                }
                spType.setAdapter(new SimpleAdapter(MainActivity.this,listems,R.layout.test,new String[]{"name"},new int[]{R.id.item}));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        okInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        DBUtils.syncDataBase();
    }

    @Override
    public void onFetchConsumeClass(List<HashMap<String, String>> hashMapList) {
        spClass.setAdapter(new SimpleAdapter(this,hashMapList,R.layout.test,new String[]{"name"},new int[]{R.id.item}));
    }
}

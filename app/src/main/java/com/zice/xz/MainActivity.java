package com.zice.xz;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.TableColumn;
import com.zice.xz.utils.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private static final String TAG = "MainActivity";
    private Spinner spClass;
    private DataBaseHelper dbh;
    private Spinner spType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.i(TAG, "onCreate() savedInstanceState -> " + savedInstanceState);
        setListener();
    }

    private void initView() {
        spClass = (Spinner) findViewById(R.id.sp_class);
        spType = (Spinner) findViewById(R.id.sp_type);
    }

    public void click(View view) {
        dbh = new DataBaseHelper(this, DataBaseTable.DB_NAME, null, 1);
        Log.i(TAG, "onCreate() savedInstanceState -> ");
        List<HashMap<String, String>> listems = new ArrayList<>();
//
//        dbh.queryTable("")
//                .where("列名").is("值")
//                .where("te").is("value")
//                .exec();
        Cursor consumeClass = dbh.queryTable(DataBaseTable.TABLE_CONSUME_CLASS).exec();
        
        while (consumeClass.moveToNext()){
            HashMap<String, String> map = new HashMap<>();
            String name = consumeClass.getString(consumeClass.getColumnIndex(TableColumn.COLUMN_NAME));
            String id = consumeClass.getString(consumeClass.getColumnIndex(TableColumn.COLUMN_CLASS_ID));
            map.put("class_id", id);
            map.put("name",name);
            listems.add(map);
        }
        spClass.setAdapter(new SimpleAdapter(this,listems,R.layout.test,new String[]{"name"},new int[]{R.id.item}));
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        DBUtils.syncDataBase();
    }
}

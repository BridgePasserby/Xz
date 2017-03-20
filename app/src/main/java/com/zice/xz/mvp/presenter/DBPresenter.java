package com.zice.xz.mvp.presenter;

import android.database.Cursor;
import android.util.Log;

import com.zice.xz.App;
import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.TableColumn;
import com.zice.xz.mvp.contract.IMainActivityView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public class DBPresenter extends BasePresenter<IMainActivityView> {
    /**
     * 自动关联view和presenter
     *
     * @param iMainActivityView 关联对象
     */
    public DBPresenter(IMainActivityView iMainActivityView) {
        super(iMainActivityView);
    }

    public void initConsumeClass(DataBaseHelper dbh) {
        Log.i(TAG, "onCreate() savedInstanceState -> ");
        List<HashMap<String, String>> listItems = new ArrayList<>();
        Cursor consumeClass = dbh.queryTable(DataBaseTable.TABLE_CONSUME_CLASS).exec();
        while (consumeClass.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String name = consumeClass.getString(consumeClass.getColumnIndex(TableColumn.COLUMN_NAME));
            String id = consumeClass.getString(consumeClass.getColumnIndex(TableColumn.COLUMN_CLASS_ID));
            map.put("class_id", id);
            map.put("name", name);
            listItems.add(map);
        }
        if (isAttach()) {
            getView().onFetchConsumeClass(listItems);
        }
    }

    public void upDateConsumeType(DataBaseHelper dbh, HashMap<String, String> selectedItem) {
        String class_id = selectedItem.get("class_id");
        Cursor query = dbh.query(DataBaseTable.TABLE_CONSUME_TYPE, TableColumn.COLUMN_CLASS_ID, class_id);
        List<HashMap<String, String>> listItems = new ArrayList<>();
        while (query.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String name = query.getString(query.getColumnIndex(TableColumn.COLUMN_NAME));
            String typeId = query.getString(query.getColumnIndex(TableColumn.COLUMN_TYPE_ID));
            map.put("type_id", typeId);
            map.put("name", name);
            listItems.add(map);
        }
        if (isAttach()) {
            getView().onFetchConsumeType(listItems);
        }
    }
}

package com.zice.xz.mvp.presenter;

import android.database.Cursor;
import android.util.Log;

import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.ColumnName;
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

    public void initConsumeCategory(DataBaseHelper dbh) {
        Log.i(TAG, "onCreate() savedInstanceState -> ");
        List<HashMap<String, String>> listItems = new ArrayList<>();
        Cursor consumeCategory = dbh.queryTable(DataBaseTable.TABLE_CONSUME_CATEGORY).exec();
        while (consumeCategory.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String name = consumeCategory.getString(consumeCategory.getColumnIndex(ColumnName.COLUMN_NAME));
            String id = consumeCategory.getString(consumeCategory.getColumnIndex(ColumnName.COLUMN_CATEGORY_ID));
            map.put("category_id", id);
            map.put("name", name);
            listItems.add(map);
        }
        if (isAttach()) {
            getView().onFetchConsumeCategory(listItems);
        }
    }

    public void upDateConsumeType(DataBaseHelper dbh, HashMap<String, String> selectedItem) {
        String category_id = selectedItem.get("category_id");
        Cursor query = dbh.query(DataBaseTable.TABLE_CONSUME_TYPE, ColumnName.COLUMN_CATEGORY_ID, category_id);
        List<HashMap<String, String>> listItems = new ArrayList<>();
        while (query.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String name = query.getString(query.getColumnIndex(ColumnName.COLUMN_NAME));
            String typeId = query.getString(query.getColumnIndex(ColumnName.COLUMN_TYPE_ID));
            map.put("type_id", typeId);
            map.put("name", name);
            listItems.add(map);
        }
        if (isAttach()) {
            getView().onFetchConsumeType(listItems);
        }
    }
}

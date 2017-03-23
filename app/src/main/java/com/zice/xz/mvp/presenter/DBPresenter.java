package com.zice.xz.mvp.presenter;

import android.database.Cursor;
import android.util.Log;

import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.ColumnName;
import com.zice.xz.mvp.contract.IMainActivityView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public void initConsumeType(DataBaseHelper dbh, HashMap<String, String> selectedItem) {
        String categoryId = selectedItem.get(ColumnName.COLUMN_CATEGORY_ID);
        Cursor query = dbh.query(DataBaseTable.TABLE_CONSUME_TYPE, ColumnName.COLUMN_CATEGORY_ID, categoryId);
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

    public void insertConsume(DataBaseHelper dbh, HashMap<String, String> categoryItem, HashMap<String, String> typeItem, String money) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String categoryId = categoryItem.get(ColumnName.COLUMN_CATEGORY_ID);
        String typeId = typeItem.get(ColumnName.COLUMN_TYPE_ID);
        boolean success = dbh.insertTable(DataBaseTable.TABLE_CONSUME_BILL)
                .where(ColumnName.COLUMN_CATEGORY_ID).is(categoryId)
                .where(ColumnName.COLUMN_TYPE_ID).is(typeId)
                .where(ColumnName.COLUMN_CONSUME_YEAR).is(String.valueOf(year))
                .where(ColumnName.COLUMN_CONSUME_MONTH).is(String.valueOf(month))
                .where(ColumnName.COLUMN_CONSUME_DAY).is(String.valueOf(day))
                .where(ColumnName.COLUMN_MONEY).is(money)
                .where(ColumnName.COLUMN_INSERT_TIME).is(String.valueOf(calendar.getTime().getTime()))
                .where(ColumnName.COLUMN_DESC).is("")
                .exec();
        if (isAttach()) {
            if (success) {
                getView().onFetchInsertSuccess();
            } else {
                getView().onFetchInsertFailed();
            }
        }
    }
}

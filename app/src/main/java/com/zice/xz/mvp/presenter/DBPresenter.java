package com.zice.xz.mvp.presenter;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.zice.xz.App;
import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.ColumnName;
import com.zice.xz.mvp.contract.IMainActivityView;
import com.zice.xz.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public class DBPresenter extends BasePresenter<IMainActivityView> {

    private DataBaseHelper dbh;
    public static final String PR_YEAR = "YER";
    public static final String PR_MONTH = "MON";
    public static final String PR_DAY = "DAY";
    public static final String PR_MONEY = "MOY";
    public static final String PR_TIME = "TIE";
    public static final String PR_TYPE_ID = "TID";
    public static final String PR_CATEGORY_ID = "CID";
    public static final String PR_DESC = "DES";

    /**
     * 自动关联view和presenter
     *
     * @param iMainActivityView 关联对象
     */
    public DBPresenter(IMainActivityView iMainActivityView) {
        super(iMainActivityView);
        if (dbh == null) {
            dbh = new DataBaseHelper(App.getAppContext(), DataBaseTable.DB_NAME, null, DataBaseTable.DATABASE_VERSION_INIT);
        }
    }

    public void initConsumeCategory() {
        Log.i(TAG, "onCreate() savedInstanceState -> ");
        List<HashMap<String, String>> listItems = new ArrayList<>();
        Cursor consumeCategory = dbh.queryTable(DataBaseTable.TABLE_CONSUME_CATEGORY).exec();
        while (consumeCategory.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String name = consumeCategory.getString(consumeCategory.getColumnIndex(ColumnName.COLUMN_NAME));
            String id = consumeCategory.getString(consumeCategory.getColumnIndex(ColumnName.COLUMN_CATEGORY_ID));
            map.put(ColumnName.COLUMN_CATEGORY_ID, id);
            map.put(ColumnName.COLUMN_NAME, name);
            listItems.add(map);
        }
        if (isAttach()) {
            getView().onFetchConsumeCategory(listItems);
        }
    }

    public void initConsumeType( HashMap<String, String> selectedItem) {
        String categoryId = selectedItem.get(ColumnName.COLUMN_CATEGORY_ID);
        Cursor query = dbh.query(DataBaseTable.TABLE_CONSUME_TYPE, ColumnName.COLUMN_CATEGORY_ID, categoryId);
        List<HashMap<String, String>> listItems = new ArrayList<>();
        while (query.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String name = query.getString(query.getColumnIndex(ColumnName.COLUMN_NAME));
            String typeId = query.getString(query.getColumnIndex(ColumnName.COLUMN_TYPE_ID));
            map.put(ColumnName.COLUMN_TYPE_ID, typeId);
            map.put(ColumnName.COLUMN_NAME, name);
            listItems.add(map);
        }
        if (isAttach()) {
            getView().onFetchConsumeType(listItems);
        }
    }

    public void insertConsume( HashMap<String, String> categoryItem, HashMap<String, String> typeItem, String money) {
        Double aDouble = Double.valueOf(money);
        money = String.valueOf(NumberUtils.formatDouble(aDouble, 2, true));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String categoryId = categoryItem.get(ColumnName.COLUMN_CATEGORY_ID);
        String typeId = typeItem.get(ColumnName.COLUMN_TYPE_ID);
        boolean success = dbh.insertTable(DataBaseTable.TABLE_CONSUME_BILL)
                .where(ColumnName.COLUMN_CATEGORY_ID).is(categoryId)
                .where(ColumnName.COLUMN_TYPE_ID).is(typeId)
                .where(ColumnName.COLUMN_YEAR).is(String.valueOf(year))
                .where(ColumnName.COLUMN_MONTH).is(String.valueOf(month))
                .where(ColumnName.COLUMN_DAY).is(String.valueOf(day))
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

    public void searchConsume(final String sortType, String... condition) {
        DataBaseHelper.DBQuery dbQuery = dbh.queryTable(DataBaseTable.TABLE_CONSUME_BILL);
        for (String s : condition) {
            
            if (TextUtils.isEmpty(s)){
                continue;    
            }
            String substring = s.substring(0, 3);
            switch (substring) {
                case PR_YEAR:
                    dbQuery.where(ColumnName.COLUMN_YEAR).is(s.substring(3));
                    break;
                case PR_MONTH:
                    dbQuery.where(ColumnName.COLUMN_MONEY).is(s.substring(3));
                    break;
                case PR_DAY:
                    dbQuery.where(ColumnName.COLUMN_DAY).is(s.substring(3));
                    break;
                case PR_MONEY:
                    dbQuery.where(ColumnName.COLUMN_MONEY).is(NumberUtils.formatDouble(s.substring(3),2,true));
                    break;
                case PR_TIME:
                    dbQuery.where(ColumnName.COLUMN_INSERT_TIME).is(s.substring(3));
                    break;
                case PR_TYPE_ID:
                    dbQuery.where(ColumnName.COLUMN_TYPE_ID).is(s.substring(3));
                    break;
                case PR_CATEGORY_ID:
                    dbQuery.where(ColumnName.COLUMN_CATEGORY_ID).is(s.substring(3));
                    break;
                case PR_DESC:
                    dbQuery.where(ColumnName.COLUMN_DESC).is(s.substring(3));
                    break;
            }
        }
        Cursor cursor = dbQuery.exec();
        List<HashMap<String, String>> listItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            String money = cursor.getString(cursor.getColumnIndex(ColumnName.COLUMN_MONEY));
            String year = cursor.getString(cursor.getColumnIndex(ColumnName.COLUMN_YEAR));
            String month = cursor.getString(cursor.getColumnIndex(ColumnName.COLUMN_MONTH));
            String day = cursor.getString(cursor.getColumnIndex(ColumnName.COLUMN_DAY));
            map.put("time", year + "年" + month + "月" + day + "日");
            map.put("money", money);
            map.put("sort_time", year + (month.length() > 1 ? "0" + month : month) + (day.length() > 1 ? "0" + day : day));
            listItems.add(map);
            Log.i(TAG, "kai ---- searchConsume() money ----> " + money);
        }
        if (isAttach()) {
            Collections.sort(listItems, new Comparator<HashMap<String, String>>() {
                @Override
                public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                    String sort1;
                    String sort2;
                    switch (sortType) {
                        case PR_MONEY:
                            sort1 = lhs.get("money");
                            sort2 = rhs.get("money");
                            break;
                        default:
                            sort2 = lhs.get("sort_time");
                            sort1 = rhs.get("sort_time");
                            break;
                    }
                    return NumberUtils.formatInt(Double.parseDouble(sort1) - Double.parseDouble(sort2));
                }
            });

                getView().onFetchUpdateConsume(listItems);
        }
        
    }
}

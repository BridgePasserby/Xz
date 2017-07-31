package com.zice.xz.mvp.presenter;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.zice.xz.App;
import com.zice.xz.database.ColumnName;
import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.mvp.contract.IMainActivityView;
import com.zice.xz.utils.DataModeUtils;
import com.zice.xz.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public class MainPresenter extends BasePresenter<IMainActivityView> {

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
    public MainPresenter(IMainActivityView iMainActivityView) {
        super(iMainActivityView);
        if (dbh == null) {
            dbh = new DataBaseHelper(App.getAppContext(), DataBaseTable.DB_NAME, null, DataBaseTable.DATABASE_VERSION_INIT);
        }
    }

    public void queryConsumeCategory() {
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

    public void queryConsumeType(HashMap<String, String> selectedItem) {
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

    public void insertConsume(HashMap<String, String> categoryItem, HashMap<String, String> typeItem, String dateTime, String money, String desc) {
        Double aDouble = Double.valueOf(money);
        money = String.valueOf(NumberUtils.formatDouble3(aDouble, 2, true));
        Log.i(TAG, "insertConsume:insert money " + money);
        DataModeUtils.DataTime dataTime = DataModeUtils.parseDateTime(dateTime);
        String categoryId = categoryItem.get(ColumnName.COLUMN_CATEGORY_ID);
        String typeId = typeItem.get(ColumnName.COLUMN_TYPE_ID);
        boolean success = dbh.insertTable(DataBaseTable.TABLE_CONSUME_BILL)
                .where(ColumnName.COLUMN_CATEGORY_ID).is(categoryId)
                .where(ColumnName.COLUMN_TYPE_ID).is(typeId)
                .where(ColumnName.COLUMN_YEAR).is(String.valueOf(dataTime.year))
                .where(ColumnName.COLUMN_MONTH).is(String.valueOf(dataTime.month))
                .where(ColumnName.COLUMN_DAY).is(String.valueOf(dataTime.day))
                .where(ColumnName.COLUMN_MONEY).is(money)
                .where(ColumnName.COLUMN_INSERT_TIME).is(String.valueOf(new Date().getTime()))
                .where(ColumnName.COLUMN_DESC).is(desc == null ? "" : desc)
                .exec();
        if (isAttach()) {
            if (success) {
                getView().onFetchInsertSuccess();
            } else {
                getView().onFetchInsertFailed();
            }
        }
    }

    public void queryConsume(final String sortType, String... condition) {
        DataBaseHelper.DBQuery dbQuery = dbh.queryTable(DataBaseTable.TABLE_CONSUME_BILL);
        for (String s : condition) {
            if (TextUtils.isEmpty(s)) {
                continue;
            }
            String substring = s.substring(0, 3);
            switch (substring) {
                case PR_YEAR:
                    dbQuery.where(ColumnName.COLUMN_YEAR).is(s.substring(3));
                    break;
                case PR_MONTH:
                    dbQuery.where(ColumnName.COLUMN_MONTH).is(s.substring(3));
                    break;
                case PR_DAY:
                    dbQuery.where(ColumnName.COLUMN_DAY).is(s.substring(3));
                    break;
//                case PR_MONEY:
//                    dbQuery.where(ColumnName.COLUMN_MONEY).is(NumberUtils.formatDouble(s.substring(3),2,true));
//                    break;
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
            Log.i(TAG, "kai ---- queryConsume() money ----> " + money);
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
            getView().onFetchConsumeMoney(listItems);
        }

    }

    /**
     * 查询指定日期的消费总和
     *
     * @param dateType ColumnName.COLUMN_YEAR year
     *                 <p>ColumnName.COLUMN_MONTH year month</p> 
     *                 <p>ColumnName.COLUMN_DAY year month day</p> where dateType = year/month/day
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String queryMoneyByDate(String dateType, String year, String month, String day) {
        if (TextUtils.isEmpty(dateType)) {
            return null;
        }
        Cursor cursor = null;
        switch (dateType) {
            case ColumnName.COLUMN_YEAR:
                if (TextUtils.isEmpty(year)) {
                    return null;
                }
                cursor = dbh.queryNum(ColumnName.COLUMN_MONEY, DataBaseTable.TABLE_CONSUME_BILL,
                        ColumnName.COLUMN_YEAR, year);
                break;
            case ColumnName.COLUMN_MONTH:
                if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month)) {
                    return null;
                }
                cursor = dbh.queryNum(ColumnName.COLUMN_MONEY, DataBaseTable.TABLE_CONSUME_BILL,
                        ColumnName.COLUMN_YEAR, year,
                        ColumnName.COLUMN_MONTH, month);
                break;
            case ColumnName.COLUMN_DAY:
                if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(day)) {
                    return null;
                }
                cursor = dbh.queryNum(ColumnName.COLUMN_MONEY, DataBaseTable.TABLE_CONSUME_BILL,
                        ColumnName.COLUMN_YEAR, year,
                        ColumnName.COLUMN_MONTH, month,
                        ColumnName.COLUMN_DAY, day);
                break;
            default:
                cursor = null;
        }
        String money = "0";
        if (cursor != null && cursor.moveToNext()) {
            money = cursor.getString(cursor.getColumnIndex("result"));
        }
        return money == null ? "0" : money;
    }
}

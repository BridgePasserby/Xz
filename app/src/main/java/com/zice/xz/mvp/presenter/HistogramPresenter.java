package com.zice.xz.mvp.presenter;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.zice.xz.App;
import com.zice.xz.database.ColumnName;
import com.zice.xz.database.DataBaseHelper;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.mvp.contract.IHistogramActivityView;
import com.zice.xz.mvp.mode.DataMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zkai on 2017/7/29.
 */

public class HistogramPresenter extends BasePresenter<IHistogramActivityView> {
    private DataBaseHelper dbh;
    /**
     * 自动关联view和presenter
     *
     * @param iHistogramActivityView 关联对象
     */
    public HistogramPresenter(IHistogramActivityView iHistogramActivityView) {
        super(iHistogramActivityView);
        if (dbh == null) {
            dbh = new DataBaseHelper(App.getAppContext(), DataBaseTable.DB_NAME, null, DataBaseTable.DATABASE_VERSION_INIT);
        }
    }
    
    public void queryConsumeDate(String dateType, String year, String month, String day){
        String date = "";
        if (TextUtils.isEmpty(dateType)) {
            return ;
        }
        Cursor cursor = null;
        switch (dateType) {
            case ColumnName.COLUMN_YEAR:
                if (TextUtils.isEmpty(year)) {
                    return ;
                }
                cursor = dbh.queryConsumeData(ColumnName.COLUMN_YEAR, year);
                date = year + "年";
                break;
            case ColumnName.COLUMN_MONTH:
                if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month)) {
                    return ;
                }
                cursor = dbh.queryConsumeData(ColumnName.COLUMN_YEAR, year,ColumnName.COLUMN_MONTH, month);
                date = year + "年" + month + "月";
                break;
            case ColumnName.COLUMN_DAY:
                if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(day)) {
                    return ;
                }
                cursor = dbh.queryConsumeData(ColumnName.COLUMN_YEAR, year,
                        ColumnName.COLUMN_MONTH, month,
                        ColumnName.COLUMN_DAY, day);
                date = year + "年" + month + "月" + day + "日";
                break;
            default:
                cursor = null;
        }

        if (cursor == null) {
            getView().onFetchConsumeData(date, null);
            return;
        }
        List<DataMode.ConsumeData> consumeDatas = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("分类"));
            int money = cursor.getInt(cursor.getColumnIndex("金额"));
            consumeDatas.add(new DataMode.ConsumeData(money, name));
            Log.i(TAG, "kai ---- queryConsume() money ----> " + money);
        }
        getView().onFetchConsumeData(date, consumeDatas);
    }
    
}

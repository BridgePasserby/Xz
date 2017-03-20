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
    
    public void initConsumeClass(DataBaseHelper dbh){
        Log.i(TAG, "onCreate() savedInstanceState -> ");
        List<HashMap<String, String>> listems = new ArrayList<>();

        Cursor consumeClass = dbh.queryTable(DataBaseTable.TABLE_CONSUME_CLASS).exec();

        boolean test = dbh.insertTable(DataBaseTable.TABLE_CONSUME_BILL)
                .where(TableColumn.COLUMN_CLASS_ID).is("test")
                .exec();
        while (consumeClass.moveToNext()){
            HashMap<String, String> map = new HashMap<>();
            String name = consumeClass.getString(consumeClass.getColumnIndex(TableColumn.COLUMN_NAME));
            String id = consumeClass.getString(consumeClass.getColumnIndex(TableColumn.COLUMN_CLASS_ID));
            map.put("class_id", id);
            map.put("name",name);
            listems.add(map);
        }
        if (isAttach()) {
            viewWF.get().onFetchConsumeClass(listems);
        }
    }
}

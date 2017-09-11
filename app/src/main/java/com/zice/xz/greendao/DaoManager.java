package com.zice.xz.greendao;

import android.util.Log;

import com.zice.xz.App;
import com.zice.xz.database.DataBase;
import com.zice.xz.database.DataBaseTable;
import com.zice.xz.database.DefaultTableContent;
import com.zice.xz.dataentity.ConsumeCategory;
import com.zice.xz.dataentity.ConsumeType;
import com.zice.xz.utils.DBUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/8/23
 * description：
 */

public class DaoManager {
    private static final String TAG = "DaoManager";
    
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DaoManager mDaoManager;

    public DaoManager() {
        init();
    }

    private void init() {
        boolean isNeedInsert = false;
        File file = new File(DBUtils.getDefaultDBPath(DataBaseTable.DB_NAME));
        if (!file.exists()) {// 不存在表示第一次创建数据库，需要添加默认数据
            isNeedInsert = true;
        }
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getAppContext(), DataBase.DATA_BASE_NAME);
        mDaoMaster = new DaoMaster(devOpenHelper.getReadableDb());
        mDaoSession = mDaoMaster.newSession();

        if (isNeedInsert) {
            insertDefault();
        }
    }

    private void insertDefault() {
        Log.i(TAG, "insertDefault: insert default CATEGORY and TYPE");
        DefaultTableContent defaultTableContent = new DefaultTableContent();
        ConsumeCategoryDao consumeCategoryDao = mDaoSession.getConsumeCategoryDao();
        HashMap<String, String> consumeCategoryMap = defaultTableContent.getConsumeCategoryMap();
        Set<String> categorys = consumeCategoryMap.keySet();
        for (String key : categorys) {
            Long categoryId = Long.valueOf(key);
            ConsumeCategory consumeCategory = new ConsumeCategory(null, categoryId, consumeCategoryMap.get(key), "");
            consumeCategoryDao.insertInTx(consumeCategory);
        }
        ConsumeTypeDao consumeTypeDao = mDaoSession.getConsumeTypeDao();
        HashMap<String, String> consumeTypeMap = defaultTableContent.getConsumeTypeMap();
        Set<String> types = consumeTypeMap.keySet();
        for (String key : types) {
            String cId = key.substring(0, key.length() - 2);
            Long typeId = Long.valueOf(key);
            Long categoryId = Long.valueOf(cId);
            String typeName = consumeTypeMap.get(key);
            Log.i(TAG, "kai ---- insertDefault() typeName ----> " + typeName);
            ConsumeType consumeType = new ConsumeType(null, typeId, categoryId,  consumeTypeMap.get(key), "");
            consumeTypeDao.insertInTx(consumeType);
        }

    }

    public static DaoManager getInstance(){
        if (mDaoManager == null) {
            mDaoManager = new DaoManager();
        }
        return mDaoManager;
    }
    
    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}

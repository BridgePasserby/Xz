package com.zice.xz.mvp.contract;

import com.zice.xz.mvp.view.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public interface IMainActivityView extends BaseView {
    /**
     * 查询消费分类成功
     *
     * @param hashMapList 消费分类list
     */
    void onFetchConsumeCategory(List<HashMap<String, String>> hashMapList);

    /**
     * 查询消费类型成功
     *
     * @param hashMapList 消费类型list
     */
    void onFetchConsumeType(List<HashMap<String, String>> hashMapList);

    /**
     * 数据库添加数据成功
     */
    void onFetchInsertSuccess();

    /**
     * 数据库添加数据失败
     */
    void onFetchInsertFailed();

    /**
     * 查询消费表成功
     */
    void onFetchUpdateConsume(List<HashMap<String, String>> hashMapList);
}

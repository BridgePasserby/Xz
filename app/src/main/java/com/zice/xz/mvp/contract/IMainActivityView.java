package com.zice.xz.mvp.contract;

import com.zice.xz.mvp.presenter.BasePresenter;
import com.zice.xz.mvp.view.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public interface IMainActivityView extends BaseView  {
    void onFetchConsumeClass(List<HashMap<String, String>> hashMapList);
}

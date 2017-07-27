package com.zice.xz.mvp.view;

import android.content.Context;

import com.zice.xz.BaseActivity.DialogClickListener;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public interface BaseView {
    void showErrorDialog(String code);

    void showDateDialog(Context context, String selectTime, DialogClickListener dialogClickListener);
}

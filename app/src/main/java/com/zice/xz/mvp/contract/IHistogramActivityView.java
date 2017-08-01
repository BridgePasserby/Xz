package com.zice.xz.mvp.contract;

import com.zice.xz.mvp.mode.DataMode;
import com.zice.xz.mvp.view.BaseView;

import java.util.List;

/**
 * Created by Zkai on 2017/7/29.
 */

public interface IHistogramActivityView extends BaseView {
    public void onFetchConsumeData(String date, List<DataMode.ConsumeData> datas);
}

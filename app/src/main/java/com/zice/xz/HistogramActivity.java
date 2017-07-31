package com.zice.xz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zice.xz.database.ColumnName;
import com.zice.xz.extraview.ExtHistogramView;
import com.zice.xz.mvp.contract.IHistogramActivityView;
import com.zice.xz.mvp.mode.DataMode;
import com.zice.xz.mvp.presenter.HistogramPresenter;
import com.zice.xz.network.TestNet;

import java.util.HashMap;
import java.util.List;

public class HistogramActivity extends BaseActivity implements IHistogramActivityView {

    private HistogramPresenter presenter;
    private Button btnQuery;
    private ExtHistogramView extHvConsume;

    private Button btnTestPost;
    private TestNet testPost;
    private HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        presenter = new HistogramPresenter(this);
        initView();
        setListener();
    }

    private void setListener() {
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.queryConsumeDate(ColumnName.COLUMN_MONTH, "2017", "7", "");
            }
        });
        btnTestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = new HashMap<>();
                params.put("money", "12.00");
                params.put("name", "Test");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testPost.requestGet(params);
                        testPost.requestPost(params);
                    }
                }).start();
            }
        });
    }

    private void initView() {
        btnQuery = (Button) findViewById(R.id.btn_query);
        extHvConsume = (ExtHistogramView) findViewById(R.id.ext_hv_consume);
        btnTestPost = (Button) findViewById(R.id.btn_test_post);
    }

    @Override
    public void onFetchConsumeData(List<DataMode.ConsumeData> datas) {
        extHvConsume.refreshDate(datas);
        testPost = new TestNet();
    }
}

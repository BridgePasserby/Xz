package com.zice.xz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zice.xz.network.TestNet;

import java.util.HashMap;

public class HistogramActivity extends AppCompatActivity {

    private Button btnTestPost;
    private TestNet testPost;
    private HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        initView();
        setListener();
        testPost = new TestNet();
    }

    private void setListener() {
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
        btnTestPost = (Button) findViewById(R.id.btn_test_post);
    }
}

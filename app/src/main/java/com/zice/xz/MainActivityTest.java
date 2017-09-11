package com.zice.xz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zice.xz.database.ColumnName;
import com.zice.xz.dataentity.ConsumeBill;
import com.zice.xz.dataentity.ConsumeCategory;
import com.zice.xz.dataentity.ConsumeType;
import com.zice.xz.greendao.ConsumeBillDao;
import com.zice.xz.greendao.ConsumeCategoryDao;
import com.zice.xz.greendao.ConsumeTypeDao;
import com.zice.xz.greendao.DaoManager;
import com.zice.xz.greendao.DaoSession;
import com.zice.xz.mvp.contract.IMainActivityView;
import com.zice.xz.mvp.presenter.MainPresenter;
import com.zice.xz.utils.DBUtils;
import com.zice.xz.utils.DataModeUtils;
import com.zice.xz.utils.DeviceUtils;
import com.zice.xz.utils.NumberUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivityTest extends BaseActivity implements IMainActivityView {
    private static final String TAG = "MainActivityTest";
    private Spinner spCategory;
    private Spinner spType;
    private EditText etMoney;
    private Button btnInsert;
    private Button btnSearch;
    private ListView lvConsume;
    private EditText etSearch;
    private EditText etDesc;
    private TextView tvDayConsume;
    private TextView tvMonthConsume;
    private ScrollView scrollView;
    private TextView tvDate;
    private String selectTime;
    private Button btnActivity2;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        daoSession = DaoManager.getInstance().getDaoSession();
        initData();
        updateTopConsume();
    }

    private void initData() {
        ConsumeCategoryDao consumeCategoryDao = daoSession.getConsumeCategoryDao();
        List<ConsumeCategory> list = consumeCategoryDao.queryBuilder().build().list();
        List<HashMap<String, String>> categoryList = new ArrayList<>();
        for (ConsumeCategory consumeCategory : list) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", consumeCategory.getCategoryId() + "");
            hashMap.put("categoryName", consumeCategory.getCategoryName());
            categoryList.add(hashMap);
        }
        spCategory.setAdapter(new SimpleAdapter(this, categoryList, R.layout.class_type, new String[]{"categoryName"}, new int[]{R.id.item}));
    }

    private void initDefaultContent() {
        ConsumeCategoryDao consumeCategoryDao = daoSession.getConsumeCategoryDao();
        ConsumeTypeDao consumeTypeDao = daoSession.getConsumeTypeDao();
        for (int i = 0; i < 9; i++) {
            ConsumeCategory consumeCategory = new ConsumeCategory(null, 100L + i, "分类" + i, "");
            consumeCategoryDao.insertInTx(consumeCategory);

            ConsumeType consumeType = new ConsumeType(null, 10L + i, 100L + i, "类型" + i, "");
            consumeTypeDao.insertInTx(consumeType);
        }
    }

    private void updateTopConsume() {
        DataModeUtils.DataTime dataTime = DataModeUtils.parseDateTime(null);
        ConsumeBillDao consumeBillDao = daoSession.getConsumeBillDao();
        QueryBuilder<ConsumeBill> queryBuilder = consumeBillDao.queryBuilder()
                .where(ConsumeBillDao.Properties.Year.eq(dataTime.year))
                .where(ConsumeBillDao.Properties.Month.eq(dataTime.month));
        List<ConsumeBill> list;
        float sum = 0;
        list = queryBuilder.build().list();
        for (ConsumeBill consumeBill : list) {
            sum += consumeBill.getMoney();
        }
        tvDayConsume.setText(NumberUtils.formatDouble3(sum, 2, true));
        
        sum = 0;
        list = queryBuilder.where(ConsumeBillDao.Properties.Day.eq(dataTime.day)).build().list();
        for (ConsumeBill consumeBill : list) {
            sum += consumeBill.getMoney();
        }
        tvMonthConsume.setText(NumberUtils.formatDouble3(sum, 2, true));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels,
                        DeviceUtils.getContentHeight(this));
        lvConsume.setLayoutParams(layoutParams);
    }

    private void initView() {
        spCategory = (Spinner) findViewById(R.id.sp_category);
        spType = (Spinner) findViewById(R.id.sp_type);
        etMoney = (EditText) findViewById(R.id.et_money);
        btnInsert = (Button) findViewById(R.id.ok_insert);
        btnSearch = (Button) findViewById(R.id.btn_search);
        lvConsume = (ListView) findViewById(R.id.lv_consume);
        etSearch = (EditText) findViewById(R.id.et_search);
        etDesc = (EditText) findViewById(R.id.et_desc);
        tvDayConsume = (TextView) findViewById(R.id.tv_day_consume);
        tvMonthConsume = (TextView) findViewById(R.id.tv_month_consume);
        scrollView = (ScrollView) findViewById(R.id.scv_root);
        tvDate = (TextView) findViewById(R.id.btn_date);
        selectTime = DataModeUtils.formatDateTime(new Date());
        tvDate.setText(selectTime);
        btnActivity2 = (Button) findViewById(R.id.btn_activity2);
    }

    private void setListener() {
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) spCategory.getSelectedItem();
                ConsumeTypeDao consumeTypeDao = daoSession.getConsumeTypeDao();
                List<ConsumeType> list = consumeTypeDao.queryBuilder()
                        .where(ConsumeTypeDao.Properties.CategoryId.eq(selectedItem.get("id")))
                        .build().list();

                List<HashMap<String, String>> typeList = new ArrayList<>();
                for (ConsumeType consumeType : list) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", consumeType.getTypeId() + "");
                    hashMap.put("typeName", consumeType.getTypeName());
                    typeList.add(hashMap);
                }
                spType.setAdapter(new SimpleAdapter(MainActivityTest.this, typeList, R.layout.class_type, new String[]{"typeName"}, new int[]{R.id.item}));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> categoryItem = (HashMap<String, String>) spCategory.getSelectedItem();
                HashMap<String, String> typeItem = (HashMap<String, String>) spType.getSelectedItem();
                String money = String.valueOf(etMoney.getText());
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(MainActivityTest.this, "金额不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                DataModeUtils.DataTime dataTime = DataModeUtils.parseDateTime(selectTime);
                ConsumeBillDao consumeBillDao = daoSession.getConsumeBillDao();
                consumeBillDao.insertInTx(new ConsumeBill(null,
                        null,
                        NumberUtils.parseToLong(typeItem.get("id")),
                        typeItem.get("typeName"),
                        dataTime.year + "",
                        dataTime.month + "",
                        dataTime.day + "",
                        (float) Double.parseDouble(money),
                        System.currentTimeMillis() + "",
                        String.valueOf(etDesc.getText())));
                updateTopConsume();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mainPresenter.queryConsume(MainPresenter.PR_TIME, etSearch.getText().toString());
            }
        });
        lvConsume.setOnTouchListener(new View.OnTouchListener() {
            public int mLastY;
            public int mLastX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int moveX = x - mLastX;
                int moveY = y - mLastY;
                int scvHeight = scrollView.getHeight();
                int scvChildHeight = scrollView.getChildAt(0).getHeight();
                Log.i(TAG, "onTouch: extScrollView.getHeight()" + scvHeight);
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (moveY < 0) {// 往上滑
                            if (scvChildHeight > scvHeight && scvChildHeight - scvHeight > scrollView.getScrollY()) {// 未滑动到底部
                                scrollView.requestDisallowInterceptTouchEvent(false);
                            }

                        } else {// 往下滑
                            if (lvConsume.getFirstVisiblePosition() == 0) {
                                View lvConsumeChildAt = lvConsume.getChildAt(0);
                                if (lvConsumeChildAt == null || lvConsumeChildAt.getTop() == 0) {
                                    scrollView.requestDisallowInterceptTouchEvent(false);
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                mLastX = x;
                mLastY = y;
                return false;
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(MainActivityTest.this, selectTime, new DialogClickListener() {
                    @Override
                    public void conformListener(String dateTime) {
                        tvDate.setText(dateTime);
                        selectTime = dateTime;
                    }

                    @Override
                    public void cancelListener() {

                    }
                });
            }
        });

        btnActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityTest.this, HistogramActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBUtils.syncDataBase();
//        if (mainPresenter != null) {
//            mainPresenter.detach();
//        }
    }

    @Override
    public void onFetchConsumeCategory(List<HashMap<String, String>> hashMapList) {
        spCategory.setAdapter(new SimpleAdapter(this, hashMapList, R.layout.class_type, new String[]{"name"}, new int[]{R.id.item}));
    }

    @Override
    public void onFetchConsumeType(List<HashMap<String, String>> hashMapList) {
        spType.setAdapter(new SimpleAdapter(MainActivityTest.this, hashMapList, R.layout.class_type, new String[]{"name"}, new int[]{R.id.item}));
    }

    @Override
    public void onFetchInsertSuccess() {
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        etMoney.setText("");
        updateTopConsume();
    }

    @Override
    public void onFetchInsertFailed() {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchConsumeMoney(List<HashMap<String, String>> hashMapList) {
        if (hashMapList == null || hashMapList.size() == 0) {
            Toast.makeText(this, "无消费记录", Toast.LENGTH_SHORT).show();
            return;
        }
        lvConsume.setAdapter(new SimpleAdapter(this, hashMapList, R.layout.layout_consume, new String[]{"time", "money"}, new int[]{R.id.txt_time, R.id.txt_money}));
    }
}

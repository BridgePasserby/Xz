package com.zice.xz.extraview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zice.xz.R;
import com.zice.xz.mvp.mode.DataMode;
import com.zice.xz.utils.DeviceUtils;

import java.util.List;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/7/28
 * description：
 */

public class ExtHistogramView extends View {
    private static final String TAG = "HistogramView";
    private Paint mPaint;
    private final int[] DOT_LOCATION = {50, 400};// 圆点
    private final int[] X_AXIS = {600, 400};// x轴顶点坐标
    private final int[] Y_AXIS = {50, 100};// y轴顶点坐标
    private float yMaxMoney = 700f;// y轴表示的消费的最大值
    private int xEveryWidth = 20;
    private float yEveryHeight;
    private int yHeight;
    private int xWidth;
    private Canvas canvas;
    private List<DataMode.ConsumeData> datas;
    private int mWidth;// 控件宽度
    private int mHeight;// 控件高度
    private int yScale;// y 轴等分分数
    private int xScale;// y 轴等分分数
    private String title;


    public ExtHistogramView(Context context) {
        this(context, null);
    }

    public ExtHistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtHistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExtHistogramView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.ExtHistogramView_dotPosition:
                    String string = typedArray.getString(i);
                    if (string != null && string.split(",").length == 2) {
                        String[] split = string.split(",");
                        try {
                            DOT_LOCATION[0] = DeviceUtils.dp2px(context, Integer.parseInt(split[0])) + getPaddingLeft();
                            DOT_LOCATION[1] = DeviceUtils.dp2px(context, Integer.parseInt(split[1])) + getPaddingTop() - getPaddingBottom();// 圆点坐标
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        X_AXIS[0] = mWidth - getPaddingRight();
        X_AXIS[1] = DOT_LOCATION[1];// x轴右顶点坐标的y值
        Y_AXIS[0] = DOT_LOCATION[0];// y轴上顶点坐标的x值
        Y_AXIS[1] = 0 + getPaddingTop();

        yHeight = DOT_LOCATION[1] - Y_AXIS[1];
        xWidth = X_AXIS[0] - DOT_LOCATION[0];

        yScale = 5;
        yHeight = yHeight - (yHeight / yScale / 2);// 留出每一份一半的头
        // 每一份的高度
        yEveryHeight = yHeight / yScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.drawLine(DOT_LOCATION[0], DOT_LOCATION[1], X_AXIS[0], X_AXIS[1], mPaint);// 横坐标
        canvas.drawLine(Y_AXIS[0], Y_AXIS[1], DOT_LOCATION[0], DOT_LOCATION[1], mPaint);// 纵坐标
        if (datas == null) {
            return;
        }
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(20f);
        mPaint.setStrokeWidth(1f);
        mPaint.setAntiAlias(true);

        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                drawRectangle(i, datas.get(i).getMoney(), datas.get(i).getName(), canvas);
                Log.i(TAG, "refreshDate: 柱状图" + datas.get(i).getMoney() + datas.get(i).getName());
            }
        }

        /** 画 Y 轴 --start-- */
        // 画Y轴的值
        int yMoney = (int) (yMaxMoney / yScale);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.DKGRAY);
        Path path = new Path();
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5}, 1);
        paint.setPathEffect(effects);
        for (int i = 0; i <= yScale; i++) {
            path.moveTo(DOT_LOCATION[0], DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth());
            path.lineTo(X_AXIS[0], DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth());
            canvas.drawPath(path, paint);
            canvas.drawLine(DOT_LOCATION[0], DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth()
                    , DOT_LOCATION[0] + 10, DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth(), mPaint);
            String money = yMoney * i + "";
            float measureText = mPaint.measureText(money);
            canvas.drawText(money, DOT_LOCATION[0] - measureText, DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth(), mPaint);
            if (i == yScale) {// 画标题
                mPaint.setTextSize(30f);
                float textWidth = mPaint.measureText(title);
                int textHeight = (int) (mPaint.descent() - mPaint.ascent());
                canvas.drawText(title, DOT_LOCATION[0] + (X_AXIS[0] - DOT_LOCATION[0] - textWidth) / 2, Y_AXIS[1] + textHeight, mPaint);
                mPaint.setTextSize(20f);
            }

        }
        /** 画 Y 轴 --end-- */

        canvas.drawLine(DOT_LOCATION[0], DOT_LOCATION[1], X_AXIS[0], X_AXIS[1], mPaint);// 横坐标
        canvas.drawLine(Y_AXIS[0], Y_AXIS[1], DOT_LOCATION[0], DOT_LOCATION[1], mPaint);// 纵坐标
    }

    /**
     * 画柱状图矩形
     *
     * @param index  矩形索引
     * @param money  消费金额
     * @param name   消费名称
     * @param canvas 画布
     */
    private void drawRectangle(int index, int money, String name, Canvas canvas) {
        if (index % 2 == 0) {
            mPaint.setColor(Color.parseColor("#00ffff"));
        } else {
            mPaint.setColor(Color.parseColor("#ff00ff"));
        }
//        int startX = DOT_LOCATION[0] + xEveryWidth * (index * 2 - 2) + (int) mPaint.getStrokeWidth();// index从1开始
//        int stopX = DOT_LOCATION[0] + xEveryWidth * (index * 2 - 2 + 1) + (int) mPaint.getStrokeWidth();

        int startX = DOT_LOCATION[0] + xEveryWidth * (index * 2 + 1) + (int) mPaint.getStrokeWidth();// index从0开始
        int stopX = DOT_LOCATION[0] + xEveryWidth * (index * 2 + 2) + (int) mPaint.getStrokeWidth();
        int startY = DOT_LOCATION[1]/* - (int) mPaint.getStrokeWidth()*/;
        // stopY = y轴总高度 - 柱状图y轴高度
        int stopY = (int) ((DOT_LOCATION[1]) - (yHeight * (money / yMaxMoney)));// 根据消费数据来决定
        Log.i(TAG, "drawRectangle: " + startX + "," + stopY + "," + stopX + "," + startY);
        canvas.drawRect(startX, stopY, stopX, startY, mPaint);
        float devider = (xEveryWidth - mPaint.measureText(name.substring(0, 1))) / 2;// 文字居中
        drawText(name, startX + devider, startY + 10, 90, canvas);
    }

    private void drawText(String text, float x, float y, float angle, Canvas canvas) {
        if (angle != 0) {
            canvas.rotate(angle, x, y);
        }
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y, mPaint);
        Log.i(TAG, "drawText: text" + text);
        if (angle != 0) {
            canvas.rotate(-angle, x, y);
        }
    }

    public void refreshDate(String title, List<DataMode.ConsumeData> datas) {
        this.datas = datas;
        this.title = title;

//        int xScale = datas.size() + datas.size() - 1;// x 轴等分分数 // index从1开始
        int xScale = datas.size() + datas.size();// x 轴等分分数 // index从0开始
        if (this.xScale != xScale) {
            xWidth = xWidth - (xWidth / xScale / 2);// 留出每一份一半的头
            xEveryWidth = xWidth / xScale;
            this.xScale = xScale;
        }

        setMaxMoney(datas);
        invalidate();
    }

    private void setMaxMoney(List<DataMode.ConsumeData> datas) {
        int result = 0;
        for (DataMode.ConsumeData consumeData : datas) {
            int money = consumeData.getMoney();
            Log.i(TAG, "setMaxMoney: money" + money);
            if (money > result) {
                result = money;
            }
        }
        yMaxMoney = result;
        if (yMaxMoney == 0) {
            yMaxMoney = 700;
        }
    }
}

package com.zice.xz.extraview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    public ExtHistogramView(Context context) {
        this(context, null);
    }

    public ExtHistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtHistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        yHeight = DOT_LOCATION[1] - Y_AXIS[1];
        xWidth = X_AXIS[0] - DOT_LOCATION[0];

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExtHistogramView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.ExtHistogramView_dotPosition:
                    String string = typedArray.getString(i);
                    if (string != null) {
                        String[] split = string.split(",");
                        try {
                            DOT_LOCATION[0] = DeviceUtils.dp2px(context, Integer.parseInt(split[0]));
                            DOT_LOCATION[1] = DeviceUtils.dp2px(context, Integer.parseInt(split[1]));// 圆点坐标
                            X_AXIS[1] = DOT_LOCATION[1];// x轴右顶点坐标的y值
                            Y_AXIS[0] = DOT_LOCATION[0];// y轴上顶点坐标的x值
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
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
        // 坐标(50,400),x = 700,y = 300
        canvas.drawLine(DOT_LOCATION[0], DOT_LOCATION[1], X_AXIS[0], X_AXIS[1], mPaint);// 横坐标
        canvas.drawLine(Y_AXIS[0], Y_AXIS[1], DOT_LOCATION[0], DOT_LOCATION[1], mPaint);// 纵坐标
        /** 画 Y 轴 --start-- */
        // 画Y轴刻度
        float yScale = 5f;// y 轴等分分数
        yHeight = (int) (yHeight - (yHeight / yScale / 2));// 留出每一份一半的头
        // 每一份的高度
        yEveryHeight = yHeight / yScale;
        // 画Y轴的值
        int yMoney = (int) (yMaxMoney / yScale);
        for (int i = 0; i <= yScale; i++) {
            canvas.drawLine(DOT_LOCATION[0], DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth()
                    , DOT_LOCATION[0] + 10, DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth(), mPaint);
            String money = yMoney * i + "";
            float measureText = mPaint.measureText(money);
            canvas.drawText(money, DOT_LOCATION[0] - measureText, DOT_LOCATION[1] - (yEveryHeight * i) - (int) mPaint.getStrokeWidth(), mPaint);
        }
        /** 画 Y 轴 --end-- */
        int xScale = datas.size() + datas.size() - 1;// x 轴等分分数
        xEveryWidth = (xWidth - (xWidth / xScale / 2)) / xScale;
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                drawRectangle(i + 1, datas.get(i).getMoney(), datas.get(i).getName(), canvas);
                Log.i(TAG, "refreshDate: 柱状图" + datas.get(i).getMoney() + datas.get(i).getName());
            }
        }
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
        int startX = DOT_LOCATION[0] + xEveryWidth * (index * 2 - 2) + (int) mPaint.getStrokeWidth();
        int stopX = DOT_LOCATION[0] + xEveryWidth * (index * 2 - 2 + 1) + (int) mPaint.getStrokeWidth();
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

    public void refreshDate(List<DataMode.ConsumeData> datas) {
        this.datas = datas;
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
    }
}

package com.zice.xz.extraview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
    private final int[] X_AXIS = {750, 400};// x轴顶点坐标
    private final int[] Y_AXIS = {50, 100};// y轴顶点坐标
    private final float Y_MAX_MONEY = 700f;// y轴表示的消费的最大值
    private int rectangleWidth = 20;

    public ExtHistogramView(Context context) {
        this(context, null);
    }

    public ExtHistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtHistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1f);
        // 坐标(0,0),x = 700,y = 300
        canvas.drawLine(DOT_LOCATION[0], DOT_LOCATION[1], X_AXIS[0], X_AXIS[1], mPaint);// 横坐标
        canvas.drawLine(Y_AXIS[0], Y_AXIS[1], DOT_LOCATION[0], DOT_LOCATION[1], mPaint);// 纵坐标
        // 画刻度
        
        for (int i = 0; i < 5; i++) {
            drawRectangle(i, 100 * i, canvas);
        }
    }

    /**
     * 画柱状图矩形
     *
     * @param index  矩形索引，从 0 开始
     * @param canvas 画布
     */
    private void drawRectangle(int index, int money, Canvas canvas) {
        if (index % 2 == 0) {
            mPaint.setColor(Color.parseColor("#00ffff"));
        } else {
            mPaint.setColor(Color.parseColor("#ff00ff"));
        }
        int startX = DOT_LOCATION[0] + rectangleWidth * (index * 2);
        int stopX = DOT_LOCATION[0] + rectangleWidth * (index * 2 + 1);
        int startY = DOT_LOCATION[1] - (int) mPaint.getStrokeWidth();
        // stopY = y轴总高度 - 柱状图y轴高度
        int stopY = (int) ((DOT_LOCATION[1]) - ((DOT_LOCATION[1] - Y_AXIS[1]) * (money / Y_MAX_MONEY)));// 根据消费数据来决定
        canvas.drawRect(startX, stopY, stopX, startY, mPaint);
    }

}

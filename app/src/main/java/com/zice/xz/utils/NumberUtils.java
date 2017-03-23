package com.zice.xz.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/23
 * description：
 */

public class NumberUtils {
    /**
     * 保留两位小数，四舍五入的一个老土的方法
     * @param d
     * @return
     */
    public static double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }

    /**
     * 处理带小数的数
     * @param num 需要处理的数
     * @param decimalDigits 需要保留的小数位数
     * @param round 是否需要四舍五入
     * @return 
     */
    public static double formatDouble(double num, int decimalDigits, boolean round) {
        // 旧方法，已经不再推荐使用
//        BigDecimal bg = new BigDecimal(num).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(num).setScale(decimalDigits, round ? RoundingMode.UP : RoundingMode.DOWN);
        return bg.doubleValue();
    }

    public static String formatDouble3(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(d);
    }
    public static String formatDouble4(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    public static String formatDouble5(double d) {
        return String.format("%.2f", d);
    }
}

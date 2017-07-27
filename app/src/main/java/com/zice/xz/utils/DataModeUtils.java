package com.zice.xz.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/7/25
 * description：
 */

public class DataModeUtils {

    public static DataTime parseDateTime(String date) {
        if (TextUtils.isEmpty(date)) {
            date = formatDateTime(System.currentTimeMillis());
        }
        DataTime dataTime = new DataTime();
        Date parseTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d H:m");
        try {
            parseTime = simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parseTime);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            dataTime = new DataTime(year, month, day, hour, minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataTime;
    }

    public static String formatDateTime(long longTime) {
        if (longTime == -1) {
            longTime = System.currentTimeMillis();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d H:m");
        Date date = new Date(longTime);
        return simpleDateFormat.format(date);
    }
    public static String formatDateTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        return formatDateTime(date.getTime());
    }


    public static class DataTime {
        public int year;
        public int month;
        public int day;
        public int hour;
        public int minute;
        public int second;

        public DataTime() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d H:m");
            try {
                Date date = simpleDateFormat.parse(formatDateTime(System.currentTimeMillis()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                this.year = calendar.get(Calendar.YEAR);
                this.month = calendar.get(Calendar.MONTH);
                this.day = calendar.get(Calendar.DAY_OF_MONTH);
                this.hour = calendar.get(Calendar.HOUR_OF_DAY);
                this.minute = calendar.get(Calendar.MINUTE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public DataTime(int year, int month, int day, int hour, int minute) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
        }
    }

}

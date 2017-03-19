package com.zice.xz.database;

import android.util.Log;

import java.util.HashMap;

/**
 * Author Kevin
 * Date 2016/10/26 20:39
 * Email Bridge_passerby@outlook.com
 */
public class DataBaseTable {
    private static final String TAG = "DataBaseTable";
    /**
     * 数据库名
     */
    public static final String DB_NAME = "wz.db";
    /**
     * 消费分类表
     */
    public static final String TABLE_CONSUME_CLASS = "CONSUME_CLASS";// 如：衣食住行
    /**
     * 消费类型表
     */
    public static final String TABLE_CONSUME_TYPE = "CONSUME_TYPE";// 如：早饭，午饭
    /**
     * 消费清单表
     */
    public static final String TABLE_CONSUME_BILL = "CONSUME_BILL";

    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS ";
    
    public HashMap<Integer,String[]> consumeClassMap;
    
    public String getCreateSQL(String tableName) {
        String[] values = null;
        switch (tableName) {
            case TABLE_CONSUME_CLASS:
                values = new String[]{TableColumn.COLUMN_ID,
                        TableColumn.COLUMN_CLASS_ID,
                        TableColumn.COLUMN_NAME,
                        TableColumn.COLUMN_DESC};
                break;
            case TABLE_CONSUME_TYPE:
                values = new String[]{TableColumn.COLUMN_ID,
                        TableColumn.COLUMN_TYPE_ID,
                        TableColumn.COLUMN_NAME,
                        TableColumn.COLUMN_CLASS_ID,
                        TableColumn.COLUMN_DESC};
                break;
            case TABLE_CONSUME_BILL:
                values = new String[]{TableColumn.COLUMN_ID,
                        TableColumn.COLUMN_CONSUME_YEAR,
                        TableColumn.COLUMN_CONSUME_MONTH,
                        TableColumn.COLUMN_CONSUME_DAY,
                        TableColumn.COLUMN_MONEY,
                        TableColumn.COLUMN_INSERT_TIME,
                        TableColumn.COLUMN_TYPE_ID,
                        TableColumn.COLUMN_CLASS_ID,
                        TableColumn.COLUMN_DESC};
                break;
        }
        if (values == null) {
            return null;
        }
        StringBuilder args = new StringBuilder();
        for (String value : values) {
            if ("id".equals(value)) {
                args.append(value).append(" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,");
            }
//            else if (value.contains("id")) {
//                args.append(value).append(" INTEGER,");
//            } 
            else if (value.contains("money")) {
                args.append(value).append(" NUMERIC(12,2),");
            } else {
                args.append(value).append(" TEXT,");
            }
        }
        String sql = CREATE_SQL + tableName + " (" + args.substring(0, args.length() - 1) + " );";
        Log.i(TAG, "getCreateSQL() sql -> " + sql);
        return sql;
    }


    public final String SQL_CREATE_TABLE_CONSUME_CLASS = "CREATE TABLE IF NOT EXISTS " + TABLE_CONSUME_CLASS + " (" +
            "    id       INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
            "    name     TEXT," +
            "    class_id INTEGER" +
            "    desc        TEXT" +
            ");";
    public final String SQL_CREATE_TABLE_CONSUME_TYPE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONSUME_TYPE + " (" +
            "    id       INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
            "    name     TEXT," +
            "    type_id  INTEGER," +
            "    class_id INTEGER" +
            "    desc        TEXT" +
            ");";
    public final String SQL_CREATE_TABLE_CONSUME_BILL = "CREATE TABLE IF NOT EXISTS " + TABLE_CONSUME_BILL + " (" +
            "    id            INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
            "    consume_year  INTEGER," +
            "    consume_month INTEGER," +
            "    consume_day   INTEGER," +
            "    consume_type  INTEGER," +
            "    consume_money NUMERIC(12,2)," +
            "    insert_time   TEXT," +
            "    desc        TEXT" +
            ");";

}

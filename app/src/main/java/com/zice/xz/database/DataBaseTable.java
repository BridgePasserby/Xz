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
    public static final int DATABASE_VERSION_INIT = 1;
    /**
     * 数据库名
     */
    public static final String DB_NAME = "xz.db";
    /**
     * 消费分类表
     */
    public static final String TABLE_CONSUME_CATEGORY = "CONSUME_CATEGORY";// 如：衣食住行
    /**
     * 消费类型表
     */
    public static final String TABLE_CONSUME_TYPE = "CONSUME_TYPE";// 如：早饭，午饭
    /**
     * 消费清单表
     */
    public static final String TABLE_CONSUME_BILL = "CONSUME_BILL";

    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS ";
    
    public HashMap<Integer,String[]> consumeCategoryMap;
    
    public String getCreateSQL(String tableName) {
        String[] values = null;
        switch (tableName) {
            case TABLE_CONSUME_CATEGORY:
                values = new String[]{ColumnName.COLUMN_ID,
                        ColumnName.COLUMN_CATEGORY_ID,
                        ColumnName.COLUMN_NAME,
                        ColumnName.COLUMN_DESC};
                break;
            case TABLE_CONSUME_TYPE:
                values = new String[]{ColumnName.COLUMN_ID,
                        ColumnName.COLUMN_TYPE_ID,
                        ColumnName.COLUMN_NAME,
                        ColumnName.COLUMN_CATEGORY_ID,
                        ColumnName.COLUMN_DESC};
                break;
            case TABLE_CONSUME_BILL:
                values = new String[]{ColumnName.COLUMN_ID,
                        ColumnName.COLUMN_YEAR,
                        ColumnName.COLUMN_MONTH,
                        ColumnName.COLUMN_DAY,
                        ColumnName.COLUMN_MONEY,
                        ColumnName.COLUMN_INSERT_TIME,
                        ColumnName.COLUMN_TYPE_ID,
                        ColumnName.COLUMN_CATEGORY_ID,
                        ColumnName.COLUMN_DESC};
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

}

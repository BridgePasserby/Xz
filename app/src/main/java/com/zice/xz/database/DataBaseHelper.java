package com.zice.xz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author Kevin
 * Date 2016/10/24 22:46
 * Email Bridge_passerby@outlook.com
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private DataBaseTable dataBaseTable;
    private TableField tableField;
    public static final int DB_VERSION_INIT = 1;
    private static final String TAG = "DataBaseHelper";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dataBaseTable = new DataBaseTable();
        tableField = new TableField();
        Log.i(TAG, "DataBaseHelper() context -> " + context);
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            Log.i(TAG, "DataBaseHelper(): getWritableDatabase occur error,message -> " + e.getMessage());
            db = getReadableDatabase();
        } finally {
            Log.i(TAG, "DataBaseHelper() db -> " + db);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate() db -> " + db);
        db.execSQL(dataBaseTable.getCreateSQL(DataBaseTable.TABLE_CONSUME_CLASS));
        db.execSQL(dataBaseTable.getCreateSQL(DataBaseTable.TABLE_CONSUME_TYPE));
        db.execSQL(dataBaseTable.getCreateSQL(DataBaseTable.TABLE_CONSUME_BILL));
        initTableConsumeClass(db);
        initConsumeType(db);
    }

    private void initConsumeType(SQLiteDatabase db) {
        Set<String> keySet;
        ArrayList<String> arrayList;
        ContentValues values;
        HashMap<String, String> consumeTypeMap = tableField.getConsumeTypeMap();
        keySet = consumeTypeMap.keySet();
        arrayList = new ArrayList<>(keySet);
        values = new ContentValues();
        Collections.sort(arrayList);
        for (String id : arrayList) {
            values.put(TableColumn.COLUMN_TYPE_ID, id);
            values.put(TableColumn.COLUMN_NAME, consumeTypeMap.get(id));
            values.put(TableColumn.COLUMN_CLASS_ID, id.substring(0, id.length() - 2));
            db.insert(DataBaseTable.TABLE_CONSUME_TYPE, null, values);
        }
    }

    private void initTableConsumeClass(SQLiteDatabase db) {
        HashMap<String, String> consumeClassMap = tableField.getConsumeClassMap();
        Set<String> keySet = consumeClassMap.keySet();
        ContentValues values = new ContentValues();
        ArrayList<String> arrayList = new ArrayList<>(keySet);
        Collections.sort(arrayList);
        for (String id : arrayList) {
            Log.i(TAG, "zkai----onCreate: id ----> " + id);
            values.put(TableColumn.COLUMN_CLASS_ID, id);
            values.put(TableColumn.COLUMN_NAME, consumeClassMap.get(id));
            db.insert(DataBaseTable.TABLE_CONSUME_CLASS, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String tableName, ContentValues contentValues) {
        db.insert(tableName, null, contentValues);
    }

    /**
     * select * from {able} where {column}={columnValue}
     *
     * @param table       数据库名
     * @param column      条件列名
     * @param columnValue 条件值
     * @return cursor
     */
    public Cursor query(String table, String column, String columnValue) {


        String selection = column + " = ?";
        String[] strings = new String[]{columnValue};
        return query(false, table, null, selection, strings, null, null, null, null);

    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Query queryTable(String tableName) {
        return new Query(tableName);
    }

    public class Query extends Exec {
        String tableName;

        Query(String tableName) {
            super(tableName);
            this.tableName = tableName;
        }

        public Value where(String column) {
            return new Value(this, column);
        }
    }

    public class Value {
        String column;
        private Query query;

        Value(Query query, String column) {
            this.column = column;
            this.query = query;
        }

        public Query is(String value) {
            query.addParam(column, value);
            return query;
        }
    }

    public class Exec {
        private Map<String, String> paramMap;
        private String tableName;

        private StringBuilder selection = new StringBuilder();
        private String[] selectionArgs;

        Exec(String tableName) {
            paramMap = new HashMap<>();
            this.tableName = tableName;
        }

        void addParam(String key, String value) {
            paramMap.put(key, value);
            Log.i(TAG, "paramMap key------> " + key);
        }

        public Cursor exec() {
            if (paramMap.size() != 0) {
                Set<String> strings = paramMap.keySet();
                List<String> argList = new ArrayList<>();
                for (String s : strings) {
                    selection.append(s).append(",");
                    argList.add(paramMap.get(s));
                }
                selectionArgs = (String[]) argList.toArray();
                Log.i(TAG, "paramMap ------> " + paramMap);
                return db.query(false, tableName, null, String.valueOf(selection.substring(0, selection.length() - 1)), selectionArgs, null, null, null, null);

            } else {
                return db.query(false, tableName, null, null, null, null, null, null, null);
            }
        }
    }
}

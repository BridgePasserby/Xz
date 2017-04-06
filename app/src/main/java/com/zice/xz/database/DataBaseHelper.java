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
    private DefaultTableContent tableContent;
    private static final String TAG = "DataBaseHelper";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dataBaseTable = new DataBaseTable();
        tableContent = new DefaultTableContent();
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
        db.execSQL(dataBaseTable.getCreateSQL(DataBaseTable.TABLE_CONSUME_CATEGORY));
        db.execSQL(dataBaseTable.getCreateSQL(DataBaseTable.TABLE_CONSUME_TYPE));
        db.execSQL(dataBaseTable.getCreateSQL(DataBaseTable.TABLE_CONSUME_BILL));
        initTableConsumeCategory(db);
        initConsumeType(db);
    }

    private void initConsumeType(SQLiteDatabase db) {
        Set<String> keySet;
        ArrayList<String> arrayList;
        ContentValues values;
        HashMap<String, String> consumeTypeMap = tableContent.getConsumeTypeMap();
        keySet = consumeTypeMap.keySet();
        arrayList = new ArrayList<>(keySet);
        values = new ContentValues();
        Collections.sort(arrayList);
        for (String id : arrayList) {
            values.put(ColumnName.COLUMN_TYPE_ID, id);
            values.put(ColumnName.COLUMN_NAME, consumeTypeMap.get(id));
            values.put(ColumnName.COLUMN_CATEGORY_ID, id.substring(0, id.length() - 2));
            db.insert(DataBaseTable.TABLE_CONSUME_TYPE, null, values);
        }
    }

    private void initTableConsumeCategory(SQLiteDatabase db) {
        HashMap<String, String> consumeCategoryMap = tableContent.getConsumeCategoryMap();
        Set<String> keySet = consumeCategoryMap.keySet();
        ContentValues values = new ContentValues();
        ArrayList<String> arrayList = new ArrayList<>(keySet);
        Collections.sort(arrayList);
        for (String id : arrayList) {
            Log.i(TAG, "zkai----onCreate: id ----> " + id);
            values.put(ColumnName.COLUMN_CATEGORY_ID, id);
            values.put(ColumnName.COLUMN_NAME, consumeCategoryMap.get(id));
            db.insert(DataBaseTable.TABLE_CONSUME_CATEGORY, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * select * from {table} where {column}={columnValue}
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

    private Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public DBQuery queryTable(String tableName) {
        return new DBQuery(tableName);
    }

    public class DBQuery extends QueryExec {
//        String tableName;

        DBQuery(String tableName) {
            super(tableName);
//            this.tableName = tableName;
        }

        public QueryValue where(String column) {
            return new QueryValue(this, column);
        }
    }

    public class QueryValue {
        String column;
        private DBQuery query;

        QueryValue(DBQuery query, String column) {
            this.column = column;
            this.query = query;
        }

        public DBQuery is(String value) {
            query.addParam(column, value);
            return query;
        }
    }

    public class QueryExec {
        private Map<String, String> paramMap;
        private String tableName;

        private StringBuilder selection = new StringBuilder();
        private String[] selectionArgs;

        QueryExec(String tableName) {
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
                    selection.append(s).append("=?").append(",");
                    argList.add(paramMap.get(s));
                }
                selectionArgs = argList.toArray(new String[argList.size()]);
                return db.query(false, tableName, null, String.valueOf(selection.substring(0, selection.length() - 1)), selectionArgs, null, null, null, null);

            } else {
                return db.query(false, tableName, null, null, null, null, null, null, null);
            }
        }
    }

    public DBInsert insertTable(String tableName){
        return new DBInsert(tableName);
    }
    
    public class DBInsert extends InsertExec {
        
        DBInsert(String tableName){
            super(tableName);
        }
        
        public InsertValue where(String column){
            return new InsertValue(this,column);
        }
    }
    
    public class InsertValue{
        String column;
        private DBInsert dbInsert;
        InsertValue(DBInsert dbInsert,String column){
            this.column = column;
            this.dbInsert = dbInsert;
        }
        public DBInsert is(String value){
            dbInsert.addParam(column, value);
            return dbInsert;
        }
    }
    
    public class InsertExec{
        String tableName;
        HashMap<String,String> map ;
        
        InsertExec(String tableName){
            map = new HashMap<>();
            this.tableName = tableName;
        }
        
        void addParam(String column,String value){
            map.put(column, value);
        }
        
        public boolean exec(){
            if (map != null) {
                Set<String> strings = map.keySet();
                ContentValues contentValues = new ContentValues();
                for (String s : strings) {
                    contentValues.put(s, map.get(s));
                }
                return db.insert(tableName, null, contentValues) != -1;
            } else {
                return false;
            }
        }
    }
    

}

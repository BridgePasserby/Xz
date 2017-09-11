package com.zice.xz.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zice.xz.dataentity.ConsumeCategory;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONSUME_CATEGORY".
*/
public class ConsumeCategoryDao extends AbstractDao<ConsumeCategory, Long> {

    public static final String TABLENAME = "CONSUME_CATEGORY";

    /**
     * Properties of entity ConsumeCategory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Index = new Property(0, Long.class, "index", false, "INDEX");
        public final static Property CategoryId = new Property(1, Long.class, "categoryId", true, "_id");
        public final static Property CategoryName = new Property(2, String.class, "categoryName", false, "category_name");
        public final static Property Desc = new Property(3, String.class, "desc", false, "desc");
    }

    private DaoSession daoSession;


    public ConsumeCategoryDao(DaoConfig config) {
        super(config);
    }
    
    public ConsumeCategoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONSUME_CATEGORY\" (" + //
                "\"INDEX\" INTEGER," + // 0: index
                "\"_id\" INTEGER PRIMARY KEY ," + // 1: categoryId
                "\"category_name\" TEXT," + // 2: categoryName
                "\"desc\" TEXT);"); // 3: desc
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_CONSUME_CATEGORY_INDEX ON \"CONSUME_CATEGORY\"" +
                " (\"INDEX\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONSUME_CATEGORY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ConsumeCategory entity) {
        stmt.clearBindings();
 
        Long index = entity.getIndex();
        if (index != null) {
            stmt.bindLong(1, index);
        }
 
        Long categoryId = entity.getCategoryId();
        if (categoryId != null) {
            stmt.bindLong(2, categoryId);
        }
 
        String categoryName = entity.getCategoryName();
        if (categoryName != null) {
            stmt.bindString(3, categoryName);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ConsumeCategory entity) {
        stmt.clearBindings();
 
        Long index = entity.getIndex();
        if (index != null) {
            stmt.bindLong(1, index);
        }
 
        Long categoryId = entity.getCategoryId();
        if (categoryId != null) {
            stmt.bindLong(2, categoryId);
        }
 
        String categoryName = entity.getCategoryName();
        if (categoryName != null) {
            stmt.bindString(3, categoryName);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
    }

    @Override
    protected final void attachEntity(ConsumeCategory entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1);
    }    

    @Override
    public ConsumeCategory readEntity(Cursor cursor, int offset) {
        ConsumeCategory entity = new ConsumeCategory( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // index
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // categoryId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // categoryName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // desc
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ConsumeCategory entity, int offset) {
        entity.setIndex(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCategoryId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCategoryName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDesc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ConsumeCategory entity, long rowId) {
        entity.setCategoryId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ConsumeCategory entity) {
        if(entity != null) {
            return entity.getCategoryId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ConsumeCategory entity) {
        return entity.getCategoryId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

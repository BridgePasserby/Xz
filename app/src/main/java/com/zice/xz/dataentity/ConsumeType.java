package com.zice.xz.dataentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.LinkedHashMap;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.zice.xz.greendao.DaoSession;
import com.zice.xz.greendao.ConsumeBillDao;
import com.zice.xz.greendao.ConsumeTypeDao;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/8/23
 * description：
 */
@Entity
public class ConsumeType {
    @Index(unique = true)
    private Long index;

    @Id
    private Long typeId;
    
    @Property(nameInDb = "category_id")
    private Long categoryId;
    

    @Property(nameInDb = "type_name")
    private String typeName;

    @ToMany(referencedJoinProperty = "typeId")
    private List<ConsumeBill> consumeBills;

    @Property(nameInDb = "desc")
    private String desc;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 689379515)
    private transient ConsumeTypeDao myDao;

    @Generated(hash = 1751609197)
    public ConsumeType(Long index, Long typeId, Long categoryId, String typeName,
            String desc) {
        this.index = index;
        this.typeId = typeId;
        this.categoryId = categoryId;
        this.typeName = typeName;
        this.desc = desc;
    }

    @Generated(hash = 1387561612)
    public ConsumeType() {
    }

    public Long getIndex() {
        return this.index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 145996414)
    public List<ConsumeBill> getConsumeBills() {
        if (consumeBills == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ConsumeBillDao targetDao = daoSession.getConsumeBillDao();
            List<ConsumeBill> consumeBillsNew = targetDao
                    ._queryConsumeType_ConsumeBills(typeId);
            synchronized (this) {
                if (consumeBills == null) {
                    consumeBills = consumeBillsNew;
                }
            }
        }
        return consumeBills;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2096599066)
    public synchronized void resetConsumeBills() {
        consumeBills = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 411073961)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getConsumeTypeDao() : null;
    }


}

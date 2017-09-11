package com.zice.xz.dataentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.zice.xz.greendao.DaoSession;
import com.zice.xz.greendao.ConsumeTypeDao;
import com.zice.xz.greendao.ConsumeCategoryDao;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/8/23
 * description：
 */

@Entity
public class ConsumeCategory {
    @Index(unique = true)
    private Long index;
    
    @Id
    private Long categoryId;
    
    @Property(nameInDb = "category_name")
    private String categoryName;
    
    @ToMany(referencedJoinProperty = "categoryId")
    private List<ConsumeType> consumeTypes;
    
    @Property(nameInDb = "desc")
    private String desc;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 706803264)
    private transient ConsumeCategoryDao myDao;

    @Generated(hash = 616415852)
    public ConsumeCategory(Long index, Long categoryId, String categoryName,
            String desc) {
        this.index = index;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.desc = desc;
    }

    @Generated(hash = 1939557089)
    public ConsumeCategory() {
    }

    public Long getIndex() {
        return this.index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    @Generated(hash = 388068905)
    public List<ConsumeType> getConsumeTypes() {
        if (consumeTypes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ConsumeTypeDao targetDao = daoSession.getConsumeTypeDao();
            List<ConsumeType> consumeTypesNew = targetDao
                    ._queryConsumeCategory_ConsumeTypes(categoryId);
            synchronized (this) {
                if (consumeTypes == null) {
                    consumeTypes = consumeTypesNew;
                }
            }
        }
        return consumeTypes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1654351863)
    public synchronized void resetConsumeTypes() {
        consumeTypes = null;
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
    @Generated(hash = 942470695)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getConsumeCategoryDao() : null;
    }


}

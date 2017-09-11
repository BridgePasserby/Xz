package com.zice.xz.dataentity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import com.zice.xz.greendao.DaoSession;
import com.zice.xz.greendao.ConsumeCategoryDao;
import com.zice.xz.greendao.ConsumeTypeDao;
import com.zice.xz.greendao.ConsumeBillDao;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/8/23
 * description：
 */

@Entity
public class ConsumeBill {
    @Index(unique = true)
    private Long index;
    
    @Id
    private Long consumeBillId; 
    
    @Property(nameInDb ="type_id")
    private Long typeId;
    
    @Property(nameInDb = "type_name")
    private String typeName;
    
    @Property(nameInDb = "year")
    private String year;
    
    @Property(nameInDb = "month")
    private String month;
    
    @Property(nameInDb = "day")
    private String day;
    
    @Property(nameInDb = "money")
    private float money;
    
    @Property(nameInDb = "insert_time")
    private String insertTime;

    @Property(nameInDb = "desc")
    private String desc;

    @Generated(hash = 2106006158)
    public ConsumeBill(Long index, Long consumeBillId, Long typeId, String typeName,
            String year, String month, String day, float money, String insertTime,
            String desc) {
        this.index = index;
        this.consumeBillId = consumeBillId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.money = money;
        this.insertTime = insertTime;
        this.desc = desc;
    }

    @Generated(hash = 976838231)
    public ConsumeBill() {
    }

    public Long getIndex() {
        return this.index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getConsumeBillId() {
        return this.consumeBillId;
    }

    public void setConsumeBillId(Long consumeBillId) {
        this.consumeBillId = consumeBillId;
    }

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getMoney() {
        return this.money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getInsertTime() {
        return this.insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

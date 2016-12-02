package com.slut.recorder.db.pass.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

@DatabaseTable
public class PassLabel {

    @DatabaseField
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassLabel() {
    }

    public PassLabel(String id, String name, long createStamp, long updateStamp) {
        this.id = id;
        this.name = name;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(long createStamp) {
        this.createStamp = createStamp;
    }

    public long getUpdateStamp() {
        return updateStamp;
    }

    public void setUpdateStamp(long updateStamp) {
        this.updateStamp = updateStamp;
    }
}

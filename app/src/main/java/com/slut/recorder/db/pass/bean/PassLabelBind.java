package com.slut.recorder.db.pass.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/12/2.
 */

@DatabaseTable
public class PassLabelBind {

    @DatabaseField
    private String UUID;

    @DatabaseField
    private String passUUID;

    @DatabaseField
    private String labelUUID;

    @DatabaseField
    private long createStamp;

    public PassLabelBind() {
    }

    public PassLabelBind(String UUID, String passUUID, String labelUUID, long createStamp) {
        this.UUID = UUID;
        this.passUUID = passUUID;
        this.labelUUID = labelUUID;
        this.createStamp = createStamp;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPassUUID() {
        return passUUID;
    }

    public void setPassUUID(String passUUID) {
        this.passUUID = passUUID;
    }

    public String getLabelUUID() {
        return labelUUID;
    }

    public void setLabelUUID(String labelUUID) {
        this.labelUUID = labelUUID;
    }

    public long getCreateStamp() {
        return createStamp;
    }

    public void setCreateStamp(long createStamp) {
        this.createStamp = createStamp;
    }

    @Override
    public String toString() {
        return "PassLabelBind{" +
                "UUID='" + UUID + '\'' +
                ", passUUID='" + passUUID + '\'' +
                ", labelUUID='" + labelUUID + '\'' +
                ", createStamp=" + createStamp +
                '}';
    }
}

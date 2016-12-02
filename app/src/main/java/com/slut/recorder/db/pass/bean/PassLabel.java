package com.slut.recorder.db.pass.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

@DatabaseTable
public class PassLabel implements Parcelable{

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String name;
    @DatabaseField
    private boolean isCollapsed;
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassLabel() {
    }

    public PassLabel(String uuid, String name, boolean isCollapsed, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.name = name;
        this.isCollapsed = isCollapsed;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    protected PassLabel(Parcel in) {
        uuid = in.readString();
        name = in.readString();
        isCollapsed = in.readByte() != 0;
        createStamp = in.readLong();
        updateStamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(name);
        dest.writeByte((byte) (isCollapsed ? 1 : 0));
        dest.writeLong(createStamp);
        dest.writeLong(updateStamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PassLabel> CREATOR = new Creator<PassLabel>() {
        @Override
        public PassLabel createFromParcel(Parcel in) {
            return new PassLabel(in);
        }

        @Override
        public PassLabel[] newArray(int size) {
            return new PassLabel[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
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

    @Override
    public String toString() {
        return "PassLabel{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", isCollapsed=" + isCollapsed +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }
}

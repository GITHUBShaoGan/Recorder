package com.slut.recorder.db.pass.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 七月在线科技 on 2016/11/30.
 * 密码解锁的配置
 */

@DatabaseTable
public class PassConfig {

    public class PreferLockType{
        public static final String SIX = "six";
        public static final String PATTERN = "pattern";
        public static final String FINGERPRINT = "fingerprint";
        public static final String TEXT = "text";
    }

    @DatabaseField
    private String uuid;
    @DatabaseField
    private String sixPass;//六位数密码
    @DatabaseField
    private String patternPass;//手势密码
    @DatabaseField
    private String fingerprintPass;//指纹密码
    @DatabaseField
    private String textPass;//文字密码
    @DatabaseField
    private String preferLockType;//锁定程序的类型,1：6位数字，2：九宫格，3：指纹，4，4-128位文字密码
    @DatabaseField
    private long createStamp;
    @DatabaseField
    private long updateStamp;

    public PassConfig() {
    }

    public PassConfig(String uuid, String sixPass, String patternPass, String fingerprintPass, String textPass, String preferLockType, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.sixPass = sixPass;
        this.patternPass = patternPass;
        this.fingerprintPass = fingerprintPass;
        this.textPass = textPass;
        this.preferLockType = preferLockType;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSixPass() {
        return sixPass;
    }

    public void setSixPass(String sixPass) {
        this.sixPass = sixPass;
    }

    public String getPatternPass() {
        return patternPass;
    }

    public void setPatternPass(String patternPass) {
        this.patternPass = patternPass;
    }

    public String getFingerprintPass() {
        return fingerprintPass;
    }

    public void setFingerprintPass(String fingerprintPass) {
        this.fingerprintPass = fingerprintPass;
    }

    public String getTextPass() {
        return textPass;
    }

    public void setTextPass(String textPass) {
        this.textPass = textPass;
    }

    public String getPreferLockType() {
        return preferLockType;
    }

    public void setPreferLockType(String preferLockType) {
        this.preferLockType = preferLockType;
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

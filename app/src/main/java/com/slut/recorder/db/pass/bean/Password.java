package com.slut.recorder.db.pass.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static android.R.attr.id;

/**
 * Created by 七月在线科技 on 2016/11/24.
 */
@DatabaseTable
public class Password {

    @DatabaseField()
    public String uuid;//主键id
    @DatabaseField
    public String title;//标题
    @DatabaseField
    public String account;//账户名
    @DatabaseField
    public String password;//密码
    @DatabaseField
    public String websiteURL;//网址url
    @DatabaseField
    public String remark;//备注
    @DatabaseField
    public long createStamp;//创建时的时间戳
    @DatabaseField
    public long updateStamp;//更新时的时间戳

    public Password() {
    }

    public Password(String uuid, String title, String account, String password, String websiteURL, String remark, long createStamp, long updateStamp) {
        this.uuid = uuid;
        this.title = title;
        this.account = account;
        this.password = password;
        this.websiteURL = websiteURL;
        this.remark = remark;
        this.createStamp = createStamp;
        this.updateStamp = updateStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "Password{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", websiteURL='" + websiteURL + '\'' +
                ", remark='" + remark + '\'' +
                ", createStamp=" + createStamp +
                ", updateStamp=" + updateStamp +
                '}';
    }
}

package com.slut.recorder.create.password.m;

import android.text.TextUtils;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.dao.PassDao;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.rsa.RSAUtils;
import com.slut.recorder.utils.ResUtils;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public class PassNewModelImpl implements PassNewModel {

    @Override
    public void newPassword(String title, String account, String password, String url, String remark, OnNewPassListener onNewPassListener) {
        //标题不能为空
        if (TextUtils.isEmpty(title)) {
            onNewPassListener.onNewPassError(ResUtils.getString(R.string.error_pass_new_title_empty));
            return;
        }
        //密码不能为空
        if (TextUtils.isEmpty(password)) {
            onNewPassListener.onNewPassError(ResUtils.getString(R.string.error_pass_new_password_empty));
            return;
        }
        String encryptTitle = RSAUtils.encrypt(title);
        String encryptAccount = RSAUtils.encrypt(account);
        String encryptPassword = RSAUtils.encrypt(password);
        String encryptUrl = RSAUtils.encrypt(url);
        String encryptRemark = RSAUtils.encrypt(remark);
        if (TextUtils.isEmpty(encryptTitle) || TextUtils.isEmpty(encryptPassword)) {
            encryptTitle = title;
            encryptAccount = account;
            encryptPassword = password;
            encryptUrl = url;
            encryptRemark = remark;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        Password pwd = new Password(uuid, encryptTitle, encryptAccount, encryptPassword, encryptUrl, encryptRemark, stamp, stamp);
        boolean flag = PassDao.getInstances().insertSingle(pwd);
        if (flag) {
            onNewPassListener.onNewPassSuccess(pwd);
        } else {
            onNewPassListener.onNewPassError(ResUtils.getString(R.string.error_pass_new_insert2db));
        }
    }

}

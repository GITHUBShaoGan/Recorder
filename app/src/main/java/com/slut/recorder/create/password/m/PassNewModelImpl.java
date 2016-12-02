package com.slut.recorder.create.password.m;

import android.text.TextUtils;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.PassLabelBind;
import com.slut.recorder.db.pass.dao.PassDao;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.db.pass.dao.PassLabelBindDao;
import com.slut.recorder.db.pass.dao.PassLabelDao;
import com.slut.recorder.rsa.RSAUtils;
import com.slut.recorder.utils.Const;
import com.slut.recorder.utils.ResUtils;
import com.slut.recorder.utils.SPUtils;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public class PassNewModelImpl implements PassNewModel {

    @Override
    public void newPassword(String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, OnNewPassListener onNewPassListener) {
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
            //插入数据成功，开始插入label
            if (passLabelArrayList != null && !passLabelArrayList.isEmpty()) {
                //循环插入绑定数据
                for (PassLabel passLabel : passLabelArrayList) {
                    String bindUUID = UUID.randomUUID().toString();
                    String passUUID = uuid;
                    String labelUUID = passLabel.getUuid();
                    PassLabelBind passLabelBind = new PassLabelBind(bindUUID, passUUID, labelUUID, stamp);
                    PassLabelBindDao.getInstances().insertSingle(passLabelBind);
                }
            } else {
                //把密码加入无标签一类
                boolean isLabelNoneExists = PassLabelDao.getInstances().isLabelExistsById(Const.UUID_DEFAULT_LABEL_NONE);
                if (!isLabelNoneExists) {
                    //不存在这样的label,先删除原有的label
                    PassLabelDao.getInstances().deleteByUUID(Const.UUID_DEFAULT_LABEL_NONE);
                    //创建新label
                    PassLabel passLabel = new PassLabel(Const.UUID_DEFAULT_LABEL_NONE, Const.TITLE_DEFAULT_LABEL_NONE,true, stamp, stamp);
                    PassLabelDao.getInstances().insertSingle(passLabel);
                }
                //插入绑定数据
                PassLabelBind passLabelBind = new PassLabelBind(UUID.randomUUID().toString(), Const.UUID_DEFAULT_LABEL_NONE, uuid, stamp);
                PassLabelBindDao.getInstances().insertSingle(passLabelBind);
            }
            onNewPassListener.onNewPassSuccess(pwd);
        } else {
            onNewPassListener.onNewPassError(ResUtils.getString(R.string.error_pass_new_insert2db));
        }
    }

}

package com.slut.recorder.main.fragment.password.m;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.PassLabelBind;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.db.pass.dao.PassDao;
import com.slut.recorder.db.pass.dao.PassLabelBindDao;
import com.slut.recorder.db.pass.dao.PassLabelDao;
import com.slut.recorder.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class PassQueryModelImpl implements PassQueryModel {

    @Override
    public void query(long pageNumber, long pageSize, OnPassQueryListener onPassQueryListener) {
        if (pageNumber < 1) {
            onPassQueryListener.onPassQueryError(ResUtils.getString(R.string.error_home_pass_invalid_pageno));
            return;
        }
        if (pageSize < 1) {
            onPassQueryListener.onPassQueryError(ResUtils.getString(R.string.error_home_pass_invalid_pagesize));
            return;
        }
        List<PassLabel> passLabelList = PassLabelDao.getInstances().queryByPage(pageNumber, pageSize);
        if (passLabelList != null) {
            if (passLabelList.size() < pageSize) {
                List<List<Password>> passwordLists = new ArrayList<>();
                for (PassLabel passLabel : passLabelList) {
                    List<Password> passwordList = new ArrayList<>();
                    List<PassLabelBind> passLabelBinds = PassLabelBindDao.getInstances().queryByLabelUUID(passLabel.getUuid());
                    for (PassLabelBind passLabelBind : passLabelBinds) {
                        Password password = PassDao.getInstances().querySingleByUUID(passLabelBind.getPassUUID());
                        if (password != null) {
                            passwordList.add(password);
                        }
                    }
                    passwordLists.add(passwordList);
                }
                onPassQueryListener.onPassQueryFinished(passLabelList, passwordLists);
            } else {
                List<List<Password>> passwordLists = new ArrayList<>();
                for (PassLabel passLabel : passLabelList) {
                    List<Password> passwordList = new ArrayList<>();
                    List<PassLabelBind> passLabelBinds = PassLabelBindDao.getInstances().queryByLabelUUID(passLabel.getUuid());
                    for (PassLabelBind passLabelBind : passLabelBinds) {
                        Password password = PassDao.getInstances().querySingleByUUID(passLabelBind.getPassUUID());
                        if (password != null) {
                            passwordList.add(password);
                        }
                    }
                    passwordLists.add(passwordList);
                }
                onPassQueryListener.onPassQuerySuccess(passLabelList, passwordLists);
            }
        } else {
            //查询过程发生错误
            onPassQueryListener.onPassQueryError(ResUtils.getString(R.string.error_app_inner_error));
        }

    }


}

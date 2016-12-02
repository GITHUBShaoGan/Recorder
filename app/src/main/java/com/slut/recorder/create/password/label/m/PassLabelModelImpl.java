package com.slut.recorder.create.password.label.m;

import android.text.TextUtils;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.dao.PassLabelDao;
import com.slut.recorder.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public class PassLabelModelImpl implements PassLabelModel {

    @Override
    public void addLabel(String title, OnLabelAddListener onLabelAddListener) {
        if (TextUtils.isEmpty(title)) {
            onLabelAddListener.onLabelAddError("title cannot be empty");
            return;
        }
        if (PassLabelDao.getInstances().isLabelExists(title)) {
            onLabelAddListener.onLabelAddError("Label with " + title + " already exists");
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        PassLabel passLabel = new PassLabel(uuid, title,true, stamp, stamp);
        boolean flag = PassLabelDao.getInstances().insertSingle(passLabel);
        if (flag) {
            onLabelAddListener.onLabelAddSuccess(passLabel);
        } else {
            onLabelAddListener.onLabelAddError(ResUtils.getString(R.string.error_insert2db_exception));
        }
    }

    @Override
    public void queryLabel(long pageNo, long pageSize, ArrayList<PassLabel> passLabelArrayList, OnLabelQueryListener onLabelQueryListener) {
        if (pageNo < 1) {
            onLabelQueryListener.onLabelQueryError(ResUtils.getString(R.string.error_home_pass_invalid_pageno));
            return;
        }
        if (pageSize < 1) {
            onLabelQueryListener.onLabelQueryError(ResUtils.getString(R.string.error_home_pass_invalid_pagesize));
            return;
        }
        List<PassLabel> passLabelList = PassLabelDao.getInstances().queryByPage(pageNo, pageSize);
        if (passLabelList == null) {
            onLabelQueryListener.onLabelQueryError("Query error");
            return;
        } else {
            if (passLabelList.size() < pageSize) {
                List<Boolean> isCheckedList = new ArrayList<>();
                if (passLabelArrayList != null) {
                    for (PassLabel passLabel : passLabelList) {
                        boolean flag = false;
                        for (PassLabel passLabel1 : passLabelArrayList) {
                            if (passLabel.getUuid().equals(passLabel1.getUuid())) {
                                flag = true;
                                break;
                            }
                        }
                        isCheckedList.add(flag);
                    }
                }
                onLabelQueryListener.onLabelQueryFinish(passLabelList, isCheckedList);
            } else {
                List<Boolean> isCheckedList = new ArrayList<>();
                if (passLabelArrayList != null) {
                    for (PassLabel passLabel : passLabelList) {
                        boolean flag = false;
                        for (PassLabel passLabel1 : passLabelArrayList) {
                            if (passLabel.getUuid().equals(passLabel1.getUuid())) {
                                flag = true;
                                break;
                            }
                        }
                        isCheckedList.add(flag);
                    }
                }
                onLabelQueryListener.onLabelQuerySuccess(passLabelList, isCheckedList);
            }
        }
    }

}

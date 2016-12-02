package com.slut.recorder.main.fragment.password.m;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.db.pass.dao.PassDao;
import com.slut.recorder.utils.ResUtils;

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
        Map<String, Object> map = PassDao.getInstances().queryByPage(pageNumber, pageSize);
        if (map.containsKey("errno")) {
            int errno = (int) map.get("errno");
            switch (errno) {
                case 0:
                    //查询成功
                    List<Password> passwordList = (List<Password>) map.get("data");
                    if (passwordList.size() < pageSize) {
                        onPassQueryListener.onPassQueryFinished(passwordList);
                    } else {
                        onPassQueryListener.onPassQuerySuccess(passwordList);
                    }
                    break;
                default:
                    //查询失败
                    String msg = map.get("msg") + "";
                    onPassQueryListener.onPassQueryError(msg);
                    break;
            }
        }
    }

}

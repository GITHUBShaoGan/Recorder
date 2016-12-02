package com.slut.recorder.db.pass.dao;

import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.utils.ResUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 七月在线科技 on 2016/11/24.
 */

public class PassDao {

    private static volatile PassDao instances;
    private Dao<Password, Integer> dao;

    private PassDao() {

    }

    public static PassDao getInstances() {
        if (instances == null) {
            synchronized (PassDao.class) {
                if (instances == null) {
                    instances = new PassDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(Password.class);
    }

    public boolean insertSingle(Password password) {
        boolean flag = false;
        try {
            dao.create(password);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            return flag;
        }
    }

    public void deleteAll() {
        try {
            List<Password> passwordList = dao.queryForAll();
            if (passwordList != null && !passwordList.isEmpty()) {
                dao.delete(passwordList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> queryByPage(long pageNo, long pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        if (pageNo < 1) {
            resultMap.put("errno", -1);
            resultMap.put("msg", ResUtils.getString(R.string.error_home_pass_invalid_pageno));
            return resultMap;
        }
        if (pageSize < 1) {
            resultMap.put("errno", -2);
            resultMap.put("msg", ResUtils.getString(R.string.error_home_pass_invalid_pagesize));
            return resultMap;
        }
        QueryBuilder<Password, Integer> builder = dao.queryBuilder();
        long offSet = (pageNo - 1) * pageSize;
        try {
            builder.offset(offSet);
            builder.orderBy("createStamp", false);
            builder.limit(pageSize);
            resultMap.put("errno", 0);
            resultMap.put("data", builder.query());
        } catch (Exception e) {
            e.printStackTrace();
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                resultMap.put("errno", -3);
                resultMap.put("data", e.getLocalizedMessage());
            } else {
                resultMap.put("errno", -3);
                resultMap.put("msg", ResUtils.getString(R.string.error_insert2db_exception));
            }
        } finally {
            return resultMap;
        }
    }

    /**
     * 查询所有密码
     *
     * @return
     */
    public List<Password> queryAll() {
        try {
            return dao.queryForAll();
        } catch (Exception e) {
            return null;
        }
    }

}

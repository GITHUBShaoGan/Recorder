package com.slut.recorder.db.pass.dao;

import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
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
 * Created by 七月在线科技 on 2016/11/30.
 */

public class PassConfigDao {

    private static volatile PassConfigDao instances;
    private Dao<PassConfig, Integer> dao;

    private PassConfigDao() {

    }

    public static PassConfigDao getInstances() {
        if (instances == null) {
            synchronized (PassConfigDao.class) {
                if (instances == null) {
                    instances = new PassConfigDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(PassConfig.class);
    }

    public boolean insertSingle(PassConfig passConfig) {
        if (passConfig == null) {
            return false;
        }
        boolean flag = false;
        try {
            dao.create(passConfig);
            flag = true;
        } catch (Exception e) {
            flag = false;
        } finally {
            return flag;
        }
    }

    public void deleteAll() {
        try {
            List<PassConfig> passConfigList = dao.queryForAll();
            if (passConfigList != null && !passConfigList.isEmpty()) {
                dao.delete(passConfigList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PassConfig getConfig() {
        PassConfig passConfig = null;
        try {
            List<PassConfig> passConfigList = dao.queryForAll();
            if (passConfigList != null) {
                if (passConfigList.size() == 1) {
                    passConfig = passConfigList.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return passConfig;
        }
    }

    public Map<String, Object> queryAll() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<PassConfig> passConfigList = dao.queryForAll();
            map.put("errno", 0);
            map.put("data", passConfigList);
        } catch (Exception e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                map.put("errno", -1);
                map.put("msg", e.getLocalizedMessage());
            } else {
                map.put("errno", -1);
                map.put("msg", ResUtils.getString(R.string.error_querydb_exception));
            }
        } finally {
            return map;
        }
    }

}

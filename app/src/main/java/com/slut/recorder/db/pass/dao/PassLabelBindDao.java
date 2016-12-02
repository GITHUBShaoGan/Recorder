package com.slut.recorder.db.pass.dao;

import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.slut.recorder.App;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.PassLabelBind;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/2.
 */

public class PassLabelBindDao {

    private static volatile PassLabelBindDao instances;
    private Dao<PassLabelBind, Integer> dao;

    public static PassLabelBindDao getInstances() {
        if (instances == null) {
            synchronized (PassLabelBindDao.class) {
                if (instances == null) {
                    instances = new PassLabelBindDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(PassLabelBind.class);
    }

    public void insertSingle(PassLabelBind passLabelBind) {
        try {
            dao.create(passLabelBind);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PassLabelBind> queryByLabelUUID(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            return null;
        }
        QueryBuilder<PassLabelBind, Integer> builder = dao.queryBuilder();
        try {
            builder.where().eq("labelUUID", uuid);
            return builder.query();
        } catch (Exception e) {
            return null;
        }
    }

}

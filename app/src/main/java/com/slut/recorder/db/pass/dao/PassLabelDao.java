package com.slut.recorder.db.pass.dao;

import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.utils.ResUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public class PassLabelDao {

    private static volatile PassLabelDao instances;
    private Dao<PassLabel, Integer> dao;

    public static PassLabelDao getInstances() {
        if (instances == null) {
            synchronized (PassLabelDao.class) {
                if (instances == null) {
                    instances = new PassLabelDao();
                }
            }
        }
        return instances;
    }

    public void initDao() {
        dao = App.getDbHelper().getDao(PassLabel.class);
    }

    public boolean isLabelExists(String title) {
        boolean flag = false;
        try {
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq("name", title);
            List<PassLabel> passLabelList = builder.query();
            if (passLabelList != null && passLabelList.size() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    public void updateCollapsedByUUID(String uuid, boolean isCollapsed) {
        if (TextUtils.isEmpty(uuid)) {
            return;
        }
        UpdateBuilder<PassLabel, Integer> builder = dao.updateBuilder();
        try {
            builder.where().eq("uuid", uuid);
            builder.updateColumnValue("isCollapsed", isCollapsed);
            builder.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLabelExistsById(String uuid) {
        boolean flag = false;
        try {
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq("uuid", uuid);
            List<PassLabel> passLabelList = builder.query();
            if (passLabelList != null && passLabelList.size() > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    public List<PassLabel> queryByPage(long pageNo, long pageSize) {
        List<PassLabel> passLabelList = new ArrayList<>();
        QueryBuilder<PassLabel, Integer> builder = dao.queryBuilder();
        long offSet = (pageNo - 1) * pageSize;
        try {
            builder.offset(offSet);
            builder.orderBy("createStamp", false);
            builder.limit(pageSize);
            passLabelList = builder.query();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return passLabelList;
        }
    }

    public void deleteByUUID(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            return;
        }
        DeleteBuilder<PassLabel, Integer> builder = dao.deleteBuilder();
        try {
            builder.where().eq("uuid", uuid);
            builder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertSingle(PassLabel passtype) {
        boolean flag = false;
        try {
            dao.create(passtype);
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
            List<PassLabel> passLabelList = dao.queryForAll();
            if (passLabelList != null && !passLabelList.isEmpty()) {
                dao.delete(passLabelList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PassLabel> queryAll() {
        List<PassLabel> passLabelList = new ArrayList<>();
        try {
            passLabelList = dao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return passLabelList;
        }
    }
}

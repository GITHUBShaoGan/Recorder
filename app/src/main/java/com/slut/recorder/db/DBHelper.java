package com.slut.recorder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.slut.recorder.App;
import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.PassLabelBind;
import com.slut.recorder.db.pass.bean.Password;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 七月在线科技 on 2016/11/24.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static volatile DBHelper instances;
    private Map<String, Dao> daoMap = new HashMap<>();

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    public static synchronized DBHelper getHelper() {
        if (instances == null) {
            synchronized (DBHelper.class) {
                if (instances == null) {
                    instances = new DBHelper(App.getContext(), DBConst.DB_NAME, null, DBConst.DB_VERSION);
                }
            }
        }
        return instances;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PassConfig.class);
            TableUtils.createTable(connectionSource, PassLabel.class);
            TableUtils.createTable(connectionSource, Password.class);
            TableUtils.createTable(connectionSource, PassLabelBind.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public synchronized Dao getDao(Class cls) {
        Dao dao = null;
        String className = cls.getSimpleName();
        if (daoMap.containsKey(className)) {
            dao = daoMap.get(className);
        }
        if (dao == null) {
            try {
                dao = super.getDao(cls);
                daoMap.put(className, dao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daoMap.keySet()) {
            Dao dao = daoMap.get(key);
            dao = null;
        }
    }
}

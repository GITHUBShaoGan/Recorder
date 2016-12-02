package com.slut.recorder;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.slut.recorder.db.DBHelper;
import com.slut.recorder.db.pass.dao.PassConfigDao;
import com.slut.recorder.db.pass.dao.PassDao;
import com.slut.recorder.db.pass.dao.PassLabelBindDao;
import com.slut.recorder.db.pass.dao.PassLabelDao;
import com.slut.recorder.utils.FileUtils;

import java.util.Stack;

/**
 * Created by 七月在线科技 on 2016/11/23.
 */

public class App extends Application {

    private static volatile App instances;
    private static Context context;
    private static DBHelper dbHelper;
    private Stack<Activity> activityStack = new Stack<>();
    private int activityCount = 0;
    private static boolean isLockPassFunction = true;//默认锁定密码功能

    public static App getInstances() {
        if (instances == null) {
            synchronized (App.class) {
                if (instances == null) {
                    instances = new App();
                }
            }
        }
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        initImageLoader();
        initDbHelper();
        initDaoes();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount <= 0) {
                    //当app不可见时锁定密码功能
                    isLockPassFunction = false;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void initDbHelper() {
        App.dbHelper = DBHelper.getHelper();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(4)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(480, 800)
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(FileUtils.createImageCacheSavePath()))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private void initDaoes() {
        PassConfigDao.getInstances().initDao();
        PassDao.getInstances().initDao();
        PassLabelDao.getInstances().initDao();
        PassLabelBindDao.getInstances().initDao();
    }

    public static boolean isIsLockPassFunction() {
        return isLockPassFunction;
    }

    public static void setIsLockPassFunction(boolean isLockPassFunction) {
        App.isLockPassFunction = isLockPassFunction;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.add(activity);
            }
        }
    }

    /**
     * 退出应用，释放所有资源
     */
    public void exitApp() {
        if (dbHelper != null) {
            dbHelper.close();
        }
        if (activityStack != null && !activityStack.isEmpty()) {
            for (Activity activity : activityStack) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                    activity = null;
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

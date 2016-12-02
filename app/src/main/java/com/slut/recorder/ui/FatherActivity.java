package com.slut.recorder.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class FatherActivity extends AppCompatActivity {

    private static final int INTERVAL_EXIT = 2000;
    private static boolean isExit = false;//是否退出activity

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击两次返回键退出应用程序
     */
    private void exitBy2Click() {
        if (!isExit) {
            isExit = true;
            ToastUtils.showShort(R.string.toast_exit_app);
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            };
            timer.schedule(timerTask, INTERVAL_EXIT);
        } else {
            App.getInstances().exitApp();
        }
    }
}

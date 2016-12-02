package com.slut.recorder.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.slut.recorder.App;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public class ToastUtils {

    public static void showShort(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShort(int id) {
        Toast.makeText(App.getContext(), id, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(App.getContext(), text, Toast.LENGTH_LONG).show();
        }
    }

}

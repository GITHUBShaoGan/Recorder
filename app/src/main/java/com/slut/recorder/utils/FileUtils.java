package com.slut.recorder.utils;

import android.content.Context;
import android.os.Environment;

import com.slut.recorder.App;

import java.io.File;

import static android.R.attr.path;

/**
 * Created by 七月在线科技 on 2016/11/24.
 */

public class FileUtils {

    public static File createImageCacheSavePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = App.getContext().getExternalCacheDir().getAbsolutePath() + File.separator + "imageCache" + File.separator;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

}

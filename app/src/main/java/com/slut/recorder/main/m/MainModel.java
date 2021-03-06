package com.slut.recorder.main.m;

import com.slut.recorder.db.pass.bean.PassConfig;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public interface MainModel {

    interface OnPassAddClickListener {

        void onPassConfigExists(PassConfig passConfig);

        void onPassConfigError();

        void onPassConfigNotExists();

        void onPassAddClickError(String msg);

    }

    void onPassAddClick(OnPassAddClickListener onPassAddClickListener);

}

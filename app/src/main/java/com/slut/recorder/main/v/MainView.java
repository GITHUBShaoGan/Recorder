package com.slut.recorder.main.v;

import com.slut.recorder.db.pass.bean.PassConfig;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public interface MainView {

    void onPassConfigExists(PassConfig passConfig);

    void onPassConfigError();

    void onPassConfigNotExists();

    void onPassAddClickError(String msg);

}

package com.slut.recorder.create.password.v;

import com.slut.recorder.db.pass.bean.Password;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public interface PassNewView {

    void onNewPassSuccess(Password password);

    void onNewPassError(String msg);

}

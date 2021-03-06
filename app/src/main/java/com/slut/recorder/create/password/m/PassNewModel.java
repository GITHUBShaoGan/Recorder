package com.slut.recorder.create.password.m;

import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.Password;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public interface PassNewModel {

    interface OnNewPassListener{

        void onNewPassSuccess(Password password);

        void onNewPassError(String msg);

    }

    void newPassword(String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, OnNewPassListener onNewPassListener);

}

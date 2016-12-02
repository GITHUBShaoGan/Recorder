package com.slut.recorder.create.password.p;

import com.slut.recorder.db.pass.bean.PassLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public interface PassNewPresenter {


    void newPassword(String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList);

}

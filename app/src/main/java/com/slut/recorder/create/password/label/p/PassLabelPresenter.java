package com.slut.recorder.create.password.label.p;

import com.slut.recorder.db.pass.bean.PassLabel;

import java.util.ArrayList;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public interface PassLabelPresenter {

    void addLabel(String title);

    void queryLabel(long pageNo, long pageSize, ArrayList<PassLabel> passLabelArrayList);
}

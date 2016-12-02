package com.slut.recorder.create.password.label.v;

import com.slut.recorder.db.pass.bean.PassLabel;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public interface PassLabelView {

    void onLabelAddSuccess(PassLabel passLabel);

    void onLabelAddError(String msg);

    void onLabelQuerySuccess(List<PassLabel> passLabelList, List<Boolean> isCheckList);

    void onLabelQueryFinish(List<PassLabel> passLabelList, List<Boolean> isCheckList);

    void onLabelQueryError(String msg);

}

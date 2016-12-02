package com.slut.recorder.create.password.type.m;

import com.slut.recorder.db.pass.bean.PassLabel;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public interface PassLabelModel {

    interface OnLabelAddListener {

        void onLabelAddSuccess(PassLabel passLabel);

        void onLabelAddError(String msg);

    }

    void addLabel(String title, OnLabelAddListener onLabelAddListener);

    interface OnLabelQueryListener {

        void onLabelQuerySuccess(List<PassLabel> passLabelList);

        void onLabelQueryFinish(List<PassLabel> passLabelList);

        void onLabelQueryError(String msg);

    }

    void queryLabel(long pageNo, long pageSize, OnLabelQueryListener onLabelQueryListener);

}

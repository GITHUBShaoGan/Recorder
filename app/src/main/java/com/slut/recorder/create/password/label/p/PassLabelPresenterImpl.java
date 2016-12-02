package com.slut.recorder.create.password.label.p;

import com.slut.recorder.create.password.label.m.PassLabelModel;
import com.slut.recorder.create.password.label.m.PassLabelModelImpl;
import com.slut.recorder.create.password.label.v.PassLabelView;
import com.slut.recorder.db.pass.bean.PassLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public class PassLabelPresenterImpl implements PassLabelPresenter, PassLabelModel.OnLabelAddListener, PassLabelModel.OnLabelQueryListener {

    private PassLabelView passLabelView;
    private PassLabelModel passLabelModel;

    public PassLabelPresenterImpl(PassLabelView passLabelView) {
        this.passLabelView = passLabelView;
        this.passLabelModel = new PassLabelModelImpl();
    }

    @Override
    public void onLabelAddSuccess(PassLabel passLabel) {
        passLabelView.onLabelAddSuccess(passLabel);
    }

    @Override
    public void onLabelAddError(String msg) {
        passLabelView.onLabelAddError(msg);
    }

    @Override
    public void onLabelQuerySuccess(List<PassLabel> passLabelList, List<Boolean> isCheckList) {
        passLabelView.onLabelQuerySuccess(passLabelList,isCheckList);
    }

    @Override
    public void onLabelQueryFinish(List<PassLabel> passLabelList, List<Boolean> isCheckList) {
        passLabelView.onLabelQueryFinish(passLabelList,isCheckList);
    }

    @Override
    public void onLabelQueryError(String msg) {
        passLabelView.onLabelQueryError(msg);
    }

    @Override
    public void addLabel(String title) {
        passLabelModel.addLabel(title, this);
    }

    @Override
    public void queryLabel(long pageNo, long pageSize, ArrayList<PassLabel> passLabelArrayList) {
        passLabelModel.queryLabel(pageNo, pageSize,passLabelArrayList, this);
    }
}

package com.slut.recorder.main.fragment.password.p;

import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.main.fragment.password.m.PassQueryModel;
import com.slut.recorder.main.fragment.password.m.PassQueryModelImpl;
import com.slut.recorder.main.fragment.password.v.PassView;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class PassQueryPresenterImpl implements PassQueryPresenter, PassQueryModel.OnPassQueryListener {

    private PassView passView;
    private PassQueryModel passQueryModel;

    public PassQueryPresenterImpl(PassView passView) {
        this.passView = passView;
        this.passQueryModel = new PassQueryModelImpl();
    }

    @Override
    public void onPassQuerySuccess(List<PassLabel> passlabelList, List<List<Password>> passwordList) {
        this.passView.onPassQuerySuccess(passlabelList,passwordList);
    }

    @Override
    public void onPassQueryFinished(List<PassLabel> passlabelList, List<List<Password>> passwordList) {
        this.passView.onPassQueryFinished(passlabelList,passwordList);
    }

    @Override
    public void onPassQueryError(String msg) {
        this.passView.onPassQueryError(msg);
    }

    @Override
    public void query(long pageNumber, long pageSize) {
        this.passQueryModel.query(pageNumber, pageSize, this);
    }
}

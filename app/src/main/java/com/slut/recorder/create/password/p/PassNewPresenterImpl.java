package com.slut.recorder.create.password.p;

import com.slut.recorder.create.password.m.PassNewModel;
import com.slut.recorder.create.password.m.PassNewModelImpl;
import com.slut.recorder.create.password.v.PassNewView;
import com.slut.recorder.db.pass.bean.Password;

/**
 * Created by 七月在线科技 on 2016/11/28.
 */

public class PassNewPresenterImpl implements PassNewPresenter, PassNewModel.OnNewPassListener {

    private PassNewModel passNewModel;
    private PassNewView passNewView;

    public PassNewPresenterImpl(PassNewView passNewView) {
        this.passNewView = passNewView;
        this.passNewModel = new PassNewModelImpl();
    }

    @Override
    public void onNewPassSuccess(Password password) {
        passNewView.onNewPassSuccess(password);
    }

    @Override
    public void onNewPassError(String msg) {
        passNewView.onNewPassError(msg);
    }

    @Override
    public void newPassword(String title, String account, String password, String url, String remark) {
        passNewModel.newPassword(title, account, password, url, remark, this);
    }
}

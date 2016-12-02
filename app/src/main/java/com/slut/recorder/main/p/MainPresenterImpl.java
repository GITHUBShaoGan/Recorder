package com.slut.recorder.main.p;

import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.main.m.MainModel;
import com.slut.recorder.main.m.MainModelImpl;
import com.slut.recorder.main.v.MainView;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class MainPresenterImpl implements MainPresenter, MainModel.OnPassAddClickListener {

    private MainView mainView;
    private MainModel mainModel;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainModel = new MainModelImpl();
    }

    @Override
    public void onPassConfigExists(PassConfig passConfig) {
        mainView.onPassConfigExists(passConfig);
    }

    @Override
    public void onPassConfigError() {
        mainView.onPassConfigError();
    }

    @Override
    public void onPassConfigNotExists() {
        mainView.onPassConfigNotExists();
    }

    @Override
    public void onPassAddClickError(String msg) {
        mainView.onPassAddClickError(msg);
    }

    @Override
    public void onPassAddClick() {
        mainModel.onPassAddClick(this);
    }
}

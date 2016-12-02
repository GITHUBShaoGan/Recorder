package com.slut.recorder.main.fragment.password.m;

import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public interface PassQueryModel {

    interface OnPassQueryListener {

        void onPassQuerySuccess(List<PassLabel> passLabelList, List<List<Password>> passwordList);

        void onPassQueryFinished(List<PassLabel> passLabelList, List<List<Password>> passwordList);

        void onPassQueryError(String msg);

    }

    void query(long pageNumber, long pageSize, OnPassQueryListener onPassQueryListener);

}

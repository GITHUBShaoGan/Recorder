package com.slut.recorder.main.fragment.password.v;

import com.slut.recorder.db.pass.bean.Password;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public interface PassView {

    void onPassQuerySuccess(List<Password> passwordList);

    void onPassQueryFinished(List<Password> passwordList);

    void onPassQueryError(String msg);

}

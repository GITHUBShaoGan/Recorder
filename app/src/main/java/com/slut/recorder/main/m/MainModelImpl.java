package com.slut.recorder.main.m;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.db.pass.dao.PassConfigDao;
import com.slut.recorder.db.pass.dao.PassDao;
import com.slut.recorder.db.pass.dao.PassLabelDao;
import com.slut.recorder.utils.ResUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class MainModelImpl implements MainModel {
    @Override
    public void onPassAddClick(OnPassAddClickListener onPassAddClickListener) {
        Map<String, Object> map = PassConfigDao.getInstances().queryAll();
        if (map.containsKey("errno")) {
            int errno = (int) map.get("errno");
            switch (errno) {
                case 0:
                    List<PassConfig> passConfigs = (List<PassConfig>) map.get("data");
                    if (passConfigs != null && !passConfigs.isEmpty()) {
                        if (passConfigs.size() == 1) {
                            //数据库只有一条记录说明数据库正常，否则数据库有可能被破坏
                            onPassAddClickListener.onPassConfigExists(passConfigs.get(0));
                        } else {
                            //删除关于密码表的所有数据
                            App.setIsLockPassFunction(true);
                            PassConfigDao.getInstances().deleteAll();
                            PassDao.getInstances().deleteAll();
                            PassLabelDao.getInstances().deleteAll();
                            onPassAddClickListener.onPassConfigError();
                        }
                    } else {
                        //数据库没有记录说明还没有创建主密码，引导用户去设置主密码
                        onPassAddClickListener.onPassConfigNotExists();
                    }
                    break;
                default:
                    String msg = map.get("msg") + "";
                    onPassAddClickListener.onPassAddClickError(msg);
                    break;

            }
        } else {
            onPassAddClickListener.onPassAddClickError(ResUtils.getString(R.string.error_app_inner_error));
        }
    }
}

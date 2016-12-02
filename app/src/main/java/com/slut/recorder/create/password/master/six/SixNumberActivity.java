package com.slut.recorder.create.password.master.six;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jungly.gridpasswordview.GridPasswordView;
import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.db.pass.dao.PassConfigDao;
import com.slut.recorder.rsa.RSAUtils;
import com.slut.recorder.utils.StringUtils;
import com.slut.recorder.utils.ToastUtils;

import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slut.recorder.utils.StringUtils.MD5;

public class SixNumberActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.pswView)
    GridPasswordView passwordView;
    @BindView(R.id.error)
    TextView error;

    private int time = 0;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_number);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() < 6) {
                    error.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onInputFinish(String psw) {
                if (time == 1) {
                    if (psw.equals(password)) {
                        //校验成功
                        initPass(psw);
                    } else {
                        //两次输入不一致
                        tips.setText(R.string.tips_six_number_once);
                        time = 0;
                        password = "";
                        passwordView.clearPassword();
                        error.setText(R.string.tips_six_number_notequal);
                        error.setVisibility(View.VISIBLE);
                    }
                } else {
                    tips.setText(R.string.tips_six_number_twice);
                    password = psw;
                    time++;
                    passwordView.clearPassword();
                }
            }
        });
    }

    private void initPass(String password) {
        if (TextUtils.isEmpty(password) || password.length() != 6) {
            error.setText(R.string.tips_six_number_length_error);
            error.setVisibility(View.VISIBLE);
        }
        String preferType = RSAUtils.encrypt(PassConfig.PreferLockType.SIX);
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        String empty = RSAUtils.encrypt("");
        String pswd = RSAUtils.encrypt(password);
        Map<String, Object> map = PassConfigDao.getInstances().queryAll();
        if (map != null) {
            if (map.containsKey("errno")) {
                int errno = (int) map.get("errno");
                switch (errno) {
                    case 0:
                        //插入数据
                        PassConfigDao.getInstances().deleteAll();
                        PassConfig passConfig = new PassConfig(uuid, pswd, empty, empty, empty, preferType, stamp, stamp);
                        boolean flag = PassConfigDao.getInstances().insertSingle(passConfig);
                        if (flag) {
                            //插入数据成功
                            //解锁密码功能
                            ToastUtils.showShort(R.string.toast_password_set_success);
                            App.setIsLockPassFunction(false);
                            if (getIntent() != null) {
                                setResult(RESULT_OK, getIntent());
                                finish();
                            }
                        } else {
                            //插入数据失败
                            error.setText(R.string.error_insert2db_exception);
                            error.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        String msg = map.get("msg") + "";
                        error.setText(msg);
                        error.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                error.setText(R.string.error_app_inner_error);
                error.setVisibility(View.VISIBLE);
            }
        } else {
            error.setText(R.string.error_app_inner_error);
            error.setVisibility(View.VISIBLE);
        }
    }
}

package com.slut.recorder.unlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.db.pass.dao.PassConfigDao;
import com.slut.recorder.rsa.RSAUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnlockActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.pswView)
    GridPasswordView gridPasswordView;
    @BindView(R.id.error)
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
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
        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() < 6) {
                    error.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onInputFinish(String psw) {
                PassConfig passConfig = PassConfigDao.getInstances().getConfig();
                if (passConfig != null) {
                    if (psw.equals(RSAUtils.decrypt(passConfig.getSixPass()))) {
                        //解锁成功
                        App.setIsLockPassFunction(false);
                        setResult(RESULT_OK, getIntent());
                        finish();
                    } else {
                        error.setVisibility(View.VISIBLE);
                        error.setText(R.string.toast_unlock_not_equal);
                    }
                } else {
                    error.setVisibility(View.VISIBLE);
                    error.setText(R.string.toast_unlock_not_equal);
                }
            }
        });
    }
}

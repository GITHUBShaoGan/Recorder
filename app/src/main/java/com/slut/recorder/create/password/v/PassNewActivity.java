package com.slut.recorder.create.password.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.create.password.p.PassNewPresenter;
import com.slut.recorder.create.password.p.PassNewPresenterImpl;
import com.slut.recorder.create.password.type.v.PassLabelNewActivity;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.main.fragment.password.v.PassFragment;
import com.slut.recorder.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassNewActivity extends AppCompatActivity implements PassNewView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.til_url)
    TextInputLayout tilUrl;
    @BindView(R.id.til_remark)
    TextInputLayout tilRemark;

    private PassNewPresenter presenter;
    private static final int REQUEST_CHOOSE_TYPE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_create);
        App.getInstances().addActivity(this);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new PassNewPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogWhenExit();
            }
        });
    }

    /**
     * 新建密码
     */
    private void newPass() {
        String title = tilTitle.getEditText().getText().toString().trim();
        String account = tilAccount.getEditText().getText().toString().trim();
        String password = tilPassword.getEditText().getText().toString().trim();
        String url = tilUrl.getEditText().getText().toString().trim();
        String remark = tilRemark.getEditText().getText().toString().trim();

        presenter.newPassword(title, account, password, url, remark);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialogWhenExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNewPassSuccess(Password password) {
        //如果创建密码成功，则退出本activity
        ToastUtils.showShort(R.string.toast_new_pass_success);
        //更新首页记录
        PassFragment.getInstances().insertSingle(password);
        finish();
    }

    @Override
    public void onNewPassError(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 用户退出时弹出对话框
     */
    private void showDialogWhenExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_passnew_title_tips_exit);
        builder.setMessage(R.string.dialog_passnew_msg_tips_exit);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                newPass();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.label:
                Intent openPassTypeNew = new Intent(this, PassLabelNewActivity.class);
                startActivityForResult(openPassTypeNew, REQUEST_CHOOSE_TYPE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

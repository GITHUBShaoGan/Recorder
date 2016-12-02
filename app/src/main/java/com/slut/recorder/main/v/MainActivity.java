package com.slut.recorder.main.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.create.note.NoteCreateActivity;
import com.slut.recorder.create.password.master.MasterMethodActivity;
import com.slut.recorder.create.password.v.PassNewActivity;
import com.slut.recorder.db.pass.bean.PassConfig;
import com.slut.recorder.db.pass.dao.PassConfigDao;
import com.slut.recorder.main.adapter.TabAdapter;
import com.slut.recorder.main.fragment.note.NoteFragment;
import com.slut.recorder.main.fragment.password.v.PassFragment;
import com.slut.recorder.main.fragment.pay.PayFragment;
import com.slut.recorder.main.p.MainPresenter;
import com.slut.recorder.main.p.MainPresenterImpl;
import com.slut.recorder.ui.FatherActivity;
import com.slut.recorder.unlock.UnlockActivity;
import com.slut.recorder.utils.ResUtils;
import com.slut.recorder.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FatherActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab)
    TabLayout tab;

    private TabAdapter tabAdapter;
    private List<Fragment> fragments;
    private List<String> titles;
    private MainPresenter presenter;

    private static final int REQUEST_ADD_PASSWORD = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getInstances().addActivity(this);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initFragments();
        initTitles();
        tabAdapter = new TabAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabAdapter);
        tab.setupWithViewPager(viewPager);

        presenter = new MainPresenterImpl(this);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(NoteFragment.getInstances());
        fragments.add(PassFragment.getInstances());
        fragments.add(PayFragment.getInstances());
    }

    private void initTitles() {
        titles = new ArrayList<>();
        titles.add(ResUtils.getString(R.string.title_tab_note));
        titles.add(ResUtils.getString(R.string.title_tab_password));
        titles.add(ResUtils.getString(R.string.title_tab_pay));
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //切换后改变fab图标
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                startActivity(new Intent(MainActivity.this, NoteCreateActivity.class));
                break;
            case 1:
                presenter.onPassAddClick();
                break;
            case 2:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPassConfigExists(PassConfig passConfig) {
        //存在密码
        if (!App.isIsLockPassFunction()) {
            //未锁定
            Intent intent = new Intent(MainActivity.this, PassNewActivity.class);
            startActivityForResult(intent, REQUEST_ADD_PASSWORD);
        } else {
            //已经锁定，引导用户解锁
            Intent intent = new Intent(MainActivity.this, UnlockActivity.class);
            startActivityForResult(intent, REQUEST_ADD_PASSWORD);
        }
    }

    @Override
    public void onPassConfigError() {
        //数据库中存在多条记录
        ToastUtils.showShort(R.string.error_password_database_tamper);
    }

    @Override
    public void onPassConfigNotExists() {
        //不存在主密码，引导用户设置主密码
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_main_title_toset_master);
        builder.setMessage(R.string.dialog_main_msg_toset_master);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, MasterMethodActivity.class);
                startActivityForResult(intent, REQUEST_ADD_PASSWORD);
            }
        });
        builder.show();
    }

    @Override
    public void onPassAddClickError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD_PASSWORD:
                    startActivity(new Intent(this, PassNewActivity.class));
                    break;
            }
        }
    }
}

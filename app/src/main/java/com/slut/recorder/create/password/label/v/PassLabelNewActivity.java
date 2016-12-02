package com.slut.recorder.create.password.label.v;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.slut.recorder.R;
import com.slut.recorder.create.password.label.adapter.PassLabelAdapter;
import com.slut.recorder.create.password.label.p.PassLabelPresenter;
import com.slut.recorder.create.password.label.p.PassLabelPresenterImpl;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassLabelNewActivity extends AppCompatActivity implements PassLabelView, SwipeRefreshLayout.OnRefreshListener, PassLabelAdapter.OnCheckChangedCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private LinearLayoutManager layoutManager;
    private PassLabelAdapter adapter;

    private List<PassLabel> passLabelList;
    private List<Boolean> isCheckedList;

    private PassLabelPresenter presenter;

    private int pageNo = 1;
    private static final int PAGE_SIZE = 10;

    private int lastVisibleItem;


    private ArrayList<PassLabel> passLabelArrayList;
    public static final String EXTRA_PASS_LABEL = "labels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_label_new);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            passLabelArrayList = intent.getParcelableArrayListExtra(EXTRA_PASS_LABEL);
        } else {
            passLabelArrayList = new ArrayList<>();
        }

        presenter = new PassLabelPresenterImpl(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PassLabelAdapter();
        adapter.setOnCheckChangedCallback(this);
        recyclerView.setAdapter(adapter);
        passLabelList = new ArrayList<>();
        isCheckedList = new ArrayList<>();

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                PassLabelNewActivity.this.onRefresh();
            }
        });
        refreshLayout.setOnRefreshListener(this);
    }

    @OnClick(R.id.insert)
    void onInsertClick() {
        String title = tilTitle.getEditText().getText().toString().trim();
        presenter.addLabel(title);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent != null) {
                    intent.putParcelableArrayListExtra(EXTRA_PASS_LABEL, passLabelArrayList);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
        tilTitle.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    insert.setEnabled(true);
                    insert.setClickable(true);
                } else {
                    insert.setEnabled(false);
                    insert.setClickable(false);
                }
                tilTitle.setError("");
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem + 1 == totalItemCount) {
                        presenter.queryLabel(pageNo, PAGE_SIZE, passLabelArrayList);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onLabelAddSuccess(PassLabel passLabel) {
        //用户新建的肯定是想直接加进去的
        passLabelArrayList.add(passLabel);
        pageNo = 0;
        passLabelList.clear();
        isCheckedList.clear();
        refreshLayout.setRefreshing(true);
        onRefresh();
        //清空输入框
        tilTitle.getEditText().setText("");
    }

    @Override
    public void onLabelAddError(String msg) {
        tilTitle.setError(msg);
    }

    @Override
    public void onLabelQuerySuccess(List<PassLabel> passLabelList, List<Boolean> isCheckList) {
        this.isCheckedList.addAll(isCheckList);
        this.passLabelList.addAll(passLabelList);
        adapter.setPassLabelList(this.passLabelList);
        adapter.setIsCheckedList(this.isCheckedList);
        adapter.notifyDataSetChanged();

        pageNo++;
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLabelQueryFinish(List<PassLabel> passLabelList, List<Boolean> isCheckList) {
        this.isCheckedList.addAll(isCheckList);
        this.passLabelList.addAll(passLabelList);
        adapter.setPassLabelList(this.passLabelList);
        adapter.setIsCheckedList(this.isCheckedList);
        adapter.notifyDataSetChanged();

        //加载完所有数据
        pageNo++;
        refreshLayout.setRefreshing(false);
        adapter.addFooter();
    }

    @Override
    public void onLabelQueryError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onRefresh() {
        adapter.removeFooter();
        pageNo = 1;
        this.passLabelList.clear();
        this.isCheckedList.clear();
        presenter.queryLabel(pageNo, PAGE_SIZE, passLabelArrayList);
    }

    @Override
    public void onCheckChange(View view, int position, boolean b) {
        isCheckedList.set(position, b);
        PassLabel currentLabel = this.passLabelList.get(position);
        if (currentLabel != null) {
            if (b) {
                //添加
                boolean flag = false;
                for (PassLabel passLabel : passLabelArrayList) {
                    if (passLabel.getUuid().equals(currentLabel.getUuid())) {
                        //如果id一样表示已经存在,无需重复添加
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    passLabelArrayList.add(this.passLabelList.get(position));
                }
            } else {
                //移除
                for (PassLabel passLabel : passLabelArrayList) {
                    if (passLabel.getUuid().equals(currentLabel.getUuid())) {
                        //如果id一样则删除
                        passLabelArrayList.remove(passLabel);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回数据
            Intent intent = getIntent();
            if (intent != null) {
                intent.putParcelableArrayListExtra(EXTRA_PASS_LABEL, passLabelArrayList);
                setResult(RESULT_OK, intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

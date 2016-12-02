package com.slut.recorder.create.password.type.v;

import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.slut.recorder.R;
import com.slut.recorder.create.password.type.adapter.PassLabelAdapter;
import com.slut.recorder.create.password.type.p.PassLabelPresenter;
import com.slut.recorder.create.password.type.p.PassLabelPresenterImpl;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassLabelNewActivity extends AppCompatActivity implements PassLabelView, SwipeRefreshLayout.OnRefreshListener {

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

    private PassLabelPresenter presenter;

    private int pageNo = 1;
    private static final int PAGE_SIZE = 10;

    private int lastVisibleItem;

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

        presenter = new PassLabelPresenterImpl(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PassLabelAdapter();
        recyclerView.setAdapter(adapter);
        passLabelList = new ArrayList<>();

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
                        presenter.queryLabel(pageNo, PAGE_SIZE);
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
        pageNo = 0;
        passLabelList.clear();
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
    public void onLabelQuerySuccess(List<PassLabel> passLabelList) {
        this.passLabelList.addAll(passLabelList);
        adapter.setPassLabelList(this.passLabelList);
        adapter.notifyDataSetChanged();

        pageNo++;
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLabelQueryFinish(List<PassLabel> passLabelList) {
        this.passLabelList.addAll(passLabelList);
        adapter.setPassLabelList(this.passLabelList);
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
        presenter.queryLabel(pageNo, PAGE_SIZE);
    }
}

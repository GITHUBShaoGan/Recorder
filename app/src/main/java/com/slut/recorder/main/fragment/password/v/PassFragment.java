package com.slut.recorder.main.fragment.password.v;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.main.fragment.password.adapter.HomeLabelAdapter;
import com.slut.recorder.main.fragment.password.p.PassQueryPresenter;
import com.slut.recorder.main.fragment.password.p.PassQueryPresenterImpl;
import com.slut.recorder.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassFragment extends Fragment implements PassView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private HomeLabelAdapter adapter;

    private View rootView;

    private static volatile PassFragment instances;
    private List<PassLabel> passLabelList = new ArrayList<>();
    private List<List<Password>> passwordLists = new ArrayList<>();
    private long pageNo = 1;//页码，从1开始
    private long pageSize = 10;//页面大小

    private PassQueryPresenter passQueryPresenter;

    private int lastVisibleItem;

    public static PassFragment getInstances() {
        if (instances == null) {
            synchronized (PassFragment.class) {
                if (instances == null) {
                    instances = new PassFragment();
                }
            }
        }
        return instances;
    }

    public PassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pass, container, false);
        }
        ButterKnife.bind(this, rootView);
        initView();
        initListener();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeLabelAdapter(getActivity());
        adapter.setPassLabelList(passLabelList);
        recyclerView.setAdapter(adapter);
        passQueryPresenter = new PassQueryPresenterImpl(this);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                PassFragment.this.onRefresh();
            }
        });
        refreshLayout.setOnRefreshListener(this);
    }

    private void initListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem + 1 == totalItemCount) {
                        passQueryPresenter.query(pageNo, pageSize);
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
    public void onPassQuerySuccess(List<PassLabel> passlabelList, List<List<Password>> passwordList) {
        this.passLabelList.addAll(passlabelList);
        this.passwordLists.addAll(passwordList);
        adapter.setPassLabelList(this.passLabelList);
        adapter.setPassLists(this.passwordLists);
        adapter.notifyDataSetChanged();
        pageNo++;

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPassQueryFinished(List<PassLabel> passlabelList, List<List<Password>> passwordList) {
        this.passLabelList.addAll(passlabelList);
        this.passwordLists.addAll(passwordList);
        adapter.setPassLabelList(this.passLabelList);
        adapter.setPassLists(this.passwordLists);
        adapter.notifyDataSetChanged();
        pageNo++;

        refreshLayout.setRefreshing(false);

        adapter.addFooter();
    }

    @Override
    public void onPassQueryError(String msg) {
        ToastUtils.showShort(msg);

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        adapter.removeFooter();
        pageNo = 1;
        passLabelList.clear();
        passwordLists.clear();
        passQueryPresenter.query(pageNo, pageSize);
    }
}

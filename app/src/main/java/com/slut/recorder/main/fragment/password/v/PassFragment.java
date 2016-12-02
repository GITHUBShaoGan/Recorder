package com.slut.recorder.main.fragment.password.v;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.main.fragment.password.adapter.HomePassAdapter;
import com.slut.recorder.main.fragment.password.p.PassQueryPresenter;
import com.slut.recorder.main.fragment.password.p.PassQueryPresenterImpl;
import com.slut.recorder.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassFragment extends Fragment implements PassView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private HomePassAdapter adapter;

    private View rootView;

    private static volatile PassFragment instances;
    private List<Password> passwordList = new ArrayList<>();
    private long pageNo = 1;//页码，从1开始
    private long pageSize = 10;//页面大小

    private PassQueryPresenter passQueryPresenter;

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
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomePassAdapter();
        adapter.setPasswordList(passwordList);
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

    public void insertSingle(Password password) {
        if (password != null && adapter != null) {
            this.passwordList.add(0, password);
            adapter.setPasswordList(passwordList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPassQuerySuccess(List<Password> passwordList) {
        this.passwordList.addAll(passwordList);
        adapter.setPasswordList(this.passwordList);
        adapter.notifyDataSetChanged();
        pageNo++;

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPassQueryFinished(List<Password> passwordList) {
        this.passwordList.addAll(passwordList);
        adapter.setPasswordList(this.passwordList);
        adapter.notifyDataSetChanged();
        pageNo++;

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onPassQueryError(String msg) {
        ToastUtils.showShort(msg);

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        passwordList.clear();
        passQueryPresenter.query(pageNo, pageSize);
    }
}

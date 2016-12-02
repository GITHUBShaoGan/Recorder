package com.slut.recorder.main.fragment.pay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slut.recorder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {

    private View rootView;
    private static volatile PayFragment instances;

    public static PayFragment getInstances() {
        if (instances == null) {
            synchronized (PayFragment.class) {
                if (instances == null) {
                    instances = new PayFragment();
                }
            }
        }
        return instances;
    }

    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pay, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

}

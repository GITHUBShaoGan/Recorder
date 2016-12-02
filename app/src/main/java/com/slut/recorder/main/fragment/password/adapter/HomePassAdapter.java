package com.slut.recorder.main.fragment.password.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.rsa.RSAUtils;
import com.slut.recorder.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class HomePassAdapter extends RecyclerView.Adapter<HomePassAdapter.ViewHolder> {

    private List<Password> passwordList;

    public HomePassAdapter() {
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_home_pass, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (passwordList != null && !passwordList.isEmpty() && position < passwordList.size()) {
            Password password = passwordList.get(position);
            if (password != null) {
                String title = RSAUtils.decrypt(password.getTitle());
                if (!TextUtils.isEmpty(title)) {
                    holder.avatar.setText(title.charAt(0) + "");
                } else {
                    holder.avatar.setText("X");
                }
                holder.title.setText(title + "");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (passwordList != null && !passwordList.isEmpty()) {
            return passwordList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

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
 * Created by 七月在线科技 on 2016/12/2.
 */

public class HomePassAdapter extends RecyclerView.Adapter<HomePassAdapter.ItemViewHolder> {

    private List<Password> passwordList;
    private boolean isCollapsed;

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_home_pass, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (passwordList != null && !passwordList.isEmpty() && position < passwordList.size()) {
            Password password = passwordList.get(position);
            if (password != null) {
                String account = RSAUtils.decrypt(password.getAccount());
                if (TextUtils.isEmpty(account)) {
                    String title = RSAUtils.decrypt(password.getTitle());
                    if (TextUtils.isEmpty(title)) {
                        holder.avatar.setText("X");
                        holder.account.setText("X");
                    } else {
                        holder.avatar.setText(title.charAt(0) + "");
                        holder.account.setText(title);
                    }
                } else {
                    holder.avatar.setText(account.charAt(0) + "");
                    holder.account.setText(account);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(!isCollapsed){
            if (passwordList != null && !passwordList.isEmpty()) {
                return passwordList.size();
            }
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.account)
        TextView account;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

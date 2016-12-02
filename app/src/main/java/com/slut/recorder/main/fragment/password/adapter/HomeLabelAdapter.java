package com.slut.recorder.main.fragment.password.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.db.pass.bean.Password;
import com.slut.recorder.db.pass.dao.PassLabelDao;
import com.slut.recorder.utils.TimeUtils;
import com.slut.recorder.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

/**
 * Created by 七月在线科技 on 2016/11/30.
 */

public class HomeLabelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PassLabel> passLabelList;
    private List<List<Password>> passLists;
    private int footerViewSize = 0;
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;

    public HomeLabelAdapter(Context context) {
        this.context = context;
    }

    public void addFooter() {
        footerViewSize = 1;
    }

    public void removeFooter() {
        footerViewSize = 0;
    }

    public List<PassLabel> getPassLabelList() {
        return passLabelList;
    }

    public void setPassLabelList(List<PassLabel> passLabelList) {
        this.passLabelList = passLabelList;
    }

    public List<List<Password>> getPassLists() {
        return passLists;
    }

    public void setPassLists(List<List<Password>> passLists) {
        this.passLists = passLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_home_label, parent, false);
                viewHolder = new HomeLabelAdapter.ItemViewHolder(itemView);
                break;
            case TYPE_FOOT:
                View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer_recyclerview_end, parent, false);
                viewHolder = new HomeLabelAdapter.FooterViewHolder(footerView);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (footerViewSize == 1 && position == getItemCount() - 1) {
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (passLabelList != null && !passLabelList.isEmpty() && position < passLabelList.size()) {
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            PassLabel passLabel = passLabelList.get(position);
            if (passLabel != null) {
                String title = passLabel.getName();
                if (TextUtils.isEmpty(title)) {
                    title = "X";
                }
                holder.avatar.setText(title.charAt(0) + "");
                holder.title.setText(title + "");
                holder.stamp.setText(TimeUtils.interval2Str(passLabel.getUpdateStamp(), System.currentTimeMillis()));
                if (passLabelList.get(position).isCollapsed()) {
                    holder.showOrHide.setImageResource(R.mipmap.ic_expand_less_black);
                } else {
                    holder.showOrHide.setImageResource(R.mipmap.ic_expand_more_black);
                }
                holder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(context, view);
                        popupMenu.getMenuInflater().inflate(R.menu.home_pass_more,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.add:
                                        break;
                                    case R.id.edit:
                                        break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }
        }
        if (passLists != null && !passLists.isEmpty() && position < passLists.size()) {
            List<Password> passwordList = passLists.get(position);
            if (passwordList != null) {
                final ItemViewHolder holder = (ItemViewHolder) viewHolder;
                holder.count.setText(passwordList.size() + "");
                final HomePassAdapter homePassAdapter = new HomePassAdapter();
                homePassAdapter.setPasswordList(passwordList);
                homePassAdapter.setCollapsed(passLabelList.get(position).isCollapsed());
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));
                holder.recyclerView.setAdapter(homePassAdapter);
                holder.showOrHide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (passLabelList.get(position).isCollapsed()) {
                            holder.showOrHide.setImageResource(R.mipmap.ic_expand_less_black);
                        } else {
                            holder.showOrHide.setImageResource(R.mipmap.ic_expand_more_black);
                        }
                        homePassAdapter.setCollapsed(!passLabelList.get(position).isCollapsed());
                        homePassAdapter.notifyDataSetChanged();
                        PassLabelDao.getInstances().updateCollapsedByUUID(passLabelList.get(position).getUuid(), !passLabelList.get(position).isCollapsed());
                        passLabelList.get(position).setCollapsed(!passLabelList.get(position).isCollapsed());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (passLabelList != null && !passLabelList.isEmpty()) {
            return passLabelList.size() + footerViewSize;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;
        @BindView(R.id.stamp)
        TextView stamp;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.showOrHide)
        ImageView showOrHide;
        @BindView(R.id.more)
        ImageView more;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}

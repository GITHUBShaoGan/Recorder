package com.slut.recorder.create.password.label.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.slut.recorder.App;
import com.slut.recorder.R;
import com.slut.recorder.db.pass.bean.PassLabel;
import com.slut.recorder.utils.TimeUtils;
import com.slut.recorder.widget.CircleTextImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public class PassLabelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private int footerViewSize = 0;
    private List<PassLabel> passLabelList;
    private List<Boolean> isCheckedList;
    private OnCheckChangedCallback onCheckChangedCallback;

    public void setOnCheckChangedCallback(OnCheckChangedCallback onCheckChangedCallback) {
        this.onCheckChangedCallback = onCheckChangedCallback;
    }

    public List<PassLabel> getPassLabelList() {
        return passLabelList;
    }

    public void setPassLabelList(List<PassLabel> passLabelList) {
        this.passLabelList = passLabelList;
    }

    public List<Boolean> getIsCheckedList() {
        return isCheckedList;
    }

    public void setIsCheckedList(List<Boolean> isCheckedList) {
        this.isCheckedList = isCheckedList;
    }

    public void addFooter() {
        footerViewSize = 1;
    }

    public void removeFooter() {
        footerViewSize = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_pass_label, parent, false);
                viewHolder = new ItemViewHolder(itemView);
                break;
            case TYPE_FOOT:
                View footerView = LayoutInflater.from(App.getContext()).inflate(R.layout.footer_recyclerview_end, parent, false);
                viewHolder = new FooterViewHolder(footerView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //显示基本信息
        if (passLabelList != null && !passLabelList.isEmpty() && position < passLabelList.size()) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            PassLabel passLabel = passLabelList.get(position);
            if (passLabel != null) {
                String title = passLabel.getName();
                if (!TextUtils.isEmpty(title)) {
                    viewHolder.avatar.setText(title.charAt(0) + "");
                }
                viewHolder.title.setText(title + "");
                viewHolder.updateTime.setText(TimeUtils.interval2Str(passLabel.getUpdateStamp(), System.currentTimeMillis()));
            }
        }
        //checkbox点击显示与处理
        if (isCheckedList != null && !isCheckedList.isEmpty() && position < isCheckedList.size()) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onCheckChangedCallback.onCheckChange(compoundButton, position, b);
                }
            });
            viewHolder.checkBox.setChecked(isCheckedList.get(position));
        }
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
    public int getItemCount() {
        if (passLabelList != null && !passLabelList.isEmpty()) {
            return footerViewSize + passLabelList.size();
        }
        return footerViewSize;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircleTextImageView avatar;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.update_time)
        TextView updateTime;
        @BindView(R.id.checkbox)
        CheckBox checkBox;

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

    public interface OnCheckChangedCallback {
        void onCheckChange(View view, int position, boolean b);
    }

}

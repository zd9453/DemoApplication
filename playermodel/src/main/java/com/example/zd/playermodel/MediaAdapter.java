package com.example.zd.playermodel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zd.playermodel.bean.MediaBean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * use to do
 *
 * @author zhangdong on 2018/3/6 0006.
 * @version 1.0
 * @see .
 * @since 1.0
 */

class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.holder> {

    private List<MediaBean> lists;
    private SimpleDateFormat dateFormat;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MediaAdapter(List<MediaBean> lists) {
        this.lists = lists;
        this.dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new holder(inflate);
    }

    @Override
    public void onBindViewHolder(holder holder, final int position) {
        final MediaBean mediaBean = lists.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClickListener)
                    itemClickListener.clickItem(position, mediaBean);
            }
        });

        holder.name.setText(mediaBean.getName());
        holder.time.setText("时长：" + mediaBean.getTime() + "毫秒");
        holder.url.setText("路径：" + mediaBean.getDateUrl());

        String format = dateFormat.format(Long.valueOf(mediaBean.getAddTime()) * 1000L);
        holder.addTime.setText("添加时间：" + format);

    }

    @Override
    public int getItemCount() {
        return null != lists ? lists.size() : 0;
    }

    class holder extends RecyclerView.ViewHolder {
        private TextView name, time, url, addTime;

        public holder(View itemView) {
            super(itemView);
            name = ((TextView) itemView.findViewById(R.id.name));
            time = ((TextView) itemView.findViewById(R.id.time));
            url = ((TextView) itemView.findViewById(R.id.dateUrl));
            addTime = ((TextView) itemView.findViewById(R.id.addTime));
        }
    }

    public interface ItemClickListener {
        void clickItem(int position, MediaBean bean);
    }
}

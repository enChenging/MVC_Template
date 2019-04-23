package com.release.mvc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.release.mvc.R;
import com.release.mvc.base.MyBaseAdapter;
import com.release.mvc.bean.ImprotantNewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class ImportantNewsAdapter extends MyBaseAdapter<ImprotantNewsBean.NewslistBean> {

    public ImportantNewsAdapter(Context context, List<ImprotantNewsBean.NewslistBean> datas) {
        super(context, datas);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_important_news, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImprotantNewsBean.NewslistBean bean = mDatas.get(position);
        Glide.with(mContext).load(bean.getPicUrl()).optionalCenterCrop().into(holder.iv_tuijian);
        holder.tv_tuijian_title.setText(bean.getTitle());
        holder.tv_tuijian_time.setText(bean.getCtime());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_tuijian)
        ImageView iv_tuijian;
        @BindView(R.id.tv_tuijian_title)
        TextView tv_tuijian_title;
        @BindView(R.id.tv_tuijian_time)
        TextView tv_tuijian_time;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

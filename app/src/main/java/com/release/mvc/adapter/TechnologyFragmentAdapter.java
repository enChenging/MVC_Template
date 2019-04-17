package com.release.mvc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.release.mvc.R;
import com.release.mvc.base.MyBaseAdapter;
import com.release.mvc.bean.OfficalEventBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class TechnologyFragmentAdapter extends MyBaseAdapter<OfficalEventBean.DataBean> {
    public TechnologyFragmentAdapter(Context context, List<OfficalEventBean.DataBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_important_news2, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mDatas.get(position).getPath()).into(holder.iv);

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.iv)
        ImageView iv;

        public ViewHolder(View view) {
            ButterKnife.bind(view);
        }
    }
}

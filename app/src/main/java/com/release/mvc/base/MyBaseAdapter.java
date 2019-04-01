package com.release.mvc.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter {
    public  Context mContext;
    public  List<T> mDatas;
    public  LayoutInflater mInflater;

    public MyBaseAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}

package com.release.mvc.fragment;

import android.view.View;
import android.widget.ListView;

import com.release.mvc.R;
import com.release.mvc.adapter.BusinessFragmentAdapter;
import com.release.mvc.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 视频
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class VideoFragment extends BaseFragment {
    @BindView(R.id.lv_list_news)
    ListView mLvListNews;
    private List<Integer> items = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_important_news;
    }

    @Override
    public void initView(View view) {
        for (int i = 0; i < 13; i++) {
            items.add(i);
        }
        BusinessFragmentAdapter adapter = new BusinessFragmentAdapter(getContext(), items);
        mLvListNews.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

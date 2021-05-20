package com.release.mvc.fragment;

import android.view.View;
import android.widget.ListView;

import com.release.mvc.R;
import com.release.mvc.adapter.ImportantNewsAdapter;
import com.release.mvc.base.BaseFragment;
import com.release.mvc.bean.ImprotantNewsBean;
import com.release.mvc.http.HttpCallback;
import com.release.mvc.http.HttpProxy;
import com.release.mvc.http.URL;
import com.release.mvc.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 要闻
 *
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class ImportantNewsFragment extends BaseFragment {
    private static final String TAG = ImportantNewsFragment.class.getSimpleName();

    @BindView(R.id.lv_list_news)
    ListView mLvListNews;

    private List<ImprotantNewsBean.NewslistBean> list = new ArrayList<>();
    private ImportantNewsAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_important_news;
    }

    @Override
    public void initView(View view) {
        mAdapter = new ImportantNewsAdapter(getContext(), list);
        mLvListNews.setAdapter(mAdapter);
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (list.size() == 0)
            getNetData();
    }

    private void getNetData() {

        HttpProxy.obtain().post(URL.news, new HttpCallback<ImprotantNewsBean>() {
            @Override
            public void onFailure(String throwable) {
                LogUtils.e(TAG, "onErrorResponse: " + throwable);
            }

            @Override
            public void onSuccess(ImprotantNewsBean improtantNewsBean) {
                List<ImprotantNewsBean.NewslistBean> newslist = improtantNewsBean.getNewslist();
                if (newslist != null)
                list.addAll(newslist);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}

package com.release.mvc.page;

import android.view.View;
import android.widget.ListView;

import com.release.mvc.R;
import com.release.mvc.adapter.RecommendAdapter;
import com.release.mvc.base.BaseFragment;
import com.release.mvc.bean.RecommendPageBean;
import com.release.mvc.http.HttpCallback;
import com.release.mvc.http.HttpProxy;
import com.release.mvc.http.URL;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class RecommendPage extends BaseFragment {
    private static final String TAG = RecommendPage.class.getSimpleName();

    @BindView(R.id.listView)
    ListView mListView;

    private List<RecommendPageBean.DataBean> list = new ArrayList<>();
    private RecommendAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.page_recomment;
    }

    @Override
    public void initView(View view) {
        mAdapter = new RecommendAdapter(getContext(), list);
        mListView.setAdapter(mAdapter);
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

        HttpProxy.obtain().get(URL.tea, new HttpCallback<RecommendPageBean>() {
            @Override
            public void onFailure(String throwable) {

            }

            @Override
            public void onSuccess(RecommendPageBean recommendPageBean) {
                List<RecommendPageBean.DataBean> data = recommendPageBean.getData();

                list.addAll(data);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}

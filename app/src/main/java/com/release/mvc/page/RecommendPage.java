package com.release.mvc.page;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.release.mvc.R;
import com.release.mvc.adapter.RecommendAdapter;
import com.release.mvc.base.BaseFragment;
import com.release.mvc.bean.RecommendPageBean;
import com.release.mvc.http.ICallBack;
import com.release.mvc.http.URL;
import com.release.mvc.http.VolleyHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

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

        VolleyHelper.getInstance(getContext()).get(URL.tea, new ICallBack() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onSuccess(String response) {
                parse(response);
            }
        });
    }

    private void parse(String s) {
        RecommendPageBean bean = JSON.parseObject(s, RecommendPageBean.class);
        List<RecommendPageBean.DataBean> data = bean.getData();

        list.addAll(data);
        mAdapter.notifyDataSetChanged();

    }
}

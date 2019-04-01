package com.release.mvc.fragment;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.release.mvc.R;
import com.release.mvc.adapter.ImportantNewsAdapter;
import com.release.mvc.base.BaseFragment;
import com.release.mvc.bean.ImprotantNewsBean;
import com.release.mvc.http.ICallBack;
import com.release.mvc.http.URL;
import com.release.mvc.http.VolleyHelper;
import com.release.mvc.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

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

    private List<ImprotantNewsBean.DataBean> list = new ArrayList<>();
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

        VolleyHelper.getInstance(getContext()).get(URL.caipu, new ICallBack() {
            @Override
            public void onFailure(Call call, Exception e) {
                LogUtils.e(TAG, "onErrorResponse: " + e);
            }

            @Override
            public void onSuccess(String response) {
                LogUtils.i(TAG, "onResponse: " + response);
                parse(response);
            }
        });
    }

    private void parse(String s) {
        ImprotantNewsBean bean = JSON.parseObject(s, ImprotantNewsBean.class);
        List<ImprotantNewsBean.DataBean> data = bean.getData();
        list.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}

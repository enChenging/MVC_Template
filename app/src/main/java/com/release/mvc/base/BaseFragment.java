package com.release.mvc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.release.mvc.R;
import com.release.mvc.act.MainActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public abstract class BaseFragment extends Fragment implements UIIterfaceFrag {
    public MainActivity mActivity;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof MainActivity)
            mActivity = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, view);
        initView(view);
        initListener();
        initData();
        initCommonView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void initCommonView(View view) {
        View back = view.findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }
}

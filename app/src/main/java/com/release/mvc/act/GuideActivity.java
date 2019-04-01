package com.release.mvc.act;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.release.mvc.R;
import com.release.mvc.adapter.GuideViewPagerAdapter;
import com.release.mvc.base.BaseActivity;
import com.release.mvc.utils.DensityUtil;
import com.release.mvc.utils.LogUtils;
import com.release.mvc.view.pageTransformer.CubeOutTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class GuideActivity extends BaseActivity {

    int[] images = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    @BindView(R.id.view_viewpager)
    ViewPager mViewViewpager;
    @BindView(R.id.bt_home)
    Button mBtHome;
    @BindView(R.id.dot_group)
    LinearLayout mDotGroup;
    @BindView(R.id.dot_focus)
    View mDotFocus;

    private List<ImageView> imageList;
    private int dot_width;


    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        mBtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mViewViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.i("positionOffsetPixels", "----------------" + positionOffsetPixels);
                int translate = (int) (dot_width * (position + positionOffset));
                mDotFocus.setTranslationX(translate);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageList.size() - 1) {
                    mBtHome.setVisibility(View.VISIBLE);
                } else {
                    mBtHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDotFocus.postDelayed(new Runnable() {
            @Override
            public void run() {
                dot_width = mDotGroup.getChildAt(1).getLeft() - mDotGroup.getChildAt(0).getLeft();
            }
        }, 5);
    }

    @Override
    public void initData() {
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setBackgroundResource(images[i]);
            imageList.add(iv);

            View view = new View(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(getApplicationContext(), 6), DensityUtil.dip2px(getApplicationContext(), 6));
            if (i != 0) {
                params.leftMargin = DensityUtil.dip2px(getApplicationContext(), 10);
            }
            view.setBackgroundResource(R.mipmap.dot_normal);
            view.setLayoutParams(params);
            mDotGroup.addView(view);
        }

        mViewViewpager.setAdapter(new GuideViewPagerAdapter(imageList));
//        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
//        viewpager.setPageTransformer(true, new DepthPageTransformer());
//        viewpager.setPageTransformer(true, new RotatePageTransformer());
        mViewViewpager.setPageTransformer(true, new CubeOutTransformer());


    }
}

package com.release.mvc.act;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.release.mvc.R;
import com.release.mvc.adapter.HomePagesAdapder;
import com.release.mvc.base.BaseActivity;
import com.release.mvc.base.BaseFragment;
import com.release.mvc.page.LivePage;
import com.release.mvc.page.NewsPage;
import com.release.mvc.page.RecommendPage;
import com.release.mvc.utils.AppManager;
import com.release.mvc.utils.GlideApp;
import com.release.mvc.view.LazyViewPager;
import com.release.mvc.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.rb_news)
    RadioButton mRbNews;
    @BindView(R.id.rb_recommend)
    RadioButton mRbRecommend;
    @BindView(R.id.rb_live)
    RadioButton mRbLive;
    @BindView(R.id.user_photo_bg)
    ImageView mUserPhotoBg;
    @BindView(R.id.headImg)
    ImageView mHeadImg;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_duties)
    TextView mTvUserDuties;
    @BindView(R.id.id_draw_menu_header)
    RelativeLayout mIdDrawMenuHeader;
    @BindView(R.id.tv_help)
    TextView mTvHelp;
    @BindView(R.id.ll_help)
    LinearLayout mLlHelp;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.ll_setting)
    LinearLayout mLlSetting;
    @BindView(R.id.dl_drawer)
    DrawerLayout mDlDrawer;

    private List<BaseFragment> homePages;
    private static Boolean isExit = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        GlideApp.with(this).load("https://b-ssl.duitang.com/uploads/item/201802/20/20180220170028_JcYMU.jpeg").circleCrop().into(mHeadImg);
    }

    @Override
    public void initData() {
        homePages = new ArrayList<>();
        homePages.add(new NewsPage());
        homePages.add(new RecommendPage());
        homePages.add(new LivePage());
        mViewPager.setAdapter(new HomePagesAdapder(getSupportFragmentManager(), homePages));
        mViewPager.setCurrentItem(0);
        mRbNews.setChecked(true);
    }

    @Override
    public void initListener() {

        mDlDrawer.setScrimColor(getResources().getColor(R.color.black_alpha_32));
        mDlDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (drawerView.getTag().equals("left")) {
                    View content = mDlDrawer.getChildAt(0);
                    int offset = (int) (drawerView.getWidth() * slideOffset);
                    content.setTranslationX(offset);

                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mViewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mDlDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    case 1:
                    case 2:
                    case 3:
                        mDlDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.rb_news, R.id.rb_recommend, R.id.rb_live, R.id.id_draw_menu_header, R.id.ll_help, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_news:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.rb_recommend:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.rb_live:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_draw_menu_header:
                Toast.makeText(this, "点击头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_help:
                Toast.makeText(this, "帮助中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_setting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void toggle() {
        int drawerLockMode = mDlDrawer.getDrawerLockMode(GravityCompat.START);
        if (mDlDrawer.isDrawerVisible(GravityCompat.START) && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            mDlDrawer.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            mDlDrawer.openDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!closeDrawableLayout())
                exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean closeDrawableLayout() {
        if (mDlDrawer.isDrawerVisible(GravityCompat.START)) {
            mDlDrawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }


    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            delayMessage();
        } else {
            AppManager.getAppManager().appExit(this);
        }
    }

    private void delayMessage() {
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        isExit = false;
                    }
                });
    }

}

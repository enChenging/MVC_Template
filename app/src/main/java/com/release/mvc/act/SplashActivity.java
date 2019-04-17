package com.release.mvc.act;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.release.mvc.R;
import com.release.mvc.base.BaseActivity;
import com.release.mvc.base.Constants;
import com.release.mvc.utils.AppManager;
import com.release.mvc.utils.InstallUtil;
import com.release.mvc.utils.SPUtil;
import com.release.mvc.utils.StatusBarUtil;
import com.release.mvc.view.dialog.NoticeDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
@RuntimePermissions
public class SplashActivity extends BaseActivity {

    @BindView(R.id.btn_jump)
    Button mBtnJump;
    @BindView(R.id.btn_permission)
    Button mBtnPermission;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_jump, R.id.btn_permission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jump:
                if (Build.VERSION.SDK_INT >= 23 && !hasPermission)
                    SplashActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                else
                    goHome();
                break;
            case R.id.btn_permission:
                if (Build.VERSION.SDK_INT >= 23)
                    SplashActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                break;
        }
    }

    private boolean isVisiable;

    @Override
    protected void onResume() {
        super.onResume();
        isVisiable = true;

        if (Build.VERSION.SDK_INT >= 23 && !isRequest) {
            isRequest = false;
            SplashActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
        } else if (Build.VERSION.SDK_INT < 23) {
            skip();
        }
    }

    private void skip() {
        Boolean loginFirst = (Boolean) SPUtil.getParam(Constants.LOGIN_FIRST, true);
        if (loginFirst) {
            goGuide();
            SPUtil.setParam(Constants.LOGIN_FIRST, false);
        } else {
            waitJump();
        }
    }


    private boolean isBack;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBack = true;
        AppManager.getAppManager().appExit(this);
    }

    private void waitJump() {
        mBtnJump.setVisibility(View.VISIBLE);
        mBtnPermission.setVisibility(View.GONE);
        countdown(6);
    }

    private void countdown(int time) {
        countDown(time)
                .compose(this.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mBtnJump.setText(getString(R.string.jump) + "(6)");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        mBtnJump.setText(getString(R.string.jump) + "(" + aLong + ")");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (!isBack && isVisiable) {
                            goHome();
                        }
                    }
                });
    }

    public Observable<Long> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return countTime - aLong;
                    }
                })
                .take(countTime + 1);
    }

    private void goGuide() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }

    private void goHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private View.OnClickListener noticeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    if (mRequest != null && !isNever) {
                        mRequest.cancel();
                    }
                    break;
                case R.id.tv_ok:
                    if (mRequest != null && !isNever) {
                        mRequest.proceed();
                    }
                    if (isNever) {
                        isRequest = false;
                        InstallUtil.startAppSettings(SplashActivity.this);
                    }
                    break;
            }
            NoticeDialog.dismissDialog();
        }
    };

    private boolean isRequest;
    private boolean isNever;
    private boolean hasPermission;
    private PermissionRequest mRequest;

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsPermission() {
        hasPermission = true;
        skip();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
        mRequest = request;
        NoticeDialog.show(this, getResources().getString(R.string.rationale_wr), noticeListener);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        isRequest = true;
        mBtnPermission.setVisibility(View.VISIBLE);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskAgain() {
        isRequest = true;
        isNever = true;
        mBtnPermission.setVisibility(View.VISIBLE);
        NoticeDialog.show(this, getResources().getString(R.string.rationale_ask_again), noticeListener);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}

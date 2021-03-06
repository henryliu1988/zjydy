package com.zhjydy.view.avtivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.zhjydy.presenter.contract.InitLoaderContract;
import com.zhjydy.presenter.presenterImp.InitLoaderPresenterImp;
import com.zhjydy.util.ActivityUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/18 0018.
 */
public class InitLoaderActivity extends BaseActivity implements InitLoaderContract.View {

    private InitLoaderContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_initloader);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        new InitLoaderPresenterImp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        if (mPresenter != null) {
            mPresenter.finish();
        }
        super.onPause();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void setPresenter(InitLoaderContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void gotoMainTabs() {
        ActivityUtils.showHome(this, true);
    }

}

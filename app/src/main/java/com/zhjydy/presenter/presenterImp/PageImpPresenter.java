package com.zhjydy.presenter.presenterImp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhjydy.presenter.contract.PageImpContract;
import com.zhjydy.view.ActivityResultView;
import com.zhjydy.view.fragment.StatedFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/9.
 */
public  class PageImpPresenter implements PageImpContract.Presenter
{
    private final PageImpContract.View view;

    private List<ActivityResultView> mActivityResultViews = new ArrayList<>();

    public PageImpPresenter(PageImpContract.View view)
    {
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        for (ActivityResultView view:mActivityResultViews) {
            if (view != null) {
                view.onActivityResult1(requestCode,resultCode,data);
            }
        }
    }
}

package com.zhjydy.view.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.zhjydy.R;
import com.zhjydy.view.fragment.FragmentFactory;
import com.zhjydy.presenter.contract.PageImpContract;
import com.zhjydy.presenter.presenterImp.PageImpPresenter;
import com.zhjydy.view.fragment.FragKey;
import com.zhjydy.view.fragment.FragmentUtils;
import com.zhjydy.view.fragment.StatedFragment;

import butterknife.ButterKnife;

/**
 * Created by admin on 2016/8/9.
 */
public class PagerImpActivity extends BaseActivity implements PageImpContract.View {


    private FragmentManager fragManager;

    private PageImpContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra(IntentKey.INTENT_BUNDLE);
        int key = bundle.getInt(IntentKey.FRAG_KEY);
        if (!FragKey.FragMap.containsKey(key)) {
            finish();
            return;
        }
        new PageImpPresenter(this);
        setContentView(R.layout.activity_pager_imp);
        ButterKnife.bind(this);
        StatedFragment fragment = FragmentFactory.createFragment(key);
        if (fragment == null) {
            return;
        }
        String fragInfo =bundle.getString(IntentKey.FRAG_INFO);
        if (!TextUtils.isEmpty(fragInfo)) {
            Bundle fragBundle = new Bundle();
            fragBundle.putString(IntentKey.FRAG_INFO,fragInfo);
            fragment.setArguments(fragBundle);
        }
        fragManager = getSupportFragmentManager();
        FragmentUtils.addNewFragment(this,fragment,FragKey.FragMap.get(key),R.id.fragment_content);
    }


    @Override
    public void onBackPressed() {
        FragmentUtils.back(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void setPresenter(PageImpContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mPresenter != null) {
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}

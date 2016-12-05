package com.zhjydy.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.presenter.contract.SearchHomeContract;
import com.zhjydy.presenter.presenterImp.SearchHomePresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.avtivity.PagerImpActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class SearchHomeFragment extends PageImpBaseFragment implements SearchHomeContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;
    private SearchHomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(SearchHomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_home;
    }

    @Override
    protected void afterViewCreate() {
        new SearchHomePresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleSearchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    search();
                    return true;
                }
                return false;
            }
        });
    }
    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchExpertAndInfo(condition);

    }
    private void moreSeachExpert() {
        String seach = titleSearchEdit.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKey.FRAG_KEY, FragKey.search_expert_fragment);
        bundle.putString(IntentKey.FRAG_INFO, seach);
        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);

    }
    private void moreSeachInfo() {
        String seach = titleSearchEdit.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentKey.FRAG_KEY, FragKey.search_info_fragment);
        bundle.putString(IntentKey.FRAG_INFO, seach);
        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onSearchResult(List<Map<String, Object>> experts, List<Map<String, Object>> infos)
    {

    }
}

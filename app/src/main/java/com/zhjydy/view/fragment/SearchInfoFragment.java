package com.zhjydy.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.contract.SearchInfoContract;
import com.zhjydy.presenter.presenterImp.MainInfoPresenterImp;
import com.zhjydy.presenter.presenterImp.SearchInfoPresenterImp;
import com.zhjydy.view.adapter.MainInfoListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SearchInfoFragment extends PageImpBaseFragment implements SearchInfoContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;


    private List<Map<String,Object>> list = new ArrayList<>();
    private MainInfoListAdapter mInfoAdapter;
    public static SearchInfoFragment instance() {
        SearchInfoFragment frag = new SearchInfoFragment();
        return frag;
    }

    private SearchInfoContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_info;

    }

    @Override
    protected void afterViewCreate() {
        titleSearchEdit.setHint("搜索资讯");
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


        mInfoAdapter = new MainInfoListAdapter(getContext(),list);
        mList.setAdapter(mInfoAdapter);
        new SearchInfoPresenterImp(this);
    }
    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchInfo(condition);
    }

    @Override
    public void setPresenter(SearchInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
        }
    }

    @Override
    public void updateInfoList(List<Map<String, Object>> list) {
        mInfoAdapter.refreshData(list);
    }
}

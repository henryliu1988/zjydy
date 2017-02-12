package com.zhjydy.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.presenter.contract.FavExpertContract;
import com.zhjydy.presenter.presenterImp.FaveExpertPresenterImp;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.FavExpertListAdapter;
import com.zhjydy.view.avtivity.IntentKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class FavExpertFragment extends PageImpBaseFragment implements FavExpertContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    protected FavExpertContract.Presenter mPresenter;
    protected FavExpertListAdapter mExpertListAdapter;
    @BindView(R.id.list_layout)
    RelativeLayout listLayout;
    @BindView(R.id.null_data_text)
    TextView nullDataText;
    @BindView(R.id.null_data_retrye)
    TextView nullDataRetrye;
    @BindView(R.id.null_data_layout)
    RelativeLayout nullDataLayout;

    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fave_doc;
    }

    @Override
    protected void afterViewCreate() {
        initExpertList();
        new FaveExpertPresenterImp(this);
        titleSearchEdit.setHint("搜索收藏的专家");
        titleSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    search();
                }

                return false;
            }
        });
        mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                search();
            }
        });
    }


    private void search() {
        String condition = titleSearchEdit.getText().toString();
        mPresenter.searchFavExpert(condition);

    }

    private void initExpertList() {
        mExpertListAdapter = new FavExpertListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mExpertListAdapter.setPresenter(mPresenter);
        mList.setAdapter(mExpertListAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (info != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("memberid")));
                    // ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    gotoFragment(FragKey.detail_expert_fragment, bundle);
                }
            }
        });

    }

    @Override
    public void setPresenter(FavExpertContract.Presenter presenter) {
        this.mPresenter = presenter;
        if (mExpertListAdapter != null) {
            mExpertListAdapter.setPresenter(presenter);
        }
    }

    @Override
    public void refreshView() {
    }

    @Override
    public void updateExperts(List<Map<String, Object>> list) {
        mExpertListAdapter.refreshData(list);
        mList.onRefreshComplete();
        if (list != null && list.size() > 0) {
            listLayout.setVisibility(View.VISIBLE);
            nullDataLayout.setVisibility(View.GONE);
        } else {
            listLayout.setVisibility(View.GONE);
            nullDataLayout.setVisibility(View.VISIBLE);
            nullDataRetrye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @OnClick({R.id.title_back,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                mPresenter.finish();
                back();
                break;
        }
    }
}

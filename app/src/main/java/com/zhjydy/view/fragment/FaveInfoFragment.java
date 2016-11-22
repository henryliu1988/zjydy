package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.FavInfoContract;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.presenterImp.FaveExpertPresenterImp;
import com.zhjydy.presenter.presenterImp.FaveInfoPresenterImp;
import com.zhjydy.presenter.presenterImp.MainInfoPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.FaveInfoListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.avtivity.PagerImpActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class FaveInfoFragment extends PageImpBaseFragment implements FavInfoContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_edit)
    EditText titleSearchEdit;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    public static FaveInfoFragment instance() {
        FaveInfoFragment frag = new FaveInfoFragment();
        return frag;
    }

    private FavInfoContract.Presenter mPresenter;

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
        new FaveInfoPresenterImp(this);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object>info =(Map<String,Object>)adapterView.getAdapter().getItem(i);
                if(info != null && !TextUtils.isEmpty(Utils.toString(info.get("id")))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                    ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
    }

    @Override
    public void setPresenter(FavInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateInfoList(List<Map<String,Object>> infos) {
        FaveInfoListAdapter adapter = new FaveInfoListAdapter(getContext(), infos);
        mList.setAdapter(adapter);
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
}

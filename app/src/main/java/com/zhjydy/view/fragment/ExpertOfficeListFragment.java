package com.zhjydy.view.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.presenterImp.MainExpertTabPresenterImp;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.MainExpertListAdapter;
import com.zhjydy.view.avtivity.IntentKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/18 0018.
 */
public class ExpertOfficeListFragment extends PageImpBaseFragment implements MainExpertContract.TabView {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;


    private String officeId;
    private String officeName;

    private MainExpertContract.TabPresenter mPresneter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_office_expert_list;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            return;
        }
        String data  = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(data)) {
            return;
        }
        Map<String,Object> map = Utils.parseObjectToMapString(data);
        if (map.size() < 0 || TextUtils.isEmpty(Utils.toString(map.get("id"))) || TextUtils.isEmpty(Utils.toString(map.get("name")))) {
            return;
        }
        officeId = Utils.toString(map.get("id"));
        officeName = Utils.toString(map.get("name"));
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText(officeName);
        MainExpertListAdapter adapter = new MainExpertListAdapter(getContext(),new ArrayList<Map<String, Object>>());
        new MainExpertTabPresenterImp(this,mList,adapter, WebKey.func_getExpertsList);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> info = (Map<String,Object>)parent.getAdapter().getItem(position);
                if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("memberid")))) {
                   // ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.detail_expert_fragment, Utils.toString(info.get("memberid")), false);
                gotoFragment(FragKey.detail_expert_fragment,Utils.toString(info.get("memberid")));

                }
            }
        });

    }

    @Override
    public void refreshView() {

    }

    @Override
    public Map<String, Object> getFilterConditions() {
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(officeId)) {
            params.put("office", officeId);
        }
        return params;
    }


    @Override
    public void setPresenter(MainExpertContract.TabPresenter presenter) {
        mPresneter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

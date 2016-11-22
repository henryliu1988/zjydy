package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yyydjk.library.DropDownMenu;
import com.zhjydy.R;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.presenter.contract.FavExpertContract;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.presenterImp.FaveExpertPresenterImp;
import com.zhjydy.presenter.presenterImp.MainExpertPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.FavExpertListAdapter;
import com.zhjydy.view.adapter.SimPleTextAdapter;
import com.zhjydy.view.avtivity.PagerImpActivity;

import java.util.ArrayList;
import java.util.Arrays;
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
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    private View mListContainerView;
    private PullToRefreshListView mExpertListView;
    protected FavExpertContract.Presenter mPresenter;
    protected String headers[] = {"全部地区", "科室", "职称"};
    protected String depart[] = {"内科", "外科", "五官科"};
    protected String proTitle[] = {"内科", "外科", "五官科"};
    protected List<View> dropViews = new ArrayList<>();
    protected FavExpertListAdapter mExpertListAdapter;

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
        titleSearchEdit.setHint("搜索专家");
        titleSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH) {

                }

                return false;
            }
        });
    }

    private void initExpertList() {
        mListContainerView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_expert_list, null);
        mExpertListView = (PullToRefreshListView) mListContainerView.findViewById(R.id.m_list);
        mExpertListAdapter = new FavExpertListAdapter(getContext(), new ArrayList<Map<String,Object>>());
        mExpertListView.setAdapter(mExpertListAdapter);
        mExpertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> info = (Map<String,Object>) adapterView.getAdapter().getItem(i);
                if (info != null) {
                    ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.detail_expert_fragment, Utils.toString(info.get("id")), false);
                }
            }
        });

    }

    @Override
    public void setPresenter(FavExpertContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateFilters(Map<String, Object> data) {
        dropViews.clear();
        List<District> districts = (List<District>) data.get(headers[0]);
        List<NormalDicItem> depart = (List<NormalDicItem>) data.get(headers[1]);
        List<NormalDicItem> profe = (List<NormalDicItem>) data.get(headers[2]);


        ListView listView0 = new ListView(getContext());
        SimPleTextAdapter adapter0 = new SimPleTextAdapter(getContext(), depart);
        listView0.setAdapter(adapter0);
        dropViews.add(listView0);


        ListView listView1 = new ListView(getContext());
        SimPleTextAdapter adapter1 = new SimPleTextAdapter(getContext(), profe);
        listView1.setAdapter(adapter1);
        dropViews.add(listView1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.closeMenu();
            }
        });
        ListView listView2 = new ListView(getContext());
        SimPleTextAdapter adapter2 = new SimPleTextAdapter(getContext(), profe);
        listView2.setAdapter(adapter2);
        dropViews.add(listView2);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), dropViews, mListContainerView);

    }

    @Override
    public void updateExperts(List<Map<String,Object>> list) {
        mExpertListAdapter.refreshData(list);
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
                FragmentUtils.back(getActivity());
                break;
        }
    }
}

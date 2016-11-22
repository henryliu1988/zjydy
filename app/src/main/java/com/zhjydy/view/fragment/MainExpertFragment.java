package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.presenterImp.MainExpertPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.FavExpertListAdapter;
import com.zhjydy.view.adapter.MainExpertListAdapter;
import com.zhjydy.view.adapter.SimPleTextAdapter;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.BadgImage;

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
public class MainExpertFragment extends StatedFragment implements MainExpertContract.MainExpertView {

    @BindView(R.id.right_img)
    BadgImage rightImg;
    @BindView(R.id.right_l_img)
    BadgImage rightLImg;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;

    private View mListContainerView;
    private PullToRefreshListView mExpertListView;

    private ListView mDropDomainListView;
    private ListView mDropDepartListView;
    private ListView mDropProfessListView;

    private SimPleTextAdapter mDropDomainAdapter;
    private SimPleTextAdapter mDropDepartAdapter;private SimPleTextAdapter mDropProfessAdapter;
    protected MainExpertContract.MainExpertPresenter mPresenter;
    protected String headers[] = {"全部地区", "科室", "职称"};
    protected String depart[] = {"内科", "外科", "五官科"};
    protected String proTitle[] = {"内科", "外科", "五官科"};
    protected List<View> dropViews = new ArrayList<>();
    protected MainExpertListAdapter mExpertListAdapter;
    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_doc;
    }

    @Override
    protected void afterViewCreate() {
        initExpertList();
        initDropMenuViews();
        titleSearchText.setText("搜索专家");
        rightLImg.setImageSrc(R.mipmap.title_msg);
        rightImg.setImageSrc(R.mipmap.shoucang);
        rightLImg.setText("1");
        rightImg.setText("1");
        new MainExpertPresenterImp(this);
    }


    private void initDropMenuViews() {
        mDropDomainListView = new ListView(getContext());
        mDropDomainAdapter = new SimPleTextAdapter(getContext(), new ArrayList<NormalDicItem>());
        mDropDomainListView.setAdapter(mDropDomainAdapter);
        dropViews.add(mDropDomainListView);


        mDropDepartListView = new ListView(getContext());
        mDropDepartAdapter = new SimPleTextAdapter(getContext(),  new ArrayList<NormalDicItem>());
        mDropDepartListView.setAdapter(mDropDepartAdapter);
        dropViews.add(mDropDepartListView);
        mDropDepartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.closeMenu();
            }
        });

        mDropProfessListView = new ListView(getContext());
        mDropProfessAdapter = new SimPleTextAdapter(getContext(),  new ArrayList<NormalDicItem>());
        mDropProfessListView.setAdapter(mDropProfessAdapter);
        dropViews.add(mDropProfessListView);
        mDropProfessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.closeMenu();
            }
        });
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), dropViews, mListContainerView);

    }
    private void initExpertList() {
        mListContainerView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_expert_list,null);
        mExpertListView = (PullToRefreshListView)mListContainerView.findViewById(R.id.m_list);
        mExpertListAdapter = new MainExpertListAdapter(getContext(), new ArrayList<Map<String,Object>>());
        mExpertListView.setAdapter(mExpertListAdapter);
        mExpertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> info = (Map<String,Object>)adapterView.getAdapter().getItem(i);
                if(info != null && !TextUtils.isEmpty(Utils.toString(info.get("id")))) {
                    ActivityUtils.transToFragPagerActivity(getActivity(),PagerImpActivity.class,FragKey.detail_expert_fragment,Utils.toString(info.get("id")),false);
                }
            }
        });

    }
    @Override
    public void setPresenter(MainExpertContract.MainExpertPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateFilters(Map<String, Object> data) {
        List<District> districts = (List<District>) data.get(headers[0]);
        List<NormalDicItem> depart = (List<NormalDicItem>) data.get(headers[1]);
        List<NormalDicItem> profe = (List<NormalDicItem>) data.get(headers[2]);
        mDropDomainAdapter.refreshData(depart);
        mDropDepartAdapter.refreshData(depart);
        mDropProfessAdapter.refreshData(profe);
    }

    @Override
    public void updateExperts(List<Map<String, Object>> maps) {
        mExpertListAdapter.refreshData(maps);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.title_search_ly, R.id.right_img, R.id.right_l_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_search_ly:
                Bundle bundle = new Bundle();
                bundle.putInt("key",FragKey.search_expert_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class,bundle,false);
                break;
            case R.id.right_img:
                Bundle bundleFave = new Bundle();
                bundleFave.putInt("key", FragKey.fave_expert_list_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleFave, false);
                break;
            case R.id.right_l_img:
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
                break;
        }
    }
}

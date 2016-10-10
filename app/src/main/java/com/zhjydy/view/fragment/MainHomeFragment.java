package com.zhjydy.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zhjydy.R;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.MainHomeContract;
import com.zhjydy.presenter.presenterImp.MainHomePresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewFindUtils;
import com.zhjydy.view.adapter.MainInfoListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainHomeFragment extends StatedFragment implements MainHomeContract.MainHomeView {

    ConvenientBanner<List<Map<String, Object>>> expertList;
    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_l_img)
    ImageView rightLImg;
    @BindView(R.id.banner_home)
    ConvenientBanner bannerHome;
    @BindView(R.id.m_new_msg_icon)
    ImageView mNewMsgIcon;
    @BindView(R.id.m_new_msg_title)
    TextView mNewMsgTitle;
    @BindView(R.id.news_msg)
    RelativeLayout newsMsg;
    @BindView(R.id.expert_more)
    TextView expertMore;
    @BindView(R.id.banner_expert)
    ConvenientBanner bannerExpert;
    @BindView(R.id.info_more)
    TextView infoMore;
    @BindView(R.id.info_list)
    ListViewForScrollView infoList;


    private MainHomeContract.MainHomePresenter mPresenter;

    private MainInfoListAdapter mInfoListAdapter;
    public static MainHomeFragment instance() {
        MainHomeFragment frag = new MainHomeFragment();
        return frag;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void afterViewCreate() {
        new MainHomePresenterImp(this);
        centerTv.setText("专家医对壹");
    }

    @Override
    protected void initData() {
    }


    @Override
    public void setPresenter(MainHomeContract.MainHomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void updateBanner(List<String> images) {
        bannerHome.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, images).startTurning(3000)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
    }

    @Override
    public void updateMsg(Map<String, Object> msg) {
        String title = Utils.toString(msg.get("title"));
        mNewMsgTitle.setText(title);
        newsMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void updateMsg(List<DocTorInfo> experts) {
            int bannerCount = experts.size() / 3;
            if (experts.size() % 3 != 0) {
                bannerCount++;
            }
        List<List<DocTorInfo>> bannerList = new ArrayList<>();
        List<DocTorInfo> list = new ArrayList<>();
        List<DocTorInfo> list1 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            list.add(experts.get(i));
            list1.add(experts.get(i));

        }

        bannerList.add(list);
        bannerList.add(list1);

        bannerExpert.setPages(
                new CBViewHolderCreator<ExpertHolderView>() {
                    @Override
                    public ExpertHolderView createHolder() {
                        return new ExpertHolderView();
                    }
                }, bannerList)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

    }

    @Override
    public void updateInfo(List<Infomation> infos) {
        mInfoListAdapter = new MainInfoListAdapter(getContext(),new ArrayList<Infomation>());
        infoList.setAdapter(mInfoListAdapter);
        infoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Infomation info = (Infomation)adapterView.getAdapter().getItem(i);
                if(info != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, info.getId());
                    ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
        mInfoListAdapter.refreshData(infos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.news_msg, R.id.expert_more, R.id.info_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_msg:
                break;
            case R.id.expert_more:
                Bundle bundle = new Bundle();
                bundle.putInt("key", FragKey.search_expert_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                break;
            case R.id.info_more:
                Bundle bundleInfo = new Bundle();
                bundleInfo.putInt("key", FragKey.search_info_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleInfo, false);

                break;
        }
    }

    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageUtils.getInstance().displayFromRemote(data, imageView);
        }
    }

    class ExpertHolderView implements Holder<List<DocTorInfo>> {
        private LinearLayout mView;

        @Override
        public View createView(Context context) {
            mView = new LinearLayout(context);
            mView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mView.setLayoutParams(param);
            return mView;
        }

        @Override
        public void UpdateUI(Context context, int position, List<DocTorInfo> data) {
            if (data == null || data.size() < 1) {
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                DocTorInfo item = data.get(i);
                String photoUrl = item.getPhotoUrl();
                String name = item.getName();
                String hospital = item.getHospital();
                String office = item.getOffice();
                String profess = item.getProfess();
                LinearLayout child = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.main_home_expert_layout, null);
                child.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                child.setLayoutParams(param);
                ImageView imageView = ViewFindUtils.find(child, R.id.image);
                TextView nameTv = ViewFindUtils.find(child, R.id.name);
                TextView hospitalTv = ViewFindUtils.find(child, R.id.hospital);
                TextView officeTv =  ViewFindUtils.find(child, R.id.office);
                TextView professTv = ViewFindUtils.find(child, R.id.profess);
                ImageUtils.getInstance().displayFromRemote(photoUrl, imageView);
                nameTv.setText(name);
                hospitalTv.setText(hospital);
                officeTv.setText(office);
                professTv.setText(profess);
                mView.addView(child);
            }

        }


    }
}

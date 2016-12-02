package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.presenterImp.MainInfoPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.MainInfoListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.BadgImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainInfoFragment extends StatedFragment implements MainInfoContract.MainInfoView {
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.right_img)
    BadgImage rightImg;
    @BindView(R.id.right_l_img)
    BadgImage rightLImg;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;
    public static MainInfoFragment instance() {
        MainInfoFragment frag = new MainInfoFragment();
        return frag;
    }

    private MainInfoContract.MainInfoPresenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_info;

    }

    @Override
    protected void afterViewCreate() {
        initView();
        new MainInfoPresenterImp(this,mList);
        titleSearchText.setText("搜索资讯");
        rightLImg.setImageSrc(R.mipmap.title_msg);
        rightImg.setImageSrc(R.mipmap.shoucang);
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("key", FragKey.fave_info_list_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
            }
        });
        rightLImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
            }
        });
        titleSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.search_info_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);

            }
        });

    }

    private void initView() {
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("id")))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IntentKey.FRAG_KEY, FragKey.detail_info_fragment);
                    bundle.putString(IntentKey.FRAG_INFO, Utils.toString(info.get("id")));
                    ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
    }

    @Override
    public void setPresenter(MainInfoContract.MainInfoPresenter presenter) {
        mPresenter = presenter;
    }

    public void updateUnReadMsgCount(int count) {
        rightLImg.setText(count + "");
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateFavInfoCount(int count) {
        if (count > 0) {
            rightImg.setText(count + "");

        }
    }
}

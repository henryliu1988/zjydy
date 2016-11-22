package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.InfoDetailContract;
import com.zhjydy.presenter.presenterImp.InfoDetailPresenterImp;
import com.zhjydy.view.avtivity.IntentKey;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class InfoDetailFragment extends PageImpBaseFragment implements InfoDetailContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.m_save_info_button)
    ImageView mSaveInfoButton;
    @BindView(R.id.m_share_info_button)
    ImageView mShareInfoButton;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.m_info_webview)
    WebView mInfoWebview;
    private InfoDetailContract.Presenter mPresenter;

    String id;
    @Override
    public void setPresenter(InfoDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    protected void initData() {
        titleCenterTv.setText("资讯详情");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    back();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_detail;
    }

    @Override
    protected void afterViewCreate() {
        if (getArguments() == null) {
            return;
        }
         id = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        new InfoDetailPresenterImp(this, id);
        mSaveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveInfo(id);
            }
        });
    }

    @Override
    public void update(Map<String, Object> info) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

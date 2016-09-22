package com.zhjydy.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zhjydy.R;
import com.zhjydy.presenter.contract.MainHomeContract;
import com.zhjydy.presenter.presenterImp.MainHomePresenterImp;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewFindUtils;

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

    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_l_img)
    ImageView rightLImg;
    @BindView(R.id.banner_home)
    ConvenientBanner<String> bannerHome;
    @BindView(R.id.m_new_msg_icon)
    ImageView mNewMsgIcon;
    @BindView(R.id.m_new_msg_title)
    TextView mNewMsgTitle;
    @BindView(R.id.news_msg)
    RelativeLayout newsMsg;
    @BindView(R.id.export_more)
    TextView exportMore;
    @BindView(R.id.banner_expert)
    ConvenientBanner<List<Map<String,Object>>> bannerExpert;


    private MainHomeContract.MainHomePresenter mPresenter;

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
        centerTv.setText("专家一对壹");
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @OnClick(R.id.right_img)
    public void onClick() {

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
    public void updateMsg(List<Map<String, Object>> experts) {
        List<Map<String, Object>> bannerExperts = new ArrayList<>(3);
        Observable.from(experts).buffer(3).buffer(experts.size() / 3).subscribe(new Action1<List<List<Map<String, Object>>>>() {
            @Override
            public void call(final List<List<Map<String, Object>>> lists) {
                bannerExpert.setPages(
                        new CBViewHolderCreator<ExpertHolderView>() {
                            @Override
                            public ExpertHolderView createHolder() {
                                return new ExpertHolderView();
                            }
                        }, lists)
                        .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
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
            imageView.setImageResource(R.mipmap.ic_default_adimage);
            ImageUtils.getInstance().displayFromRemote(data, imageView);
        }
    }

    class ExpertHolderView implements Holder<List<Map<String, Object>>> {
        private LinearLayout mView;
        @Override
        public View createView(Context context) {
            mView = new LinearLayout(context);
            mView.setOrientation(LinearLayout.HORIZONTAL);
            mView.setPadding(5,5,5,20);
            return mView;
        }

        @Override
        public void UpdateUI(Context context, int position, List<Map<String, Object>> data) {
            if (data == null || data.size() < 1) {
                return;
            }
            for (int i = 0 ; i <data.size(); i ++) {
                Map<String, Object> item = data.get(i);
                String iamgeUrl = Utils.toString(item.get("url"));
                String name = Utils.toString(item.get("name"));
                String group = Utils.toString(item.get("group"));
                String prefession = Utils.toString(item.get("prefession"));
                String level = Utils.toString(item.get("level"));
                View child =  LayoutInflater.from(context).inflate(R.layout.main_home_expert_layout,null);
                ImageView imageView = (ImageView) ViewFindUtils.find(child,R.id.image);
                TextView nameTv = (TextView) ViewFindUtils.find(child,R.id.name);
                TextView groupTv = (TextView) ViewFindUtils.find(child,R.id.group);
                TextView ProTv = (TextView) ViewFindUtils.find(child,R.id.profession);
                TextView levelTv = (TextView) ViewFindUtils.find(child,R.id.level);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageResource(R.mipmap.ic_default_adimage);
                ImageUtils.getInstance().displayFromRemote(iamgeUrl, imageView);
                nameTv.setText(name);
                groupTv.setText(group);
                ProTv.setText(prefession);
                levelTv.setText(level);
                mView.addView(child);
                View view = new View(context);
            }

        }


    }
}

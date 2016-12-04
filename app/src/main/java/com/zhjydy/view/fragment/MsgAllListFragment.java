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

import com.alibaba.fastjson.JSONObject;
import com.zhjydy.R;
import com.zhjydy.presenter.contract.MsgAllListContract;
import com.zhjydy.presenter.presenterImp.MsgAllListPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.MsgChatListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.avtivity.PagerImpActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class MsgAllListFragment extends PageImpBaseFragment implements MsgAllListContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.msg_order_layout)
    LinearLayout msgOrderLayout;
    @BindView(R.id.msg_comment_layout)
    LinearLayout msgCommentLayout;



    private MsgAllListContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_list;
    }

    @Override
    protected void afterViewCreate() {
        ButterKnife.bind(this, mRootView);
        titleCenterTv.setText("消息");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        new MsgAllListPresenterImp(this);
    }


    @Override
    public void updateOrderList(List<Map<String, Object>> data) {
        msgOrderLayout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
            ImageView imagew = (ImageView) view.findViewById(R.id.image);
            TextView title = (TextView) view.findViewById(R.id.msg_title);
            TextView content = (TextView) view.findViewById(R.id.msg_content);
            TextView timeTv = (TextView) view.findViewById(R.id.msg_time);

            ImageUtils.getInstance().displayFromDrawable(Utils.toInteger(data.get(i).get("image")), imagew);
            title.setText(Utils.toString(data.get(i).get("title")));
            content.setText(Utils.toString(data.get(i).get("content")));
            timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get(i).get("time"))));
            view.setTag(Utils.toInteger(data.get(i).get("type")));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = Utils.toInteger(v.getTag());
                    if (type == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("key", FragKey.msg_order_list_fragment);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    } else if (type == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("key", FragKey.system_order_list_fragment);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    }
                }
            });
            msgOrderLayout.addView(view);
        }
    }

    @Override
    public void updateChatList(List<Map<String, Object>> data) {
        msgCommentLayout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.listview_msg_item_layout, null);
            ImageView imagew = (ImageView) view.findViewById(R.id.image);
            TextView title = (TextView) view.findViewById(R.id.msg_title);
            TextView content = (TextView) view.findViewById(R.id.msg_content);
            TextView timeTv = (TextView) view.findViewById(R.id.msg_time);


            String photoUrl = Utils.toString(data.get(i).get("url"));
            if (!TextUtils.isEmpty(photoUrl)) {
                ImageUtils.getInstance().displayFromRemote(photoUrl, imagew);
            } else {
                ImageUtils.getInstance().displayFromDrawable(R.mipmap.photo, imagew);
            }

            title.setText(Utils.toString(data.get(i).get("sendname")));
            content.setText(Utils.toString(data.get(i).get("content")));
            timeTv.setText(DateUtil.getTimeDiffDayCurrent(Utils.toLong(data.get(i).get("addtime"))));
            view.setTag(data.get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if (tag != null && tag instanceof Map) {
                        Map<String, Object> data = (Map<String, Object>) tag;
                        Bundle bundle = new Bundle();
                        String info = JSONObject.toJSONString(data);
                        bundle.putString(IntentKey.FRAG_INFO,info);
                        bundle.putInt("key", FragKey.doc_chat_record_fragment);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    }
                }
            });
            msgCommentLayout.addView(view);
        }
    }

    @Override
    public void setPresenter(MsgAllListContract.Presenter presenter) {
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
}

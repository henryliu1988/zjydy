package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.MsgAllListContract;
import com.zhjydy.presenter.presenterImp.MsgAllListPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.MsgChatListAdapter;
import com.zhjydy.view.adapter.MsgSystemListAdapter;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class MsgAllListFragment extends PageImpBaseFragment implements MsgAllListContract.View{
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.msg_list_view_order)
    ListViewForScrollView msgListViewOrder;
    @BindView(R.id.msg_list_view_chat)
    ListViewForScrollView msgListViewChat;


    private MsgSystemListAdapter mOrderListAdapter;
    private MsgChatListAdapter mChatListAdapter;

    private List<Map<String,Object>> orderList = new ArrayList<>();
    private List<Map<String,Object>> chatList = new ArrayList<>();

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
        mOrderListAdapter = new MsgSystemListAdapter(getContext(),orderList);
        mChatListAdapter = new MsgChatListAdapter(getContext(),chatList);
        msgListViewOrder.setAdapter(mOrderListAdapter);
        msgListViewChat.setAdapter(mChatListAdapter);
        msgListViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> itemData = mOrderListAdapter.getItem(i);
                if (itemData != null && itemData.size() > 0) {
                    String title = Utils.toString(itemData.get("title"));
                    if (!TextUtils.isEmpty(title) && title.contains("订单")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("key", FragKey.msg_order_list_fragment);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                    }
                    if (!TextUtils.isEmpty(title) && title.contains("系统")) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("key", FragKey.system_order_list_fragment);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);

                    }
                }
            }
        });
        msgListViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Map<String,Object> itemData = mChatListAdapter.getItem(position);
                if (itemData != null && itemData.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("key", FragKey.doc_chat_record_fragment);
                        ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                }
            }
        });
        new MsgAllListPresenterImp(this);

    }


    @Override
    public void updateOrderList(List<Map<String, Object>> data) {
        mOrderListAdapter.refreshData(data);
    }
    @Override
    public void updateChatList(List<Map<String, Object>> data) {
        mChatListAdapter.refreshData(data);
    }
    @Override
    public void setPresenter(MsgAllListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


}

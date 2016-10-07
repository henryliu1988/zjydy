package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.MsgAllListContract;
import com.zhjydy.presenter.presenterImp.MsgAllListPresenterImp;
import com.zhjydy.view.adapter.MsgListAdapter;
import com.zhjydy.view.zhview.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class MsgAllListFragment extends StatedFragment implements MsgAllListContract.View{
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.msg_list_view_order)
    ListViewForScrollView msgListViewOrder;
    @BindView(R.id.msg_list_view_chat)
    ListViewForScrollView msgListViewChat;


    private MsgListAdapter mOrderListAdapter;
    private MsgListAdapter mChatListAdapter;

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

            }
        });
        new MsgAllListPresenterImp(this);
        mOrderListAdapter = new MsgListAdapter(getContext(),orderList);
        mOrderListAdapter = new MsgListAdapter(getContext(),chatList);

    }


    @Override
    public void updateOrderList(List<Map<String, Object>> data) {
        mOrderListAdapter.refreshData(data);
    }
    @Override
    public void updateChatList(List<Map<String, Object>> data) {
        mOrderListAdapter.refreshData(data);
    }
    @Override
    public void setPresenter(MsgAllListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


}

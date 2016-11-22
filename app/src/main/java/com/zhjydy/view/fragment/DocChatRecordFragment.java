package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.ChatRecordContract;
import com.zhjydy.presenter.presenterImp.ChatRecordPresenterImp;
import com.zhjydy.view.zhview.ArrowTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class DocChatRecordFragment extends PageImpBaseFragment implements ChatRecordContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.chat_layout)
    LinearLayout chatLayout;
    private ChatRecordContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doc_chat_record;
    }

    @Override
    protected void afterViewCreate() {

        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        new ChatRecordPresenterImp(this);
        initChatLayout();
    }

    @Override
    public void setPresenter(ChatRecordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    private void initChatLayout() {
       // String msg = getArguments().getString("msg");
        String name = "王医生";
        String time1 = "20:12";
        String time2 = "210dfsad";

        String ms1 = "fdhsjkffsdkfds";
        String ms2 = "房价开始对房价肯定是粉红色产生的反感的是否收到对方给好友度的人官方刚刚灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌";


        for(int i = 0 ; i < 4; i ++) {
            LinearLayout right =(LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.chat_record_right,null);
            TextView timeR = (TextView)right.findViewById(R.id.time);
            TextView msgR = (TextView)right.findViewById(R.id.msg);
            ImageView photoR = (ImageView) right.findViewById(R.id.photo);

            timeR.setText(time1);
            msgR.setText(ms1);

            LinearLayout left =(LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.chat_record_left,null);
            TextView timeL = (TextView)left.findViewById(R.id.time);
            ArrowTextView msgL = (ArrowTextView)left.findViewById(R.id.msg);
            ImageView photoL = (ImageView) left.findViewById(R.id.photo);

            timeL.setText(time2);
            msgL.setText(ms2);

            chatLayout.addView(right);
            chatLayout.addView(left);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

package com.zhjydy.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.presenterImp.ExpertDetailPresenterImp;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.ExperDetaiCommentListAdapter;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.ScoreView;
import com.zhjydy.view.zhview.zhToast;
import com.zhl.cbdialog.CBDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class ExpertDetailFragment extends PageImpBaseFragment implements ExpertDetailContract.View {

    @BindView(R.id.comment_make_edit)
    EditText commentMakeEdit;
    @BindView(R.id.comment_make_btn)
    TextView commentMakeBtn;
    @BindView(R.id.write_words)
    LinearLayout writeWords;
    @BindView(R.id.bottom_div)
    View bottomDiv;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.depart)
    TextView depart;
    @BindView(R.id.profession)
    TextView profession;
    @BindView(R.id.hospital)
    TextView hospital;
    @BindView(R.id.save_image)
    ImageView saveImage;
    @BindView(R.id.save_text)
    TextView saveText;
    @BindView(R.id.save_layout)
    LinearLayout saveLayout;
    @BindView(R.id.score_image)
    ImageView scoreImage;
    @BindView(R.id.score_text)
    TextView scoreText;
    @BindView(R.id.score_star)
    ScoreView scoreStar;
    @BindView(R.id.reason)
    LinearLayout reason;
    @BindView(R.id.specical)
    LinearLayout specical;
    @BindView(R.id.word_listview)
    ListView wordListview;
    @BindView(R.id.subscribe_expert)
    TextView subscribeExpert;
    @BindView(R.id.reason_tv)
    TextView reasonTv;
    @BindView(R.id.specical_tv)
    TextView specicalTv;
    private ExpertDetailContract.Presenter mPresenter;

    private ExperDetaiCommentListAdapter mCommentListAdapter;
    private View mCommentListHeaderView;

    private String id;


    private boolean isCollect = false;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_expert_detail;
    }

    @Override
    protected void afterViewCreate() {
        id = getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(id)) {
            return;
        }
        initCommentListView();
        new ExpertDetailPresenterImp(this, id);
        updateFavStatus();
    }

    private void initCommentListView() {
        mCommentListAdapter = new ExperDetaiCommentListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        wordListview.setAdapter(mCommentListAdapter);
        mCommentListHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.listview_expert_comment_header_layout, null);
        wordListview.addHeaderView(mCommentListHeaderView);
    }


    private void updateFavStatus() {
        String collect = AppData.getInstance().getToken().getCollectExperts();
        List<String> coList = new ArrayList<String>();
        if (!TextUtils.isEmpty(collect)) {
            coList = Arrays.asList(collect.split(","));
        }

    }
    @Override
    public void updateExpertInfos(Map<String, Object> expertInfo) {
        name.setText(Utils.toString(expertInfo.get("realname")));
        depart.setText(Utils.toString(expertInfo.get("office")));
        hospital.setText(Utils.toString(expertInfo.get("hospital")));
        profession.setText(Utils.toString(expertInfo.get("business")));
        reasonTv.setText(Utils.toString(expertInfo.get("reason")));
        specicalTv.setText(Utils.toString(expertInfo.get("adept")));

        int score = Utils.toInteger(expertInfo.get("stars"));
        if (score > 100) {
            score = 100;
        }
        if (score < 0) {
            score = 0;
        }
        scoreText.setText("推荐分数：" + score + "分");
        scoreStar.setScore(score,100);
        ImageUtils.getInstance().displayFromRemote(Utils.toString(expertInfo.get("path")), image);

    }

    @Override
    public void subsribExpertResult(boolean result, String msg) {
        zhToast.showToast(msg);
        if (result) {
            subscribeExpert.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateComments(List<Map<String, Object>> comments) {
        mCommentListAdapter.refreshData(comments);
        if (comments.size() < 1) {
            wordListview.setVisibility(View.GONE);
            return;
        }
        TextView commentCoutTv = (TextView)mCommentListHeaderView.findViewById(R.id.comment_count);
        commentCoutTv.setText("留言（" + comments.size() + "）");
    }

    @Override
    public void updateFavStatus(boolean isCollect) {
        if (isCollect) {
            saveText.setText("取消收藏");
            ImageUtils.getInstance().displayFromDrawable(R.mipmap.save_cancel,saveImage);
            saveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.cancelSaveExpert();
                }
            });
        } else{
            saveText.setText("收藏");
            ImageUtils.getInstance().displayFromDrawable(R.mipmap.save_start,saveImage);
            saveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.saveExpert();
                }
            });
        }
    }

    @Override
    public void setPresenter(ExpertDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void makeCommentSuccess() {
        commentMakeEdit.setText("");
    }


    private void trySubsribExpert() {
        mPresenter.getAllPatientCase().subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                if (maps == null || maps.size()< 1) {
                    gotoFragment(FragKey.patient_case_fragment);
                } else {
                    Map<String, Object> patientCase = maps.get(0);
                    mPresenter.subscribeExpert(patientCase);
                }
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

    @OnClick({R.id.comment_make_btn, R.id.title_back,R.id.subscribe_expert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_make_btn:
                String commentNew = commentMakeEdit.getText().toString();
                if (TextUtils.isEmpty(commentNew)) {
                    zhToast.showToast("留言内容不能为空");
                    return;
                }
                mPresenter.makeNewComment(commentNew);
                break;
            case R.id.title_back:
                back();
                break;
            case R.id.subscribe_expert:
                trySubsribExpert();
        }
    }
}

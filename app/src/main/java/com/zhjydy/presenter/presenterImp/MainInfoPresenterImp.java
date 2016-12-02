package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.pageload.PageLoadDataSource;
import com.zhjydy.model.pageload.RxObservalRequestHandle;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.MainInfoListAdapter;
import com.zhjydy.view.adapter.PageLoadListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainInfoPresenterImp extends PageLoadDataSource implements MainInfoContract.MainInfoPresenter
{

    private MainInfoContract.MainInfoView mView;

    public MainInfoPresenterImp(MainInfoContract.MainInfoView view, PullToRefreshListView listView)
    {
        this.mView = view;
        setPageView(listView, new MainInfoListAdapter(mView.getContext(), new ArrayList<Map<String, Object>>()));
        view.setPresenter(this);
        start();
    }

    @Override
    public void start()
    {
        refreshData();
        loadMsg();
        loadFavMsgCount();
    }

    private void loadMsg()
    {
    }

    private void loadFavMsgCount()
    {
        String collect = AppData.getInstance().getToken().getCollectNews();
        int count = 0;
        if (!TextUtils.isEmpty(collect) && collect.length() > 0)
        {
            count = Utils.getCountOfString(collect, ",");
            count++;
        }
        if (mView != null)
        {
            mView.updateFavInfoCount(count);
        }

    }

    @Override
    public void finish()
    {

    }

    @Override
    public RequestHandle loadListData(final ResponseSender<List<Map<String, Object>>> sender, int page)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("pagesize", PAGE_SIZE);
        Subscription subscription = WebCall.getInstance().call(WebKey.func_getNewsList, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                int total = Utils.toInteger(map.get("count"));
                int currentPage = Utils.toInteger(map.get("page"));
                mPage = currentPage;
                mTotalCount = total;
                sender.sendData(list);

            }

            @Override
            public void onError(Throwable e)
            {
                Exception exption = new Exception(new Throwable());
                sender.sendError(exption);
            }
        });
        RxObservalRequestHandle handle = new RxObservalRequestHandle(subscription);
        return handle;

    }
}

package com.zhjydy.presenter.presenterImp;


import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.pageload.PageLoadDataSource;
import com.zhjydy.model.pageload.RxObservalRequestHandle;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithKey;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.PageLoadListAdapter;
import com.zhjydy.view.zhview.zhToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainExpertTabPresenterImp extends PageLoadDataSource implements  MainExpertContract.TabPresenter
{

    private MainExpertContract.TabView mView;
    private int funcKey;
    public MainExpertTabPresenterImp(MainExpertContract.TabView view, PullToRefreshListView listView, PageLoadListAdapter adapter, int funcKey)
    {
        mView = view;
        this.funcKey = funcKey;
        setPageView(listView, adapter);
        view.setPresenter(this);
        start();
    }

    @Override
    public void start()
    {
        reloadExperts();
    }


    public void reloadExperts()
    {
        refreshData();
    }



    @Override
    public void finish()
    {
    }






    @Override
    public RequestHandle loadListData(final ResponseSender<List<Map<String, Object>>> sender, final int page)
    {
        HashMap<String, Object> params = new HashMap<>();

        if (mView != null)
        {
            params.putAll(mView.getFilterConditions());
        }
        params.put("page", page);
        params.put("pagesize", PAGE_SIZE);
        params.put("memberid", UserData.getInstance().getToken().getId());
        final Subscription subscription = WebCall.getInstance().call(funcKey, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                int total = Utils.toInteger(map.get("count"));
                mPage = page;
                mTotalCount = total;
                sender.sendData(list);
            }

            @Override
            public void onError(Throwable e)
            {
                super.onError(e);
                Exception exption = new Exception(new Throwable());
                sender.sendError(exption);
            }

        });

        RxObservalRequestHandle handle = new RxObservalRequestHandle(subscription);
        return handle;

    }



}

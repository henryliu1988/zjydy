package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.mvc.MVCPullrefshHelper;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.DistricPickViewData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.pageload.PageLoadDataSource;
import com.zhjydy.model.pageload.RxObservalRequestHandle;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.contract.MainHomeContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.PageLoadListAdapter;
import com.zhjydy.view.fragment.FragmentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainExpertPresenterImp extends PageLoadDataSource implements MainExpertContract.MainExpertPresenter
{

    private MainExpertContract.MainExpertView mView;

    public MainExpertPresenterImp(MainExpertContract.MainExpertView view, PullToRefreshListView listView, PageLoadListAdapter adapter)
    {
        mView = view;
        setPageView(listView, adapter);
        view.setPresenter(this);
        start();
    }

    @Override
    public void start()
    {
        loadFilterDatas();
        reloadExperts();
    }


    private void loadFilterDatas()
    {
        loadDistricPickData();
        loadOfficeData();
        loadBusinessData();
        loadExpertsFavNum();
    }

    private void loadOfficeData()
    {
        ArrayList<NormalPickViewData> officeData = new ArrayList<>();
        NormalDicItem itemDefualt = new NormalDicItem();
        itemDefualt.setId("");
        itemDefualt.setName("全部");
        NormalPickViewData defaul = new NormalPickViewData(itemDefualt);
        officeData.add(defaul);
        List<NormalDicItem> items = DicData.getInstance().getOffice();
        for (NormalDicItem item : items)
        {
            officeData.add(new NormalPickViewData(item));
        }
        mView.updateOffice(officeData);
    }

    private void loadDistricPickData()
    {
        DicData.getInstance().getAllDistrictForPicker().subscribe(new BaseSubscriber<Map<String, ArrayList>>()
        {
            @Override
            public void onNext(Map<String, ArrayList> map)
            {
                District defaultDs = new District();
                defaultDs.setId("");
                defaultDs.setName("全部");
                DistricPickViewData viewDefuatData = new DistricPickViewData(defaultDs);

                ArrayList<DistricPickViewData> viewListDefault = new ArrayList<DistricPickViewData>();
                viewListDefault.add(viewDefuatData);

                ArrayList<ArrayList<DistricPickViewData>> view2ListDefault = new ArrayList<ArrayList<DistricPickViewData>>();
                view2ListDefault.add(viewListDefault);

                ArrayList<DistricPickViewData> proPickViewData = (ArrayList<DistricPickViewData>) map.get("pro");
                ArrayList<ArrayList<DistricPickViewData>> cityPickViewData = (ArrayList<ArrayList<DistricPickViewData>>) map.get("city");
                ArrayList<ArrayList<ArrayList<DistricPickViewData>>> quPickViewData = (ArrayList<ArrayList<ArrayList<DistricPickViewData>>>) map.get("qu");
                proPickViewData.add(0, viewDefuatData);
                cityPickViewData.add(0, viewListDefault);
                quPickViewData.add(0, view2ListDefault);
                mView.updateDistrict(map);
            }
        });
    }

    private void loadBusinessData()
    {
        ArrayList<NormalPickViewData> businessData = new ArrayList<>();
        NormalDicItem itemDefualt = new NormalDicItem();
        itemDefualt.setId("");
        itemDefualt.setName("全部");
        NormalPickViewData defaul = new NormalPickViewData(itemDefualt);

        businessData.add(defaul);
        List<NormalDicItem> items = DicData.getInstance().getBusiness();
        for (NormalDicItem item : items)
        {
            businessData.add(new NormalPickViewData(item));
        }
        mView.updateBusiness(businessData);
    }



    public void reloadExperts()
    {
        refreshData();
    }

    private void loadExpertsFavNum()
    {
        String collect = AppData.getInstance().getToken().getCollectExperts();
        int count = 0;
        if (!TextUtils.isEmpty(collect) && collect.length() > 0)
        {
            count = Utils.getCountOfString(collect, ",");
            count += 1;
        }
        if (mView != null)
        {
            mView.updateFavExpertCount(count);
        }
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

        Subscription subscription = WebCall.getInstance().call(WebKey.func_getExpertsList, params).subscribe(new BaseSubscriber<WebResponse>()
        {
            @Override
            public void onNext(WebResponse webResponse)
            {
                String data = webResponse.getReturnData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(map.get("data"));
                int total = Utils.toInteger(map.get("count"));
                int currentPage = Utils.toInteger(map.get("page"));
                mPage = page;
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

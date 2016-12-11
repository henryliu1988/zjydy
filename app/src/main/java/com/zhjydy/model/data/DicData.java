package com.zhjydy.model.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zhjydy.model.entity.DistricPickViewData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.HospitalDicItem;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.preference.SPUtils;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class DicData {

    public static final int DIC_OFFICE = 1;
    public static final int DIC_BUSINESS = 2;
    public static final int DIC_HOSPITAL = 3;

    public static DicData instance = new DicData();

    public static DicData getInstance() {
        return instance;
    }


    private String officeListData;
    private String businessListData;
    private String hospitalListData;
    private String prosListData;
    private String cityListData;
    private String quListData;
    private String orderCancelReason;

    private List<District> mProList = new ArrayList<>();
    private List<District> mCityList = new ArrayList<>();
    private List<District> mQuList = new ArrayList<>();

    public void init() {
        loadOffice();
        loadBusiness();
        loadHospital();
        loadDistrict();
        loadOrderCanCelReson();
    }


    public List<NormalDicItem> getOffice() {
        List<NormalDicItem> list = new ArrayList<>();
        if (TextUtils.isEmpty(officeListData)) {
            officeListData = (String) SPUtils.get("office_dic", "");
        }
        if (TextUtils.isEmpty(officeListData)) {
            return new ArrayList<>();
        }
        list = JSON.parseObject(officeListData, new TypeReference<List<NormalDicItem>>() {
        });
        return list;

    }

    public List<NormalDicItem> getBusiness() {
        List<NormalDicItem> list = new ArrayList<>();
        if (TextUtils.isEmpty(businessListData)) {
            businessListData = (String) SPUtils.get("business_dic", "");
        }
        if (TextUtils.isEmpty(businessListData)) {
            return new ArrayList<>();
        }
        list = JSON.parseObject(businessListData, new TypeReference<List<NormalDicItem>>() {

        });
        return list;

    }


    public List<HospitalDicItem> getHospitals() {
        List<HospitalDicItem> list = new ArrayList<>();
        if (TextUtils.isEmpty(hospitalListData)) {
            hospitalListData = (String) SPUtils.get("hospital_dic", "");
        }
        if (TextUtils.isEmpty(hospitalListData)) {
            return new ArrayList<>();
        }
        list = JSON.parseObject(hospitalListData, new TypeReference<List<HospitalDicItem>>() {
        });
        return list;
    }


    public List<District> getAllPros() {
        if (mProList == null || mProList.size() < 1) {
            prosListData = (String) SPUtils.get("pro_dic", "");
            mProList = JSON.parseObject(prosListData, new TypeReference<List<District>>() {
            });
        }
        return mProList;
    }

    public List<District> getAllCities() {
        if (mCityList == null || mCityList.size() < 1) {
            cityListData = (String) SPUtils.get("city_dic", "");
            mCityList = JSON.parseObject(cityListData, new TypeReference<List<District>>() {
            });
        }
        return mCityList;
    }


    public List<District> getAllQus() {
        if (mQuList == null || mQuList.size() < 1) {
            quListData = (String) SPUtils.get("qu_dic", "");
            mQuList = JSON.parseObject(quListData, new TypeReference<List<District>>() {
            });
        }
        return mQuList;
    }
    public  Map<String,District> getAllProsMap() {
        Map<String,District> map = new HashMap<>();

        if (mProList == null || mProList.size() < 1) {
            prosListData = (String) SPUtils.get("pro_dic", "");
            mProList = JSON.parseObject(prosListData, new TypeReference<List<District>>() {
            });
        }
        for(District p:mProList) {
            map.put(p.getId(),p);
        }
        return map;
    }

    public  Map<String,District>  getAllCitiesMap() {
        Map<String,District> map = new HashMap<>();
        if (mCityList == null || mCityList.size() < 1) {
            cityListData = (String) SPUtils.get("city_dic", "");
            mCityList = JSON.parseObject(cityListData, new TypeReference<List<District>>() {
            });
        }
        for(District c:mCityList) {
            map.put(c.getId(),c);
        }
        return map;
    }


    public  Map<String,District>  getAllQusMap() {
        List<District> qus = new ArrayList<>();
        Map<String,District> map = new HashMap<>();

        if (mQuList == null || mQuList.size() < 1) {
            quListData = (String) SPUtils.get("qu_dic", "");
            qus = JSON.parseObject(quListData, new TypeReference<List<District>>() {
            });
        }
        for(District q:qus) {
            map.put(q.getId(),q);
        }
        return map;
    }

    public List<NormalDicItem> getOrderReasons() {
        List<NormalDicItem> list = new ArrayList<>();
        if (TextUtils.isEmpty(orderCancelReason)) {
            orderCancelReason = (String) SPUtils.get("order_cancel", "");
        }
        if (TextUtils.isEmpty(orderCancelReason)) {
            return new ArrayList<>();
        }
        List<Map<String,Object>> datas = Utils.parseObjectToListMapString(orderCancelReason);
        for (Map<String,Object> m:datas) {
            NormalDicItem item = new NormalDicItem();
            item.setId(Utils.toString(m.get("id")));
            item.setName(Utils.toString(m.get("content")));
            list.add(item);
        }
        return list;
    }


    public List<NormalItem> getSex() {
        List<NormalItem> list = new ArrayList<>();
        list.add(new NormalItem("1","男"));
        list.add(new NormalItem("2","女"));
        return list;
    }

    public NormalItem getSexById(String id) {
        List<NormalItem> list = getSex();
        for (NormalItem item:list) {
            if (item.getId().equals(id)){
                return item;
            }
        }
        return new NormalItem(id,id);
    }

    public HospitalDicItem getHospitalById(String id) {
        List<HospitalDicItem> hosList = getHospitals();
        for (HospitalDicItem hos:hosList ) {
            if (hos.getId().equals(id)){
                return hos;
            }
        }
        return new HospitalDicItem();
    }

    public NormalDicItem getOfficeById(String id) {
        List<NormalDicItem> offList = getOffice();
        for (NormalDicItem off:offList ) {
            if (off.getId().equals(id)){
                return off;
            }
        }
        return new NormalDicItem();
    }
    public NormalDicItem getBusinessById(String id) {
        List<NormalDicItem> busList = getBusiness();
        for (NormalDicItem bus:busList ) {
            if (bus.getId().equals(id)){
                return bus;
            }
        }
        return new NormalDicItem();
    }

    public List<District> getDistrictById1(String id) {
        List<District> list = new ArrayList<>();
        if (TextUtils.isEmpty(id)) {
            District d = new District();
            d.setName("未知地区");
            list.add(d);
            return list;
        }


        Map<String,District> pros = getAllProsMap();
        Map<String,District> cities = getAllCitiesMap();
        Map<String,District> qus = getAllQusMap();

        District qu  = qus.get(id);
        if (qu != null) {
            list.add(qu);
            String cityId = qu.getParentid();
            District city = cities.get(cityId);
            if (city != null) {
                list.add(city);
                String proId = city.getParentid();
                District pro = pros.get(proId);
                if (pro != null) {
                    list.add(pro);
                }
            }
        }
        return  list;
    }

    public List<District> getDistrictById(String id) {
        List<District> list = new ArrayList<>();
        if (TextUtils.isEmpty(id)) {
            District d = new District();
            d.setName("未知地区");
            list.add(d);
            return list;
        }
        List<District> pros = getAllPros();
        List<District> cities = getAllCities();
        List<District> qus = getAllQus();
        boolean isQu = false;
        for (District qu:qus) {
            String quId = qu.getId();
            if(id.equals(quId)) {
                list.add(qu);
                String parent = qu.getParentid();
                for (District city:cities) {
                    String cityId= city.getId();
                    if (parent.equals(cityId)) {
                        list.add(city);
                        for (District pro:pros){
                            if(pro.getId().equals(city.getParentid())){
                                list.add(pro);
                                break;
                            }
                        }
                        break;
                    }
                }
                isQu = true;
                break;
            }
        }
        boolean isCity = false;
        if (!isQu) {
            for (District city:cities) {
                String cityId= city.getId();
                if (id.equals(cityId)) {
                    isCity = true;
                    list.add(city);
                    for (District pro:pros){
                        if(pro.getId().equals(city.getParentid())){
                            list.add(pro);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if (!isQu && !isCity) {
            for(District pro:pros) {
                String proId = pro.getId();
                if (id.equals(proId)) {
                    list.add(pro);
                    break;
                }
            }
        }
        return list;

    }



    private ArrayList<DistricPickViewData> mProPickViewData;
    private ArrayList<ArrayList<DistricPickViewData>> mCityPickViewData;
    private ArrayList<ArrayList<ArrayList<DistricPickViewData>>> mQuPickViewData;
    public Observable<Map<String, ArrayList>> getAllDistrictForPicker() {
        return Observable.create(new Observable.OnSubscribe<Map<String, ArrayList>>() {
            @Override
            public void call(Subscriber<? super Map<String, ArrayList>> subscriber) {

                if (mProPickViewData == null || mProPickViewData.size()< 1 || mCityPickViewData == null
                        || mCityPickViewData.size()< 1|| mQuPickViewData == null || mQuPickViewData.size()< 1)
                {
                    mProPickViewData = new ArrayList<>();
                    mCityPickViewData = new ArrayList<>();
                    mQuPickViewData = new ArrayList<>();
                    List<District> pros = new ArrayList<>();
                    pros.addAll(getAllPros());
                    List<District> citys = new ArrayList<>();
                    citys.addAll(getAllCities());
                    List<District> qus = new ArrayList<>();
                    qus.addAll(getAllQus());
                    for (District pro : pros) {
                        mProPickViewData.add(new DistricPickViewData(pro));
                        ArrayList<DistricPickViewData> cityPickList = new ArrayList<>();
                        ArrayList<ArrayList<DistricPickViewData>> cityQuPickList = new ArrayList<>();
                        Iterator<District> itC = citys.iterator();
                        while (itC.hasNext()) {
                            ArrayList<DistricPickViewData> quPickList = new ArrayList<>();
                            District city = itC.next();
                            if (!TextUtils.isEmpty(city.getParentid()) && city.getParentid().equals(pro.getId())) {
                                cityPickList.add(new DistricPickViewData(city));
                                Iterator<District> itQu = qus.iterator();
                                while (itQu.hasNext()) {
                                    District qu = itQu.next();
                                    if (!TextUtils.isEmpty(qu.getParentid()) && qu.getParentid().equals(city.getId())) {
                                        quPickList.add(new DistricPickViewData(itQu.next()));
                                        itQu.remove();
                                    }
                                }
                                cityQuPickList.add(quPickList);
                                itC.remove();
                            }
                        }

                        mCityPickViewData.add(cityPickList);
                        mQuPickViewData.add(cityQuPickList);
                    }

                }
                Map<String, ArrayList> map = new HashMap<String, ArrayList>();
                map.put("pro", mProPickViewData);
                map.put("city", mCityPickViewData);
                map.put("qu", mQuPickViewData);
                subscriber.onNext(map);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void loadOffice() {
        WebCall.getInstance().call(WebKey.func_getoffice, new HashMap<String, Object>()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                officeListData = data;
                SPUtils.put("office_dic", data);
                List<NormalDicItem> list = new ArrayList<NormalDicItem>();
                list = JSON.parseObject(data, new TypeReference<List<NormalDicItem>>() {
                        }
                );
            }
        });
    }


    private void loadBusiness() {
        WebCall.getInstance().call(WebKey.func_getbusiness, new HashMap<String, Object>()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                businessListData = data;
                SPUtils.put("business_dic", data);
                List<NormalDicItem> list = new ArrayList<NormalDicItem>();
                list = JSON.parseObject(data, new TypeReference<List<NormalDicItem>>() {
                        }
                );
            }
        });
    }

    private void loadDistrict() {
        WebCall.getInstance().call(WebKey.func_getpro, new HashMap<String, Object>()).map(new Func1<WebResponse, List<District>>() {
            @Override
            public List<District> call(WebResponse webResponse) {
                String data = webResponse.getData();
                SPUtils.put("pro_dic", data);
                prosListData = data;
                mProList = JSON.parseObject(data, new TypeReference<List<District>>() {
                });
                return mProList;
            }
        }).subscribe(new BaseSubscriber<List<District>>() {
            @Override
            public void onNext(List<District> districts) {

            }
        });
        WebCall.getInstance().call(WebKey.func_getCity, new HashMap<String, Object>()).map(new Func1<WebResponse, List<District>>() {
            @Override
            public List<District> call(WebResponse webResponse) {
                String data = webResponse.getData();
                cityListData = data;
                SPUtils.put("city_dic", data);
                mCityList = JSON.parseObject(data, new TypeReference<List<District>>() {
                });
                ;
                return mCityList;
            }
        }).subscribe(new BaseSubscriber<List<District>>() {
            @Override
            public void onNext(List<District> districts) {

            }
        });
        WebCall.getInstance().call(WebKey.func_getQu, new HashMap<String, Object>()).map(new Func1<WebResponse, List<District>>() {
            @Override
            public List<District> call(WebResponse webResponse) {
                String data = webResponse.getData();
                quListData = data;
                SPUtils.put("qu_dic", data);
                mQuList = JSON.parseObject(data, new TypeReference<List<District>>() {
                });
                return mQuList;
            }
        }).subscribe(new BaseSubscriber<List<District>>() {
            @Override
            public void onNext(List<District> districts) {

            }
        });
    }

    private void loadHospital() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("pagesize", Utils.toString(1000000000));
        WebCall.getInstance().call(WebKey.func_getHospital, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                hospitalListData = data;
                SPUtils.put("hospital_dic", data);
                List<HospitalDicItem> list = new ArrayList<>();
                list = JSON.parseObject(hospitalListData, new TypeReference<List<HospitalDicItem>>() {
                });
            }
        });
    }

    private  void loadOrderCanCelReson() {
        WebCall.getInstance().call(WebKey.func_getCancelReason,new HashMap<String, Object>()).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                orderCancelReason = data;
                SPUtils.put("order_cancel", data);
            }
        });
    }
    public NormalDicItem getDicById(int type, String id) {
        switch (type) {
            case DIC_BUSINESS:
                List<NormalDicItem> business = getBusiness();
                for (int i = 0; i < business.size(); i++) {
                    NormalDicItem item = business.get(i);
                    if (checkDicItem(item) && item.getId().equals(id)) {
                        return item;
                    }
                }
                break;
            case DIC_OFFICE:
                List<NormalDicItem> office = getOffice();
                for (int i = 0; i < office.size(); i++) {
                    NormalDicItem item = office.get(i);
                    if (checkDicItem(item) && item.getId().equals(id)) {
                        return item;
                    }
                }
                break;

        }
        return new NormalDicItem();
    }

    public List<HospitalDicItem> getHospitalByAddress(String addessId) {
        List<HospitalDicItem> list = getHospitals();
        List<HospitalDicItem> selList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String address = list.get(i).getAddress();
            if (!TextUtils.isEmpty(address) && address.equals(addessId)) {
                selList.add(list.get(i));
            }
        }
        return selList;
    }


    public List<NormalItem> getAllOrderStatus() {
        List<NormalItem> items = new ArrayList<>();
        items.add(new NormalItem("1","预约"));
        items.add(new NormalItem("2","专家确认"));
        items.add(new NormalItem("3","患者支付"));
        items.add(new NormalItem("4","申请退单"));
        items.add(new NormalItem("5","完成"));
        items.add(new NormalItem("6","患者取消预约"));
        items.add(new NormalItem("7","专家未接受预约"));
        return  items;
    }

    public NormalItem getOrderStatuById(String id) {
        List<NormalItem> items = getAllOrderStatus();
        for (NormalItem item:items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return new NormalItem(id,id);
    }
    private boolean checkDicItem(NormalDicItem item) {
        return item != null && !TextUtils.isEmpty(item.getId());
    }

    public String getDisStatusById(String id) {
        int idInt = Utils.toInteger(id);
        switch (idInt) {
            case 1:
                return "预约中";
            case 2:
                return "已确认预约";
            case 3:
                return "订单进行中";
        }
        return "";
    }
}

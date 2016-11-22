package com.zhjydy.model.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.HospitalDicItem;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.preference.SPUtils;
import com.zhjydy.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func3;

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
    public void init() {
        loadOffice();
        loadBusiness();
        loadHospital();
        loadDistrict();
    }

    public List<NormalDicItem> getOffice() {
        List<NormalDicItem> list = new ArrayList<>();
        if (TextUtils.isEmpty(officeListData)) {
            officeListData = (String) SPUtils.get("office_dic", "");
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
        list = JSON.parseObject(businessListData, new TypeReference<List<NormalDicItem>>() {

        });
        return list;

    }


    public List<HospitalDicItem> getHospitals() {
        List<HospitalDicItem> list = new ArrayList<>();
        if (TextUtils.isEmpty(hospitalListData)) {
            businessListData = (String) SPUtils.get("hospital_dic", "");
        }
        list = JSON.parseObject(hospitalListData, new TypeReference<List<HospitalDicItem>>() {
        });
        return list;
    }


    public List<District> getAllPros() {
        List<District> pros = new ArrayList<>();
        if(TextUtils.isEmpty(prosListData)) {
            prosListData = (String)SPUtils.get("pro_dic","");
        }
        pros =JSON.parseObject(prosListData, new TypeReference<List<District>>() {
        });
        return pros;
    }
    public List<District> getAllCities() {
        List<District> city = new ArrayList<>();
        if(TextUtils.isEmpty(cityListData)) {
            cityListData = (String)SPUtils.get("city_dic","");
        }
        city =JSON.parseObject(cityListData, new TypeReference<List<District>>() {
        });
        return city;
    }
    public List<District> getAllQus() {
        List<District> qus = new ArrayList<>();
        if(TextUtils.isEmpty(quListData)) {
            quListData = (String)SPUtils.get("qu_dic","");
        }
        qus =JSON.parseObject(quListData, new TypeReference<List<District>>() {
        });
        return qus;
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
        WebCall.getInstance().call(WebKey.func_getpro,new HashMap<String, Object>()).map(new Func1<WebResponse, List<District>>() {
            @Override
            public List<District> call(WebResponse webResponse) {
                String data = webResponse.getData();
                SPUtils.put("pro_dic",data);
                prosListData = data;
                List<District> pros = JSON.parseObject(data, new TypeReference<List<District>>() {
                });
                return pros;
            }
        }).subscribe(new BaseSubscriber<List<District>>() {
            @Override
            public void onNext(List<District> districts) {

            }
        });
         WebCall.getInstance().call(WebKey.func_getCity,new HashMap<String, Object>()).map(new Func1<WebResponse, List<District>>() {
            @Override
            public List<District> call(WebResponse webResponse) {
                String data = webResponse.getData();
                cityListData = data;
                SPUtils.put("city_dic",data);
                List<District> citys = JSON.parseObject(data, new TypeReference<List<District>>() {
                });;
                return citys;
            }
        }).subscribe(new BaseSubscriber<List<District>>() {
             @Override
             public void onNext(List<District> districts) {

             }
         });
        WebCall.getInstance().call(WebKey.func_getQu,new HashMap<String, Object>()).map(new Func1<WebResponse, List<District>>() {
            @Override
            public List<District> call(WebResponse webResponse) {
                String data = webResponse.getData();
                quListData = data;
                SPUtils.put("qu_dic",data);
                List<District> qus =JSON.parseObject(data, new TypeReference<List<District>>() {
                });
                return qus;
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
        return new NormalDicItem("", "");
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

    private boolean checkDicItem(NormalDicItem item) {
        return item != null && !TextUtils.isEmpty(item.getId());
    }
}

package com.zhjydy.view.zhview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhjydy.R;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;
import com.zhjydy.view.adapter.GridViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/5.
 */
public class ItemImageAddView extends BaseItemView {
    private List<Map<String, Object>> items = new ArrayList<>();
    private GridView mGirdView;
    private GridViewAdapter mAdapter;
    private Context context;
    private boolean isShowDelete = false;
    private ImageViewPopWindow mPopView;

    public List<String> getAddFileList() {
        List<String> list = new ArrayList<>();
        List<Map<String, Object>> allFiles = mAdapter.getItems();
        for (Map<String, Object> map : allFiles) {
            if (map != null && Utils.toInteger(map.get(ViewKey.FILE_KEY_TYPE)) == ViewKey.TYPE_FILE_PATH) {
                list.add(Utils.toString(map.get(ViewKey.FILE_KEY_URL)));
            }
        }
        return list;
    }

    public List<String> getDelFileList() {
        return mAdapter.getDelList();
    }

    public List<Map<String, Object>> getAllFileList() {
        return items;
    }

    public ItemImageAddView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ItemImageAddView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    @Override
    public void setItems(Object items) {
        if (items == null || !(items instanceof List)) {
            return;
        }
        List l = (List) items;
        if (l == null || l.size() < 1) {
            return;
        }
        Object ob = l.get(0);
        if (!(ob instanceof Map)) {
            return;
        }
        setItems(l);
    }

    @Override
    public Object getItems() {
        return getAllFileList();
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
        mAdapter.setList(items);
    }

    public void addImage(String path,int type) {
        Map<String,Object> item = new HashMap<>();
        item.put(ViewKey.FILE_KEY_TYPE,type);
        item.put(ViewKey.FILE_KEY_URL,path);
        items.add(item);
        mAdapter.setList(items);

    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.image_add_view, this);
        mGirdView = (GridView) this.findViewById(R.id.add_image_gridview);
        mAdapter = new GridViewAdapter(context, items);
        mPopView = new ImageViewPopWindow(context);

        mGirdView.setAdapter(mAdapter);
        mGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete == true) {
                    // 如果处于正在删除的状态，单击则删除图标消失
                    isShowDelete = false;
                    mAdapter.setIsShowDelete(isShowDelete);
                } else {

                    showPopWindow(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mGirdView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 长按显示删除图标
                if (isShowDelete == false) {
                    isShowDelete = true;
                }
                mAdapter.setIsShowDelete(isShowDelete);
                return true;
            }
        });

    }


    private void showPopWindow(int position) {
        if (mPopView != null && mPopView.isShowing()) {
            mPopView.dismiss();
            return;
        }

        Map<String, Object> item = (Map<String, Object>) mAdapter.getItem(position);
        if (item != null) {
            if (mPopView == null) {
                mPopView = new ImageViewPopWindow(context);
            }
            if (Utils.toInteger(item.get(ViewKey.FILE_KEY_TYPE)) == ViewKey.TYPE_FILE_URL) {
                String url = Utils.toString(item.get(ViewKey.FILE_KEY_URL));
                ImageViewPopWindow.ItemData itemDataTitle = new ImageViewPopWindow.ItemData("创建人", Utils.toString(item.get("create_person_name")));
                ImageViewPopWindow.ItemData itemDataMsg = new ImageViewPopWindow.ItemData("创建时间", Utils.toString(item.get("create_time")));
                List<ImageViewPopWindow.ItemData> items = new ArrayList<ImageViewPopWindow.ItemData>();
                items.add(itemDataTitle);
                items.add(itemDataMsg);
                mPopView.setUrlType(ViewKey.TYPE_FILE_URL);
                mPopView.setUrl(url);
                mPopView.setInfos(items);
            } else {
                String url = Utils.toString(item.get(ViewKey.FILE_KEY_URL));
                mPopView.setUrlType(ViewKey.TYPE_FILE_PATH);
                mPopView.setUrl(url);
            }

            mPopView.showPopupWindow(getRootView());
        }
    }


    public void refreshView() {
        if (mAdapter != null) {
            mAdapter.setList(items);
        }
    }
}

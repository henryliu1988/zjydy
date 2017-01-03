package com.zhjydy.view.zhview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zhjydy.R;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;
import com.zhjydy.view.adapter.GridViewAdapter;

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

    public void setImageSize(int width, int height) {
        if (mAdapter != null) {
            mAdapter.setItemImageSize(width, height);
        }
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

    public void addImage(String path, int type) {
        Map<String, Object> item = new HashMap<>();
        item.put(ViewKey.FILE_KEY_TYPE, type);
        item.put(ViewKey.FILE_KEY_URL, path);
        items.add(item);
        mAdapter.setList(items);

    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.image_add_view, this);
        mGirdView = (GridView) this.findViewById(R.id.add_image_gridview);
        mAdapter = new GridViewAdapter(context, items);

        mGirdView.setAdapter(mAdapter);
        mGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowDelete == true) {
                    // 如果处于正在删除的状态，单击则删除图标消失
                    isShowDelete = false;
                    mAdapter.setIsShowDelete(isShowDelete);
                } else {

                    showImageFullScreen(position);
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



    private void showImageFullScreen(int position) {
        ActivityUtils.showImageBrowse(context,getAllFileList(),position);
    }

    public void refreshView() {
        if (mAdapter != null) {
            mAdapter.setList(items);
        }
    }
}

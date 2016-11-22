package com.zhjydy.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhjydy.R;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridViewAdapter extends android.widget.BaseAdapter
{
    private boolean isDelete;  //用于删除图标的显隐
    private LayoutInflater inflater;
    private Context mContext;

    private List<Map<String, Object>> imgList;
    private List<String> delList = new ArrayList<>();

    public GridViewAdapter(Context context, List<Map<String, Object>> imgList)
    {
        this.mContext = context;
        this.imgList = imgList;
        inflater = LayoutInflater.from(context);
        delList.clear();
    }

    public void setList(List<Map<String, Object>> list)
    {
        this.imgList = list;
        //delList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        //需要额外多出一个用于添加图片
        return imgList.size() + 1;

    }

    @Override
    public Object getItem(int arg0)
    {
        return imgList.get(arg0);
    }

    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    public List<Map<String, Object>> getItems()
    {
        return imgList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2)
    {

        //初始化页面和相关控件
        convertView = inflater.inflate(R.layout.image_add_grid_item, null);
        final ImageView img_pic = (ImageView) convertView
                .findViewById(R.id.img_pic);
        LinearLayout ly = (LinearLayout) convertView
                .findViewById(R.id.layout);
        LinearLayout ll_picparent = (LinearLayout) convertView
                .findViewById(R.id.ll_picparent);
        ImageView delete = (ImageView) convertView
                .findViewById(R.id.img_delete);

        //默认的添加图片的那个item是不需要显示删除图片的
        if (imgList.size() >= 1)
        {
            if (position <= imgList.size() - 1)
            {
                ll_picparent.setVisibility(View.GONE);
                img_pic.setVisibility(View.VISIBLE);
                if (Utils.toInteger(imgList.get(position).get(ViewKey.FILE_KEY_TYPE)) == ViewKey.TYPE_FILE_PATH)
                {
                    ImageUtils.getInstance().displayFromSDCard(Utils.toString(imgList.get(position).get(ViewKey.FILE_KEY_URL)), img_pic);
                } else if (Utils.toInteger(imgList.get(position).get(ViewKey.FILE_KEY_TYPE)) == ViewKey.TYPE_FILE_URL)
                {
                    ImageUtils.getInstance().displayFromRemote(Utils.toString(imgList.get(position).get(ViewKey.FILE_KEY_URL)), img_pic);
                }
                // 设置删除按钮是否显示
                delete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
            }
        }

        //当处于删除状态时，删除事件可用
        //注意：必须放到getView这个方法中，放到onitemClick中是不起作用的。
        if (isDelete)
        {
            delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Map<String, Object> map = imgList.get(position);
                    if (map != null && Utils.toInteger(map.get(ViewKey.FILE_KEY_TYPE)) == ViewKey.TYPE_FILE_URL)
                    {
                        delList.add(Utils.toString(map.get(ViewKey.FILE_KEY_URL)));
                    }
                    imgList.remove(position);
                    GridViewAdapter.this.notifyDataSetChanged();

                }
            });
        }

        return convertView;
    }

    /**
     * 设置是否显示删除图片
     *
     * @param isShowDelete
     */
    public void setIsShowDelete(boolean isShowDelete)
    {
        this.isDelete = isShowDelete;
        notifyDataSetChanged();
    }

    public List<String> getDelList()
    {
        return delList;
    }

}  
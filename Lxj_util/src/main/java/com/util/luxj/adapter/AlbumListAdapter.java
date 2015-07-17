package com.util.luxj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.util.luxj.R;
import com.util.luxj.model.ImageFolder;
import com.util.luxj.util.AsyncImageLoader;

import java.util.List;

/**
 * Created by Luxj on 2015/7/17 11:09
 */
public class AlbumListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private AsyncImageLoader imageLoader = AsyncImageLoader.getInstance();
    private List<ImageFolder> mImgArray;

    public AlbumListAdapter(Context aContext) {
        mLayoutInflater = LayoutInflater.from(aContext);
        mContext = aContext;
        imageLoader.init(aContext);
    }

    public void setData(List<ImageFolder> mArray) {
        mImgArray = mArray;
        L.e(mImgArray + "===");
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mImgArray != null)
            return mImgArray.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mImgArray != null)
            return mImgArray.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(R.layout.album_list_item, parent, false);
            holder.mImage = (ImageView) convertView.findViewById(R.id.m_list_img);
            holder.mDirName = (TextView) convertView.findViewById(R.id.m_list_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mDirName.setText(mImgArray.get(position).getName());
        imageLoader.setSyncHeadImages(holder.mImage, mImgArray.get(position).getFirstImagePath());
        return convertView;
    }

    class Holder {
        ImageView mImage;
        TextView mDirName;
    }
}

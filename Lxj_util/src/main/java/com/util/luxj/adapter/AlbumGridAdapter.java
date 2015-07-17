package com.util.luxj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.util.luxj.R;
import com.util.luxj.listener.AlbumCheckListener;
import com.util.luxj.model.Picture;
import com.util.luxj.util.AsyncImageLoader;

/**
 * Created by Luxj on 2015/7/16 11:47
 */
public class AlbumGridAdapter extends BaseAdapter {
    private Context mContext;
    private Picture[] mImgArray;
    private LayoutInflater mLayoutInflater;
    private AsyncImageLoader imageLoader = AsyncImageLoader.getInstance();
    private AlbumCheckListener mAlbumCheckListener;
    private int mSize, mNowSize, position;

    public AlbumGridAdapter(Context aContext, AlbumCheckListener aListener) {
        mLayoutInflater = LayoutInflater.from(aContext);
        mContext = aContext;
        mAlbumCheckListener = aListener;
        imageLoader.init(aContext);
    }

    public void setData(Picture[] mArray) {
        mImgArray = mArray;
        notifyDataSetChanged();
    }

    public void setSize(int aSize, int aNowSize) {
        mSize = aSize;
        mNowSize = aNowSize;
    }

    @Override
    public int getCount() {
        if (mImgArray != null) {
            return mImgArray.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mImgArray != null) {
            return mImgArray[position];
        }
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
            convertView = mLayoutInflater.inflate(R.layout.album_all_item, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.img);
            holder.selected_img = (CheckBox) convertView.findViewById(R.id.selected_img);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        this.position = position;
        if (mImgArray[position].isSelect()) {
            holder.selected_img.setChecked(true);
        } else {
            holder.selected_img.setChecked(false);
        }
        holder.image.setTag(holder);
        holder.image.setOnClickListener(mOnClickListener);
        holder.selected_img.setTag(R.id.tag_second, position);
        holder.selected_img.setTag(R.id.tag_first, mImgArray[position].getPath());
        holder.selected_img.setOnCheckedChangeListener(mOnCheckedChangeListener);
        imageLoader.setSyncHeadImages(holder.image, mImgArray[position].getPath());
        return convertView;
    }

    class Holder {
        ImageView image;
        CheckBox selected_img;
    }

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (mAlbumCheckListener != null) {
                mAlbumCheckListener.onPhotoSelected(buttonView.getTag(R.id.tag_first).toString(), (Integer) buttonView.getTag(R.id.tag_second), isChecked, mNowSize);
            }
        }
    };
    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Holder holder = (Holder) v.getTag();
            if (holder.selected_img.isChecked()) {
                holder.selected_img.setChecked(false);
                mNowSize--;
            } else {
                if (mNowSize < mSize) {
                    mNowSize++;
                } else {
                    Toast.makeText(mContext, "不能选择超过" + mSize + "张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.selected_img.setChecked(true);
            }

        }
    };


}



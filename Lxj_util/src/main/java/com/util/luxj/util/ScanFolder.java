package com.util.luxj.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.util.luxj.model.Picture;

import java.util.HashSet;

/**
 * 扫描照片类
 * Created by Luxj on 2015/7/16 17:32
 */
public class ScanFolder {
    private HashSet<String> mDirPaths;// 临时的辅助类，用于防止同一个文件夹的多次扫描

    public Picture[] getFolder(Context aContext, Picture[] aDir) {
        try {
            mDirPaths = new HashSet<String>();
            String firstImage = null;
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = aContext.getContentResolver();
            // 只查询jpeg和png的图片
            Cursor mCursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg",
                    "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            aDir = new Picture[mCursor.getCount()];
            int mNow = 0;
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Picture mPictuer = new Picture();
                mPictuer.setPath("file://" + path);
                mPictuer.setSelect(false);
                aDir[mNow] = mPictuer;
                mNow++;
            }
            mCursor.close();
            // 扫描完成，辅助的HashSet也就可以释放内存了
            mDirPaths = null;
            // 通知Handler扫描图片完成
            if (aDir.length > 0) {
                return aDir;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}

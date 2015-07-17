package com.util.luxj.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.nostra13.universalimageloader.utils.L;
import com.util.luxj.model.ImageFolder;
import com.util.luxj.model.Picture;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 扫描照片类
 * Created by Luxj on 2015/7/16 17:32
 */
public class ScanFolder {
    private HashSet<String> mDirPaths;// 临时的辅助类，用于防止同一个文件夹的多次扫描
    private int totalCount = 0;
    private List<ImageFolder> mImageFolders;// 扫描拿到所有的图片文件夹

    private volatile static ScanFolder instance;

    public static ScanFolder getInstance() {
        if (instance == null) {
            synchronized (ScanFolder.class) {
                if (instance == null) {
                    instance = new ScanFolder();
                }
            }
        }
        return instance;
    }

    /**
     * 从cd卡取所有JPG/PNG格式的照片
     *
     * @param aContext
     * @return
     */
    public Picture[] getAllImagerForSDCard(Context aContext) {
        try {
            Picture[] aImageArray;
            mImageFolders = new ArrayList<ImageFolder>();
            mDirPaths = new HashSet<String>();
            String firstImage = null;
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = aContext.getContentResolver();
            // 只查询jpeg和png的图片
            Cursor mCursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg",
                    "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            if (mCursor == null)
                return null;
            aImageArray = new Picture[mCursor.getCount()];
            int mNow = 0;

            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String mDirName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
                Picture mPicture = new Picture();
                mPicture.setPath("file://" + path);
                mPicture.setSelect(false);
                aImageArray[mNow] = mPicture;
                mNow++;
                if (firstImage == null) {
                    firstImage = path;
                }
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                ImageFolder imageFolder = null;
                String dirPath = parentFile.getAbsolutePath();
                if (mImageFolders.size() == 0) {
                    imageFolder = new ImageFolder();
                    imageFolder.setDir("ALL");
                    imageFolder.setName("所有图片");
                    imageFolder.setFirstImagePath("file://" + path);
                    imageFolder.setCount(mCursor.getCount());
                    mImageFolders.add(imageFolder);
                }
                if (mDirPaths.contains(dirPath)) {
                    continue;
                } else {
                    mDirPaths.add(dirPath);
                    // 初始化imageFloder
                    imageFolder = new ImageFolder();
                    imageFolder.setDir(dirPath);
                    imageFolder.setName(mDirName);
                    imageFolder.setFirstImagePath("file://" + path);
                }
                if (parentFile.list() == null) {
                    continue;
                }
                int picSize = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg"))
                            return true;
                        return false;
                    }
                }).length;
                totalCount += picSize;
                imageFolder.setCount(picSize);
                mImageFolders.add(imageFolder);
            }
            mCursor.close();
            // 扫描完成，辅助的HashSet也就可以释放内存了
            mDirPaths = null;
            // 通知Handler扫描图片完成
            if (aImageArray.length > 0) {
                return aImageArray;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取所有包含图片的文件夹
     *
     * @return
     */
    public List<ImageFolder> getAllImageFolder() {
        return mImageFolders;
    }

    /**
     * 根据文件夹名称获取对应文件夹下的所有照片
     *
     * @param aContext
     * @param aDirName
     */
    public Picture[] getImagesForFoler(Context aContext, String aDirName) {
        //selection: 指定查询条件
        try {
            File mFile = new File(aDirName);
            String mPath = mFile.getName();
            String mSelection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = '" + mPath + "'";
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = aContext.getContentResolver();
            L.i("开始查询" + mPath + "下的图片");
            Cursor mCursor = mContentResolver.query(mImageUri, null, "(" + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?) and " + mSelection, new String[]{"image/jpeg",
                    "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            if (mCursor == null)
                return null;
            L.i("mCursor.getCount() = " + mCursor.getCount());
            Picture[] aImageArray = new Picture[mCursor.getCount()];
            int mNow = 0;
            while (mCursor.moveToNext()) {
                String mImagePath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Picture mPicture = new Picture();
                mPicture.setPath("file://" + mImagePath);
                mPicture.setSelect(false);
                aImageArray[mNow] = mPicture;
                mNow++;
            }
            mCursor.close();
            return aImageArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

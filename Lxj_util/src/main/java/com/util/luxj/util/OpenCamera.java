package com.util.luxj.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 相机工具类
 * Created by Luxj on 2015/7/17 14:11
 */
public class OpenCamera {
    private volatile static OpenCamera instance;
    public static final int TAKE_PHOTO = 9999;
    private String filename;
    private File fileFolder = new File(Environment.getExternalStorageDirectory() + "/lxj_app/");
    private File mTempFile;

    public static OpenCamera getInstance() {
        if (instance == null) {
            synchronized (OpenCamera.class) {
                if (instance == null) {
                    instance = new OpenCamera();
                }
            }
        }
        return instance;
    }


    public void openCamera(Activity aActivity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        filename = "temp.ddd";
        if (!fileFolder.exists()) { //
            fileFolder.mkdir();
        }
        mTempFile = new File(fileFolder, filename);
        Uri uri = Uri.fromFile(mTempFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        aActivity.startActivityForResult(intent, TAKE_PHOTO);
    }

    public String savePhoto(Activity aContext, int aResultCode, Intent aIntent) {
        if (aResultCode == Activity.RESULT_OK) {
            L.i("拍照成功");
            try {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
                String newName = format.format(date) + ".jpg";
                File newFile = new File(fileFolder, newName);
                mTempFile.renameTo(new File(fileFolder, newName));
                return newFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                L.e(e.toString() + "aIntent" + aIntent);
            }
        } else {
            L.e("拍照失败");
        }
        return null;
    }
}

package com.util.luxj.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.util.luxj.R;

/**
 * Created by Luxj on 2015/7/16 13:58
 */
public class AsyncImageLoader {
    private Context mContext;
    private ImageLoader imageLoader;
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static ImageLoaderConfiguration mImageLoaderConfiguration = null;
    private volatile static AsyncImageLoader instance;


    public static AsyncImageLoader getInstance() {
        if (instance == null) {
            synchronized (AsyncImageLoader.class) {
                if (instance == null) {
                    instance = new AsyncImageLoader();
                }
            }
        }
        return instance;
    }

    public void init(Context aContext) {
        mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(aContext).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).memoryCacheExtraOptions(480, 800)
                        // .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(mImageLoaderConfiguration);
    }

    /**
     * 默认图片
     */
    private static DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.general_default_head).showImageForEmptyUri(R.mipmap.general_default_head)
            .showImageOnFail(R.mipmap.general_default_head).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                    // .displayer(new RoundedBitmapDisplayer(20))//设置圆角
            .build();

    /**
     * 取网络头像或者本地头像
     *
     * @param aImageView
     * @param aImageUrl
     */
    public void setSyncHeadImages(ImageView aImageView, String aImageUrl) {
        try {
            ImageLoader.getInstance().displayImage(aImageUrl, aImageView, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.util.luxj.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * DPI<->PX转换
 * Created by Luxj on 2015/6/13.
 */
public class PixelFormat {
    public static int formatDipToPx(Context context, int dip) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return (int) Math.ceil(dip * dm.density);
    }

    public static int formatPxToDip(Context context, int px) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return (int) Math.ceil(((px * 160) / dm.densityDpi));
    }
}

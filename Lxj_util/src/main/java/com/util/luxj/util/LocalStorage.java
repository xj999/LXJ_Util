package com.util.luxj.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences使用
 * Created by Luxj on 2015/6/26.
 */
public class LocalStorage {
    private SharedPreferences preferences;
    public String PREFS_NAME = "lxj_sp";
    private static LocalStorage instance;
    private Context mContext;
    private static String USER_NAME = "username";

    private LocalStorage(Context context) {
        mContext = context;
        preferences = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static LocalStorage getInstance(Context context) {
        if (null == instance) {
            instance = new LocalStorage(context);
        }
        return instance;
    }

    /**
     * 清空
     */
    public void clean() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }

    public void setUserName(String user_name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, user_name);
        editor.commit();
    }

    public String getUserName() {
        return preferences.getString(USER_NAME, "");
    }


}

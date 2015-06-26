package com.util.luxj.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.util.luxj.R;

/**
 * 自定义toast
 * Created by Luxj on 2015/6/13.
 */
public class MyToast extends Toast {
    private TextView main_text;
    private View view;

    public MyToast(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.toast_layout, null);
        main_text = (TextView) view.findViewById(R.id.chapterName);
        setGravity(Gravity.BOTTOM, 0, 0);
        setView(view);
    }

    public void setGravity(int gravity) {
        setGravity(Gravity.BOTTOM, 0, 0);
    }

    public void showLong(String content) {

        if (main_text != null) {
            setDuration(Toast.LENGTH_LONG);
            main_text.setText(content);
            show();
        }
    }

    public void showShort(String content) {

        if (main_text != null) {
            setDuration(Toast.LENGTH_SHORT);
            main_text.setText(content);
            show();
        }
    }
}

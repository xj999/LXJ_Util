package com.util.luxj.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.util.luxj.R;

/**
 * Created by Luxj on 2015/8/7 15:23
 */
public class Fragment6 extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private View view;
    private Button btn;
    private LinearLayout bottom_view;
    private GestureDetector mGestureDetector;
    int lastX, lastY;
    FrameLayout.LayoutParams paramTest;
    int screenWidth, screenHeight;

    public static Fragment6 newInstance(String s) {
        Fragment6 newFragment = new Fragment6();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.tab6, null);
        btn = (Button) view.findViewById(R.id.btn);
        bottom_view = (LinearLayout) view.findViewById(R.id.bottom_view);
        mGestureDetector = new GestureDetector(this);
        bottom_view.setOnTouchListener(this);
        paramTest = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        Display dis = getActivity().getWindowManager().getDefaultDisplay();
        screenWidth = dis.getWidth();
        screenHeight = dis.getHeight();
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX=(int)event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx=(int)event.getRawX()-lastX;
                int dy=(int)event.getRawY()-lastY;

                int top=v.getTop()+dy;

                int left=v.getLeft()+dx;


                if(top<=0)
                {
                    top=0;
                }
                if(top>=screenHeight-bottom_view.getHeight())
                {
                    top=screenHeight-bottom_view.getHeight();
                }
                if(left>=screenWidth-bottom_view.getWidth())
                {
                    left=screenWidth-bottom_view.getWidth();
                }

                if(left<=0)
                {
                    left=0;
                }

                Log.e("", "onTouch ");
                v.layout(left, top, left+bottom_view.getWidth(), top+bottom_view.getHeight());
                lastX=(int)event.getRawX();
                lastY=(int)event.getRawY();
                break;

        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        FrameLayout.LayoutParams paramTest = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        paramTest.topMargin = (int) e2.getY();
        Log.e("", "onScroll  e1 " + e1.getY());
        Log.e("", "onScroll  e2 " + e2.getY());
        bottom_view.setLayoutParams(paramTest);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}

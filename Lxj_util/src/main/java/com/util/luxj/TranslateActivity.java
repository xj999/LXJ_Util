package com.util.luxj;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


/**
 * 位移动画
 */
public class TranslateActivity extends Activity implements View.OnClickListener {
    private View top, bottom;
    private Button btn;
    private boolean flag = true;//判断顶部是否收起
    Animation anim;
    private View t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        btn = (Button) findViewById(R.id.btn);
        top = findViewById(R.id.top);
        bottom = findViewById(R.id.btn_view);
        btn.setOnClickListener(this);
        t = findViewById(R.id.p);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (flag) {
                    startTopAnim(bottom);
                    startBtnAnim(v);
                } else {
                    endTopAnim(bottom);
                    endTopAnim(v);
                }
                break;
        }
    }

    private void startTopAnim(View v) {

        anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bottom.setVisibility(View.GONE);
                flag = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }

    private void startBtnAnim(View v) {
        int[] location = new int[2];
        t.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        Log.e("", "==" + x + "==="+y);
        anim = AnimationUtils.loadAnimation(this, R.anim.btn_start);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flag = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }

    private void endTopAnim(View v) {

        anim = AnimationUtils.loadAnimation(this, R.anim.end);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flag = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }

}

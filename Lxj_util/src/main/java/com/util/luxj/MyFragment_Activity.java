package com.util.luxj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.util.luxj.adapter.MyFragmentPagerAdapter;
import com.util.luxj.fragment.Fragment1;
import com.util.luxj.fragment.Fragment2;
import com.util.luxj.fragment.Fragment3;
import com.util.luxj.fragment.Fragment4;
import com.util.luxj.fragment.Fragment5;
import com.util.luxj.fragment.Fragment6;
import com.util.luxj.model.TaskType;

import java.util.ArrayList;

public class MyFragment_Activity extends FragmentActivity {
    ViewPager pager = null;
    PagerTabStrip tabStrip = null;
    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<String> titleContainer = new ArrayList<String>();
    private ArrayList<Fragment> list;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量
    private ArrayList<TaskType> tasklist;
    public static MyFragment_Activity instance;

    public static MyFragment_Activity newInstance() {
        if (instance == null) {
            instance = new MyFragment_Activity();
        }
        return instance;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        instance = this;
        pager = (ViewPager) this.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip) this.findViewById(R.id.tabstrip);
        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置tab的背景色
        //设置当前tab页签的下划线颜色
        tabStrip.setTextSpacing(200);
        //viewpager开始添加view
        //页签项

        initTask();
        InitViewPager();

    }

    public void InitViewPager() {
        list = new ArrayList<Fragment>();

        for (int i = 0; i < tasklist.size(); i++) {
            switch (tasklist.get(i).getType()) {
                case 1:
                    list.add(Fragment1.newInstance("这是单选题"));
                    break;
                case 2:
                    list.add(Fragment2.newInstance("这是多选题"));
                    break;
                case 3:
                    list.add(Fragment3.newInstance("这是逗比题"));
                    break;
                case 4:
                    list.add(Fragment4.newInstance("这是阅读题"));
                    break;
                case 5:
                    list.add(Fragment5.newInstance("这是综合题"));
                    break;
                case 6:
                    list.add(Fragment6.newInstance("这是答题卡"));
                    break;
            }
        }
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), list, titleContainer));
        pager.setCurrentItem(0);//设置当前显示标签页为第一页
        pager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int one = offset * 2 + bmpW;//两个相邻页面的偏移量

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 模拟网络回调的题目
     */
    private void initTask() {
        tasklist = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            tasklist.add(new TaskType(i, "123", i));
            titleContainer.add("第" + i + "题");
        }
    }

    /**
     * 跳转
     *
     * @param page
     */
    public void selectPage(int page) {
        pager.setCurrentItem(page);
    }

}

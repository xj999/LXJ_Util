package com.util.luxj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

        Log.e("", "this is test   branch  ");

    }

    private void initView() {
        findViewById(R.id.btn_into_sql).setOnClickListener(this);
        findViewById(R.id.btn_into_album).setOnClickListener(this);
        findViewById(R.id.btn_into_camera).setOnClickListener(this);
        findViewById(R.id.btn_into_anim).setOnClickListener(this);


    }

    private void initData() {

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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_into_sql:
                intent.setClass(MainActivity.this, SqlTestActivity.class);
                break;
            case R.id.btn_into_album:
                intent.setClass(MainActivity.this, AlbumGridActivity.class);
                break;
            case R.id.btn_into_camera:
                intent.setClass(MainActivity.this, CameraActivity.class);
                break;
            case R.id.btn_into_anim:
                intent.setClass(MainActivity.this, TranslateActivity.class);
                break;
        }

        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

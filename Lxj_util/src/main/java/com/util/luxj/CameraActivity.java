package com.util.luxj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.util.luxj.util.AsyncImageLoader;
import com.util.luxj.util.OpenCamera;

public class CameraActivity extends Activity implements View.OnClickListener {
    private Button mBtn_camera;
    private OpenCamera mOpenCamera;
    private ImageView mImg_Now_photo;
    private AsyncImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        initData();
        initView();
    }

    private void initData() {
        mOpenCamera = OpenCamera.getInstance();
        mImageLoader = AsyncImageLoader.getInstance();
        mImageLoader.init(this);
    }

    private void initView() {
        mBtn_camera = (Button) findViewById(R.id.btn_camera);
        mBtn_camera.setOnClickListener(this);
        mImg_Now_photo = (ImageView) findViewById(R.id.now_photo);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                mOpenCamera.openCamera(CameraActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OpenCamera.TAKE_PHOTO:
                mImageLoader.setSyncHeadImages(mImg_Now_photo, "file://" + mOpenCamera.savePhoto(CameraActivity.this, resultCode, data));
                break;
        }


    }
}

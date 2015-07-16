package com.util.luxj;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.GridView;

import com.nostra13.universalimageloader.utils.L;
import com.util.luxj.adapter.AlbumGridAdapter;
import com.util.luxj.listener.AlbumCheckListener;
import com.util.luxj.model.Picture;
import com.util.luxj.util.ScanFolder;
import com.util.luxj.widget.MyToast;

/**
 * Created by Luxj on 2015/7/16 11:12
 */
public class AlbumGridActivity extends Activity implements AlbumCheckListener {
    GridView mAlbumGrid;

    private ProgressDialog mProgressDialog;
    private MyToast mToast;
    private Picture[] mDir;
    private AlbumGridAdapter mAdapter;
    private ActionBar mActionBar;
    private int mSsize;
    private String mSelectDirName;
    private ScanFolder mScanFolder;
    private static final int SCANSUCCESS = 1;
    private static final int SCANERROR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_all);
        initData();
        initView();

    }

    private void initView() {
        mActionBar = getActionBar();
        mActionBar.show();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mAlbumGrid = (GridView) findViewById(R.id.album_grid);
        mAlbumGrid.setAdapter(mAdapter);
    }

    private void initData() {
        mScanFolder = new ScanFolder();
        mToast = new MyToast(this);
        mAdapter = new AlbumGridAdapter(this, this);
        mAdapter.setSize(5);
        getAllImages();
        Intent mIntent = getIntent();
        if (mIntent != null) {
            mSelectDirName = mIntent.getStringExtra("dir_size");
//            if (!mSelectDirName.isEmpty()) {
//
//            }
        }
    }

    private void getAllImages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDir = mScanFolder.getFolder(AlbumGridActivity.this, mDir);
                Message e = new Message();
                if (mDir != null) {
                    e.what = SCANSUCCESS;
                } else {
                    e.what = SCANERROR;
                }
                mHandler.sendMessage(e);

            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SCANSUCCESS:
                    setDataView();
                    break;
                case SCANERROR:
                    mToast.showShort("无数据");
                    break;
            }
        }
    };

    private void setDataView() {
        L.i("加载完成");
        mAdapter.setData(mDir);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPhotoSelected(String path, boolean select) {
        L.i("path====" + path + "selecet" + select);
    }
}

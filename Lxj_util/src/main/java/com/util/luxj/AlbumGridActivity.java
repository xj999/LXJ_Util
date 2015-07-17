package com.util.luxj;

import android.app.ActionBar;
import android.app.Activity;
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

import java.util.HashMap;

/**
 * Created by Luxj on 2015/7/16 11:12
 */
public class AlbumGridActivity extends Activity implements AlbumCheckListener {
    GridView mAlbumGrid;
    private MyToast mToast;
    private Picture[] mDir;
    private AlbumGridAdapter mAdapter;
    private ActionBar mActionBar;
    private String mSelectDirName;
    private ScanFolder mScanFolder;
    private static final int SCANSUCCESS = 1;
    private static final int SCANERROR = 0;
    private int mSize = 5;
    private int mNowSize;
    private HashMap<String, Picture[]> mDirCacheMap;
    private String mNowDirName = "ALL";

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
        mDirCacheMap = new HashMap<>();
        mScanFolder = ScanFolder.getInstance();
        mToast = new MyToast(this);
        mAdapter = new AlbumGridAdapter(this, this);
        mAdapter.setSize(mSize, mNowSize);
        getAllImages();
    }

    /**
     * 获取所有本地图片jpg/png格式
     */
    private void getAllImages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDir = mScanFolder.getAllImagerForSDCard(AlbumGridActivity.this);
                Message e = new Message();
                if (mDir != null) {
                    mDirCacheMap.put("ALL", mDir);
                    e.what = SCANSUCCESS;
                } else {
                    e.what = SCANERROR;
                }
                mHandler.sendMessage(e);

            }
        }).start();
    }

    /**
     * 根据文件夹名称获取该文件夹内所有图片
     *
     * @param aDir
     */
    private void getImagesForDir(final String aDir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message e = new Message();
                if (!mDirCacheMap.containsKey(aDir)) {
                    L.i("不存在缓存");
                    mDir = mScanFolder.getImagesForFoler(AlbumGridActivity.this, aDir);
                    if (mDir != null) {
                        e.what = SCANSUCCESS;
                        mDirCacheMap.put(aDir, mDir);
                    } else {
                        e.what = SCANERROR;
                    }
                } else {
                    mDir = mDirCacheMap.get(aDir);
                    e.what = SCANSUCCESS;
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

    /**
     * 填充数据
     */
    private void setDataView() {
        mAdapter.setSize(mSize, mNowSize);
        mAdapter.setData(mDir);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(AlbumGridActivity.this, AlbumListActivity.class);
                startActivityForResult(intent, 100);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 选择照片的回调
     *
     * @param path
     * @param position
     * @param select
     * @param select_count
     */
    @Override
    public void onPhotoSelected(String path, int position, boolean select, int select_count) {
        mNowSize = select_count;
        mDir[position].setSelect(select);
        mDirCacheMap.put(mNowDirName, mDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 100:
                    switch (resultCode) {
                        case 100:
                            mNowDirName = data.getStringExtra("mDirName");
                            getImagesForDir(mNowDirName);
                            break;
                        case 101:
                            finish();
                            break;
                    }
                    break;
            }
        }
    }
}

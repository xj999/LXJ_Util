package com.util.luxj;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.utils.L;
import com.util.luxj.adapter.AlbumListAdapter;
import com.util.luxj.model.ImageFolder;
import com.util.luxj.util.ScanFolder;
import com.util.luxj.widget.MyToast;

import java.util.List;

/**
 * 相册列表
 * Created by Luxj on 2015/7/17 10:59
 */
public class AlbumListActivity extends Activity implements AdapterView.OnItemClickListener {
    private ActionBar mActionBar;
    private List<ImageFolder> mImageFolders;// 扫描拿到所有的图片文件夹
    private ScanFolder mScanFolder;
    private static final int SCANSUCCESS = 1;
    private static final int SCANERROR = 0;
    private MyToast mToast;
    private AlbumListAdapter mAdapter;
    private ListView mFolderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list);
        initData();
        initView();
    }

    private void initView() {
        mActionBar = getActionBar();
        mActionBar.show();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mFolderList = (ListView) findViewById(R.id.album_list);
        mFolderList.setAdapter(mAdapter);
        mFolderList.setOnItemClickListener(this);

    }

    private void initData() {
        mAdapter = new AlbumListAdapter(this);
        mScanFolder = ScanFolder.getInstance();
        mToast = new MyToast(this);
        getImagesFolder();

    }

    private void getImagesFolder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mImageFolders = mScanFolder.getAllImageFolder();
                Message e = new Message();
                if (mImageFolders != null) {
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
        mAdapter.setData(mImageFolders);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("mDirName", mImageFolders.get(position).getDir());
        setResult(100, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(101, new Intent());
                finish();

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

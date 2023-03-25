package com.example.simplevideoplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final static String url = "http://baobab.kaiyanapp.com/api/v4/tabs/selected?udid=11111&vc=168&vn=3.3.1&deviceModel=Huawei6&first_channel=eyepetizer_baidu_market&last_channel=eyepetizer_baidu_market&system_version_code=20";
    private static final int MSG_VIDEO_CONTENT = 1;

    private ListView mLV;

    private VideoListAdapter mVideoListAdapter;
    private List<VideoBean.ItemListBean> mVideoListAdapterData;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message message) {
            if (message.what == MSG_VIDEO_CONTENT) {
                String content = (String) message.obj;
                VideoBean videoBean = new Gson().fromJson(content, VideoBean.class);
                List<VideoBean.ItemListBean> itemListBean = videoBean.getItemList();

                for (int i = 0; i < itemListBean.size(); i++) {
                    VideoBean.ItemListBean itemBean = itemListBean.get(i);
                    if (itemBean.getType().equals("video")) {
                        mVideoListAdapterData.add(itemBean);
                    }
                }

                Log.i(TAG, "handleMessage: " + mVideoListAdapterData.size());

                mVideoListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("开眼视频");

        mLV = findViewById(R.id.main_video_lv);

        initVideoListAdapter();

        loadVideoData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        JzvdStd.releaseAllVideos();
    }

    private void loadVideoData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = HttpUtils.get(url);

                Log.i(TAG, "run: " + content);

                Message message = new Message();
                message.what = MSG_VIDEO_CONTENT;
                message.obj = content;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    private void initVideoListAdapter() {
        mVideoListAdapterData = new ArrayList<>();
        mVideoListAdapter = new VideoListAdapter(this, mVideoListAdapterData);
        mLV.setAdapter(mVideoListAdapter);
    }
}
package com.example.zd.playermodel;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zd.playermodel.bean.MediaBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView emptyView;
    private RecyclerView dataList;

    private ArrayList<MediaBean> mediaBeens = new ArrayList<>();
    private MediaAdapter adapter;
    private Handler handler;

    private static class MediaHandler extends Handler {
        WeakReference<Activity> weakReference;

        MediaHandler(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = weakReference.get();
            if (activity != null && activity instanceof MainActivity) {
                if (((MainActivity) activity).mediaBeens.size() != 0) {
                    ((MainActivity) activity).emptyView.setVisibility(View.GONE);
                    ((MainActivity) activity).dataList.setVisibility(View.VISIBLE);

                    ((MainActivity) activity).adapter.notifyDataSetChanged();

                } else {
                    ((MainActivity) activity).emptyView.setVisibility(View.VISIBLE);
                    ((MainActivity) activity).dataList.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyView = (TextView) findViewById(R.id.empty_view);
        dataList = (RecyclerView) findViewById(R.id.recycler_list);
        dataList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new MediaAdapter(mediaBeens);
        dataList.setAdapter(adapter);

        handler = new MediaHandler(this);

        getData();
    }

    private void getData() {

        int selfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (selfPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x1);
        } else
            new Thread() {
                @Override
                public void run() {
                    //获取手机存储的视频

                    Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    //要查的内容关心哪些信息
                    String[] mediaInfo = {
                            MediaStore.Video.Media.DISPLAY_NAME,    //名字
                            MediaStore.Video.Media.SIZE,            //大小
                            MediaStore.Video.Media.DATA,            //绝对路径
                            MediaStore.Video.Media.DURATION,        //时长
                            MediaStore.Video.Media.DATE_ADDED,      //添加日期
                            MediaStore.Video.Media.MIME_TYPE,       //类型
                            MediaStore.Video.Media.CONTENT_TYPE
                    };
                    Cursor cursor = getContentResolver().query(uri, mediaInfo, null, null, null);

                    while (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        long size = cursor.getLong(1);
                        String dateUrl = cursor.getString(2);
                        long time = cursor.getLong(3);
                        String addTime = cursor.getString(4);
                        String type = cursor.getString(5);

                        mediaBeens.add(new MediaBean(name, size, time, dateUrl, addTime, type));
                    }
                    cursor.close();

                    for (int i = 0; i < mediaBeens.size(); i++) {
                        Log.d(TAG, "run: ----" + mediaBeens.get(i).toString());
                    }

                    if (handler != null)
                        handler.sendEmptyMessage(0);

                }
            }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0x1) {
            getData();
        }
    }
}

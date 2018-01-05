package com.example.zd.demoapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.utils.PermissionsUtils;

import java.io.File;
import java.util.Calendar;

public class VideoTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VideoTestActivity";
    public static final int VIDEO_CODE = 0x1;
    private static final int P_REQUESTCODE = 0x2;
    private static final int P_SET = 0x3;
    PermissionsUtils permissionsUtils;

    String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private TextView con;
    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);
        con = (TextView) findViewById(R.id.tv_con);
        video = (VideoView) findViewById(R.id.video);

        con.setOnClickListener(this);
        permissionsUtils = new PermissionsUtils(this);

        permissionsUtils.getPath();

    }

    @Override
    public void onClick(View v) {
        checkPermission();
//        typeOne();
    }

    private void checkPermission() {
        //6.0动态权限申请
        if (Build.VERSION.SDK_INT >= 23) {
//            String[] permissions = {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.CAMERA};
//
//            PermissionsUtils permissionsUtils = new PermissionsUtils(this);
//            boolean check = permissionsUtils.check(permissions);
//            Log.d(TAG, "checkPermission: -------------" + check);
//
//            ActivityCompat.requestPermissions(this, permissions, P_REQUESTCODE);

//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////            intent.setData(Uri.parse("package:" + getPackageName()));
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            设置此标志activity将不添加到回退栈（backStack）
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
////            如果设置，新activity不保存在最近启动的activity列表中。
//            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
////            startActivity(intent);
//
//
//            startActivityForResult(intent, P_SET);

            permissionsUtils.startActivitySetting(this, P_SET);

        } else {
            typeOne();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == P_REQUESTCODE) {
            typeOne();
        }
    }

    /**
     * 获取SD path
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }
        return null;
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒

        String date = "" + year + (month + 1) + day + hour + minute + second;
        Log.d(TAG, "date:" + date);

        return date;
    }

    private void typeOne() {
        //用系统的视频录制

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //录制的最长时间
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        //录制的画质 0~1  1原画质
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        //设置视频录制后的输出路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //限制视频文件大小 以字节为单位
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024);

        startActivityForResult(intent, VIDEO_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_CODE) {
            Uri uri = data.getData();
            if (uri != null) {

                video.setVideoURI(uri);

                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });

                video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.seekTo(0);
                        mp.start();
                    }
                });

                Log.d(TAG, "onActivityResult: -----" + uri.toString());

                String encodedPath = uri.getEncodedPath();
                Log.d(TAG, "onActivityResult: -------" + encodedPath);

                con.setText(uri.toString());
            }
        } else if (requestCode == P_SET) {
            boolean check = permissionsUtils.check(permissions);
            Log.d(TAG, "onActivityResult: --------------" + check);
        }
    }

}

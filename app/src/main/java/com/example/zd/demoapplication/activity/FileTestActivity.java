package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.handler.MH;
import com.example.zd.demoapplication.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTestActivity extends AppCompatActivity {

    private static final String TAG = "FileTestActivity";
    private int time = 5;
    private TextView timeView;
    private Handler handler = new MH() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: ----------" + time);
            timeView.setText(time + "");
        }
    };

    private MR mR = new MR();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);
        timeView = (TextView) findViewById(R.id.tv_time);
        timeView.setText(time + "");
        //读
        InputStream inputStream = getResources().openRawResource(R.raw.android2017);

        OutputStream outputStream = null;

        try {
            //获取文件大小，单位B
            int available = inputStream.available();

            Log.d(TAG, "onCreate: ----------available = " + available);
            ToastUtils.showShortT("文件大小：" + inputStream.available() + " 单位字节（B）");

            byte[] red = new byte[1024];

            while (inputStream.read(red) != -1) {
                Log.d(TAG, "onCreate: --------" + inputStream.read(red));
            }

            while (inputStream.read() != -1) {
                inputStream.read();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        new Thread(mR, "this is my thread").start();
    }

    class MR implements Runnable {
        private boolean isStop;

        @Override
        public void run() {
            Log.d(TAG, "run: --------" + Thread.currentThread().getName());

            do {
                Log.d(TAG, "run: ------" + time);
                try {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(1);
                    time--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (time > 0 && !isStop);

        }

        void stopThread() {
            this.isStop = true;
        }
    }

    @Override
    protected void onDestroy() {
        mR.stopThread();
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}

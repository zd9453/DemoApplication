package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTestActivity extends AppCompatActivity {

    private static final String TAG = "FileTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);

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

            while (true) {
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

    }
}

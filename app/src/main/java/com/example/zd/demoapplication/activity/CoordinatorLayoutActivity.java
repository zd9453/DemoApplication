package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.view.DialogTest;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    private DialogTest dialogTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        if (dialogTest == null) {
            dialogTest = DialogTest.newInstance();
        }
        dialogTest.show(getFragmentManager(), "this");
    }
}

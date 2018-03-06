package com.example.zd.mycontentprovider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    private TimePicker time;
    private DatePicker date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        time = (TimePicker) findViewById(R.id.time);

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            /**
             * @param timePicker view
             * @param i 小时
             * @param i1 分钟
             */
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Log.d(TAG, "onTimeChanged: --------- I=" + i + "  i1= " + i1);
            }
        });


        date = (DatePicker) findViewById(R.id.date);
        date.setVisibility(View.VISIBLE);

        date.updateDate(2018, 2, 2);

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        }, 2018, 2, 1).show();
    }
}

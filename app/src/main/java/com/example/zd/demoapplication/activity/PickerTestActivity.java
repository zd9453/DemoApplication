package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.datepicker.picker.DatePicker;
import com.example.zd.demoapplication.datepicker.utils.ConvertUtils;

import java.util.Calendar;

public class PickerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_test);
    }

    public void choseTime(View view) {
        Calendar calendar = Calendar.getInstance();

        final DatePicker picker = new DatePicker(this);

        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));

        Log.e("tag--------", calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        //结束时间
        picker.setRangeEnd(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));

        picker.setYearMonthDay(null);

        picker.setCancelText("取消");
        picker.setSubmitText("确定");
        picker.setResetWhileWheel(false);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                //点击确定
                showToast(year + "-" + month + "-" + day);
            }
        });

        picker.show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

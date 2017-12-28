package com.example.zd.demoapplication.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.utils.ToastUtils;

public class NotificationActivity extends AppCompatActivity {

    private static final String NOTIFY = "notification";
    /**
     * 广播
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            String notify = intent.getStringExtra(NOTIFY);
            ToastUtils.showShortT(notify);

            Intent intent1 = new Intent(context, CreateViewActivity.class);
            intent1.putExtra(NOTIFY, notify);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_notification);
            remoteViews.setImageViewResource(R.id.img_ic, R.drawable.ic_file_upload_black_24dp);
            remoteViews.setOnClickPendingIntent(R.id.img_ic,null);

            Notification.Builder builder = new Notification.Builder(context);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.ic_file_upload_black_24dp);
            builder.setContentTitle("标题");
            builder.setContentText(notify);
//            builder.setCustomContentView(remoteViews);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();

            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, notification);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        IntentFilter filter = new IntentFilter();
        filter.addAction("send");
        registerReceiver(broadcastReceiver, filter);
    }

    public void sendNotification(View view) {
        Intent intent = new Intent("send");
        intent.putExtra(NOTIFY, "this is send test");
        sendBroadcast(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

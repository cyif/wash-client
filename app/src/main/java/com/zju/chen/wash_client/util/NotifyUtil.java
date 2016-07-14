package com.zju.chen.wash_client.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.view.activity.MainActivity;
import com.zju.chen.wash_client.view.activity.status_activity;

/**
 * Created by chen on 16/7/14.
 */
public class NotifyUtil {
    public static void sendNotify(Context context, String ticker, String title, String content) {
        final int NOTIFICATION_BASE_NUMBER=110;
        Notification.Builder builder = null;
        Notification n = null;
        Intent notificationIntent = new Intent(context, status_activity.class);
        PendingIntent contentIntent = null;
        contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Resources res = context.getResources();
        builder = new Notification.Builder(context);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.washmachine_green))
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)//设置可以清除
                .setContentTitle(title)//设置下拉列表里的标题
                .setContentText(content);//设置上下文内容
        n = builder.getNotification();//获取一个Notification
        n.defaults =Notification.DEFAULT_SOUND;//设置为默认的声音
        nm.notify(NOTIFICATION_BASE_NUMBER, n);
    }
}

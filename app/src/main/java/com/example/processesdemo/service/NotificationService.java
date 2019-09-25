package com.example.processesdemo.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.RemoteViews;

import com.example.processesdemo.MainActivity;
import com.example.processesdemo.R;
import com.example.processesdemo.utils.mylog.Logger;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by 71484 on 2018/8/27.
 */
@SuppressLint("NewApi")
public class NotificationService extends Service {

    private final static String ACTION1 = "android.intent.action.PACKAGE_ADDED";

    private final static String ACTION2 = "android.intent.action.PACKAGE_REMOVED";
    boolean f = true;

    //    private TempBroadCastReceiver tempBroadCastReceiver;
    long size;
    private AppBroadCastReceiver appBroadCastReceiver;
    private String ns;
    private NotificationManager mNotificationManager;
    private Notification tempNotification;
    private CLeanBroadCastReceiver cLeanBroadCastReceiver;
    private long mAvailMem, mTotoalMem, mUsedMem;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Logger.d("%s+++++++++%s", "guoyh", "handler1111111111");
            } else if (msg.what == 2) {
                Logger.d("%s+++++++++%s", "guoyh", "handler2222222222");
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(ACTION1);
        iFilter.addAction(ACTION2);
        iFilter.addDataScheme("package");
        appBroadCastReceiver = new AppBroadCastReceiver();
        registerReceiver(appBroadCastReceiver, iFilter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        cLeanBroadCastReceiver = new CLeanBroadCastReceiver();
        registerReceiver(cLeanBroadCastReceiver, intentFilter);
        ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) this.getSystemService(ns);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void showCleanNotifi() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("1001", "channel_1",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);


            Notification.Builder builder = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setChannelId("1001");
            tempNotification = builder.setAutoCancel(true).build();
            //设置优先级
            builder.setPriority(Notification.PRIORITY_HIGH);

            //builder.setOngoing(true);

            // 将布局文件转化成View，也就是自定义Notification
            //**注意只支持Framelayout Linearlayout RelativeLayout**
            RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.notitfication_layout);
            //contentView.setTextViewText(R.id.title, "消息通知");
            contentView.setTextViewText(R.id.size_tv, Html.fromHtml("11111111111"));
            contentView.setTextViewText(R.id.tv2, "22222222222222");
            contentView.setImageViewResource(R.id.noti_iv, R.mipmap.ic_launcher);
            contentView.setTextViewText(R.id.noti_btn, "清理");
            tempNotification.contentView = contentView;
            // Notification加载点击事件
            Intent notificationIntenta = new Intent(this, MainActivity.class);
            notificationIntenta.addFlags(FLAG_ACTIVITY_NEW_TASK);
//            Intent notificationIntentb = new Intent(this, ScanAnimalActivity.class);
//            notificationIntentb.addFlags(FLAG_ACTIVITY_NEW_TASK);
//            notificationIntenta.putExtra("type", 1002);
//            notificationIntent.setAction("NOTI_CLICK");
            PendingIntent contentIntent = PendingIntent.getActivities(this, 2002, new Intent[]{notificationIntenta}, PendingIntent.FLAG_UPDATE_CURRENT);
            tempNotification.contentIntent = contentIntent;

            mNotificationManager.notify(2002, tempNotification);

        } else {

            Notification.Builder builder = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher);
            tempNotification = builder.build();
            //设置优先级
            builder.setPriority(Notification.PRIORITY_HIGH);

            //builder.setOngoing(true);

            // 将布局文件转化成View，也就是自定义Notification
            RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.notitfication_layout);
            //contentView.setTextViewText(R.id.title, "消息通知");
//            String str = getRandom() +"&#160"+;
            contentView.setTextViewText(R.id.size_tv, Html.fromHtml("11111111111"));
            contentView.setTextViewText(R.id.tv2, "22222222222222");
            contentView.setImageViewResource(R.id.noti_iv, R.mipmap.ic_launcher);
            contentView.setTextViewText(R.id.noti_btn, "清理");
            tempNotification.contentView = contentView;
            Intent notificationIntenta = new Intent(this, MainActivity.class);
//            Intent notificationIntentb = new Intent(this, ScanAnimalActivity.class);
//            notificationIntenta.putExtra("type", 1002);
            PendingIntent contentIntent = PendingIntent.getActivities(this, 2002, new Intent[]{notificationIntenta}, PendingIntent.FLAG_UPDATE_CURRENT);
//            PendingIntent contentIntent = PendingIntent.getBroadcast(this, 1001, notificationIntent, 0);
            // Notification添加跳转效果
            tempNotification.contentIntent = contentIntent;
//            Intent notificationDeleteIntent = new Intent(this, ScanAnimalActivity.class);
//            notificationDeleteIntent.setAction("NOTI_DELETE");
//            notificationDeleteIntent.putExtra("type", 1001);
            PendingIntent contentDeleteIntent = PendingIntent.getActivities(this, 2002, new Intent[]{notificationIntenta}, PendingIntent.FLAG_UPDATE_CURRENT);
//            tempNotification.deleteIntent = contentDeleteIntent;
            //tempNotification.deleteIntent = contentIntent;
            //builder.setFullScreenIntent(contentIntent,false);
            // 点击删除通知栏的通知
            //tempNotification.defaults = Notification.FLAG_AUTO_CANCEL;
//        // 滑动删除通知栏的通知
//        notification.defaults = Notification.DEFAULT_ALL;
            // 启动Notification的所有操作
            mNotificationManager.notify(1001, tempNotification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(appBroadCastReceiver);
        unregisterReceiver(cLeanBroadCastReceiver);
    }

    private class AppBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION1)){
                Logger.d("%s++++++++++++++++%s","guoyh","安装");
                showCleanNotifi();

            }
            if (action.equals(ACTION2)) {
                    Logger.d("%s+++++++++++%s","guoyuhan","卸载");
                showCleanNotifi();

            }


        }
    }

    private class CLeanBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                Logger.d("%s+++++++++++%s","guoyh","+++++++++++");
                showCleanNotifi();

            }
        }
    }


}

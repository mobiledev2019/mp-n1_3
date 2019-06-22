package com.datvl.trotot.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.datvl.trotot.MainActivity;
import com.datvl.trotot.R;

public class Common {
//    static String ip = "192.168.1.12";
//    static String ip = "192.168.1.12";
//    static String ip = "192.168.43.230";
    static String ip = "192.168.31.120";
    static String prefix = "/trotot/public";
//    static String prefix = "";

    public Common() {
    }

    public static String getUrlLogin() {
        String url = "http://" + ip + prefix +  "/login/";
        return url;
    }

    public static String getUrlRegis() {
        String url = "http://" + ip + prefix +  "/regis/";
        return url;
    }

    public static String getUrlListPosts() {
        String url = "http://" + ip + prefix +  "/list-posts";
        return url;
    }

    public static String getUrlListPostsUser() {
        String url = "http://" + ip + prefix +  "/list-posts-users/";
        return url;
    }

    public static String getUrlListPostsArea(int area,int price) {
        String url = "http://" + ip + "/trotot/public/list-posts-area/"+area+"/"+price;
        return url;
    }

    public static String getUrlListPostsSaved() {
        String url = "http://" + ip + prefix +  "/list-posts-saved/";
        return url;
    }

    public static String getUrlListSearch() {
        String url = "http://" + ip + prefix +  "/search/";
        return url;
    }

    public static String getUrlPost() {
        String url = "http://" + ip + prefix +  "/post/";
        return url;
    }

    public static String getUrlPostSaved() {
        String url = "http://" + ip + prefix +  "/post-saved/";
        return url;
    }

    public static String getUrlDelete() {
        String url = "http://" + ip + prefix +  "/delete-post-saved/";
        return url;
    }

    public static String getMessageID() {
        String url = "http://" + ip + prefix +  "/message-id/";
        return url;
    }

    public static String getListMessageUser() {
        String url = "http://" + ip + prefix +  "/list-message-username/";
        return url;
    }

    public static String deleteMessage() {
        String url = "http://" + ip + prefix +  "/list-message-delete/";
        return url;
    }

    public static void setHideProgress(View view, int id){
        ProgressBar pb = view.findViewById(id);
        pb.setVisibility(View.GONE);
    }

    public static void showToast(Context view, String message, int time){
        Toast.makeText(view, message, time).show();
    }

    public static String getListKeySearch() {
        String url = "http://" + ip + prefix +  "/key-search/";
        return url;
    }

    public static String addKeyword() {
        String url = "http://" + ip + prefix +  "/saveKeyword/";
        return url;
    }

    public static String deleteKeyword() {
        String url = "http://" + ip + prefix +  "/list-keyword-delete/";
        return url;
    }

    public static String getListArea() {
        String url = "http://" + ip + prefix +  "/list-area";
        return url;
    }

    public static String getListAreaByUser() {
        String url = "http://" + ip + prefix +  "/list-area-user/";
        return url;
    }

    public static String getListAreaWithUser() {
        String url = "http://" + ip + prefix +  "/list-area-saved/";
        return url;
    }

    public static String saveAreaByUser() {
        String url = "http://" + ip + prefix +  "/save-area-liked/";
        return url;
    }

    public static String deleteAreaByUser() {
        String url = "http://" + ip + prefix +  "/delete-area-liked/";
        return url;
    }

    public static String getUrlNewPost(String name, String nd, String price, String img, String scale, String u_id) {
        String url = "http://" + ip + prefix +  "/new-post?name=" +name + "&postContent=" +nd+ "&price="+price+"&image="+ img+"&scale="+scale+"&areaId="+u_id;
        return url;
    }

    public static String getNoticeSearch() {
        String url = "http://" + ip + prefix + "/get-notice-search/nha";
        return url;
    }



    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = "0";
        if ( (Boolean) sharedPreferences.getBoolean("is_login", false) ){
            user_id = sharedPreferences.getString("user_id", "0");
        }
        return user_id;
    }

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = "0";
        if ( (Boolean) sharedPreferences.getBoolean("is_login", false) ){
            user_id = sharedPreferences.getString("username", "0");
        }
        return user_id;
    }

    public static String getArea(Context context, String type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("area", Context.MODE_PRIVATE);
        String rs = "0";
//        if ( (Boolean) sharedPreferences.getBoolean("is_login", false) ){
            rs = sharedPreferences.getString(type, "0");
//        }
        return rs;
    }

    public void setNoification( Context context, int notificationId, String ChannelId, String title, String content) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Your_channel_id")
                .setSmallIcon(R.drawable.heart)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        String channelId = "Your_channel_id";

        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentIntent(notifyPendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager ntcManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, content, NotificationManager.IMPORTANCE_HIGH);
            ntcManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(notificationId, mBuilder.build());
    }

//    public void showNotification(Context context, String title, String body, Intent intent) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        int notificationId = 1;
//        String channelId = "mess-01";
//        String channelName = "Mess Name";
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(
//                    channelId, channelName, importance);
//            notificationManager.createNotificationChannel(mChannel);
//        }
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
//                .setSmallIcon(R.drawable.heart)
//                .setContentTitle(title)
//                .setContentText(body);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//        mBuilder.setContentIntent(resultPendingIntent);
//
//        notificationManager.notify(notificationId, mBuilder.build());
//    }
}

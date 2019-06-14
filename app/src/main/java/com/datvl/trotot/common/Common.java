package com.datvl.trotot.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.datvl.trotot.R;

public class Common {
//    static String ip = "192.168.1.12";
//    static String ip = "192.168.1.12";
//    static String ip = "192.168.43.230";
    static String ip = "192.168.1.12";

    public static String getUrlLogin() {
        String url = "http://" + ip + "/trotot/public/login/";
        return url;
    }

    public static String getUrlListPosts() {
        String url = "http://" + ip + "/trotot/public/list-posts";
        return url;
    }

    public static String getUrlListPostsUser() {
        String url = "http://" + ip + "/trotot/public/list-posts-users/";
        return url;
    }

    public static String getUrlListPostsSaved() {
        String url = "http://" + ip + "/trotot/public/list-posts-saved/";
        return url;
    }

    public static String getUrlListSearch() {
        String url = "http://" + ip + "/trotot/public/search/";
        return url;
    }

    public static String getUrlPost() {
        String url = "http://" + ip + "/trotot/public/post/";
        return url;
    }

    public static String getUrlPostSaved() {
        String url = "http://" + ip + "/trotot/public/post-saved/";
        return url;
    }

    public static String getUrlDelete() {
        String url = "http://" + ip + "/trotot/public/delete-post-saved/";
        return url;
    }

    public static String getMessageID() {
        String url = "http://" + ip + "/trotot/public/message-id/";
        return url;
    }

    public static String getListMessageUser() {
        String url = "http://" + ip + "/trotot/public/list-message-username/";
        return url;
    }

    public static String deleteMessage() {
        String url = "http://" + ip + "/trotot/public/list-message-delete/";
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
        String url = "http://" + ip + "/trotot/public/key-search/";
        return url;
    }

    public static String addKeyword() {
        String url = "http://" + ip + "/trotot/public/saveKeyword/";
        return url;
    }

    public static String deleteKeyword() {
        String url = "http://" + ip + "/trotot/public/list-keyword-delete/";
        return url;
    }

    public static String getListArea() {
        String url = "http://" + ip + "/trotot/public/list-area";
        return url;
    }

    public static String getListAreaByUser() {
        String url = "http://" + ip + "/trotot/public/list-area-user/";
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
}

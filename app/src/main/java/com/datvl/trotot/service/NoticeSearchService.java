package com.datvl.trotot.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.datvl.trotot.OnEventListener;
import com.datvl.trotot.api.GetApi;
import com.datvl.trotot.common.Common;
import com.datvl.trotot.model.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NoticeSearchService extends Service {

    private Common cm = new Common();
    DatabaseReference myRef;

    public NoticeSearchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Thread noticeSearch = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        GetApi getApi = new GetApi(cm.getNoticeSearch(), getApplication(), new OnEventListener() {
                            @Override
                            public void onSuccess(JSONArray object) {
                                try {
                                    if (object.getJSONObject(0) != null) {
                                        cm.setNoification(getApplication(),
                                                112,
                                                "noticeId",
                                                "Có tin liên quan mới đăng",
                                                "xem nào");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        noticeSearch.start();

        return START_STICKY;
    }


    public void setNotice(DatabaseReference myRef) {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message value = dataSnapshot.getValue(Message.class);
                cm.setNoification(getApplication(),
                        111,
                        "messageId",
                        value.getUser(),
                        value.getContent());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message value = dataSnapshot.getValue(Message.class);
                cm.setNoification(getApplication(),
                        111,
                        "messageId",
                        value.getUser(),
                        value.getContent());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

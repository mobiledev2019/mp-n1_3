package com.datvl.trotot.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.datvl.trotot.OnEventListener;
import com.datvl.trotot.R;
import com.datvl.trotot.adapter.ListAreaAdapter;
import com.datvl.trotot.api.GetApi;
import com.datvl.trotot.common.Common;
import com.datvl.trotot.model.Area;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class LocationLike extends AppCompatActivity {

    Spinner spn_area_like;
    Common cm = new Common();
    ImageView img_list_area, img_add_area;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_like);

        spn_area_like = findViewById(R.id.spinner_address_like);
        img_list_area = findViewById(R.id.img_list_area_saved);
        img_add_area = findViewById(R.id.img_save_area);
        recyclerView = findViewById(R.id.recycler_area_like);

        cm.setNoification( this, 123, "datvl");

        final List<Area> listArea = new ArrayList<>();

        String user_id = cm.getUserID(getApplication());

        loadListArea(cm.getListAreaByUser() + user_id);

        /**
         * Lấy danh sách Đia chỉ
         */
        GetApi getArea = new GetApi(cm.getListArea(), getApplication(), new OnEventListener() {
            @Override
            public void onSuccess(JSONArray object) {
                for (int i=0 ; i< object.length() ; i++){
                    try {
                        JSONObject jsonObject = object.getJSONObject(i);

                        listArea.add(new Area(Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("name")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(getApplication(), android.R.layout.simple_gallery_item, listArea);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spn_area_like.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        /**
         * end
         */

        img_list_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetApi getArea = new GetApi(cm.getListArea(), getApplication(), new OnEventListener() {
                    @Override
                    public void onSuccess(JSONArray object) {
                        for (int i=0 ; i< object.length() ; i++){
                            try {
                                JSONObject jsonObject = object.getJSONObject(i);

                                listArea.add(new Area(Integer.parseInt(jsonObject.getString("id")),
                                        jsonObject.getString("name")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<Area> adapter = new ArrayAdapter<Area>(getApplication(), android.R.layout.simple_gallery_item, listArea);
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spn_area_like.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });
    }


    public void loadListArea(String link) {
        Log.d(TAG, "loadListKeyword Link: " + link);
        GetApi getApi = new GetApi(link, getApplication(), new OnEventListener() {
            @Override
            public void onSuccess(JSONArray object) {
                List<Area> listAreaUser = new ArrayList<>();
                for (int i=0 ; i< object.length() ; i++){
                    try {
                        JSONObject jsonObject = object.getJSONObject(i);
                        listAreaUser.add(new Area(
                                Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("name")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                ListAreaAdapter viewAdapter = new ListAreaAdapter(listAreaUser);
                recyclerView.setAdapter(viewAdapter);
            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void setNoification( Context context, int notificationId, String ChannelId) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplication(), "Your_channel_id")
                .setSmallIcon(R.drawable.heart)
                .setContentTitle("test")
                .setContentText("test content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

        String channelId = "Your_channel_id";
//        NotificationChannel channel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationManager mNotificationManager =
//                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//            channel = new NotificationChannel(
//                    channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            mNotificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(channelId);
//            mBuilder.setSmallIcon(R.drawable.heart);
//        }

        notificationManager.notify(notificationId, mBuilder.build());
    }
}

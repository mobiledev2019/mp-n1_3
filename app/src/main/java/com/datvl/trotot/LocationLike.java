package com.datvl.trotot;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.datvl.trotot.api.GetApi;
import com.datvl.trotot.common.Common;
import com.datvl.trotot.model.Area;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationLike extends AppCompatActivity {

    Spinner spn_area_like;
    Common cm;
    ImageView img_list_area, img_add_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_like);

        spn_area_like = findViewById(R.id.spinner_address_like);
        img_list_area = findViewById(R.id.img_list_area_saved);
        img_add_area = findViewById(R.id.img_save_area);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, null)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle("test")
                .setContentText("test content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 12;
        notificationManager.notify(notificationId, mBuilder.build());

        final List<Area> listArea = new ArrayList<>();


        /**
         * Lấy danh sách Đia chỉ
         */
        GetApi getApi = new GetApi(cm.getListArea(), getApplication(), new OnEventListener() {
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
        
    }
}

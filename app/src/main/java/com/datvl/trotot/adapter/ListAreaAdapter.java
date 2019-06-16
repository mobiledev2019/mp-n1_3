package com.datvl.trotot.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datvl.trotot.OnEventListener;
import com.datvl.trotot.R;
import com.datvl.trotot.api.GetApi;
import com.datvl.trotot.common.Common;
import com.datvl.trotot.model.Area;
import com.datvl.trotot.model.Message;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ListAreaAdapter extends RecyclerView.Adapter<ListAreaAdapter.RecyclerViewHolder>{

    private List<Area> data = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String username;
    Animation animation;
    Common cm;
    ViewGroup view;

    public ListAreaAdapter(List<Area> data) {
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = parent;
        View view = inflater.inflate(R.layout.item_area_like, parent, false);
        sharedPreferences = parent.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.scale_list);
        if ((Boolean) sharedPreferences.getBoolean("is_login", false)) {
            username = sharedPreferences.getString("username", "Gest");
        }
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.txt_name_area_like.setText(data.get(position).getName());
        int checked = data.get(position).getIs_save();

        if (checked == 1){
            holder.img_heart_like.setImageResource(R.drawable.heart_active);
            holder.img_heart_like.startAnimation(animation);
        }
        else
        {
            holder.img_heart_like.setImageResource(R.drawable.heart);
            holder.img_heart_like.startAnimation(animation);
        }

        holder.img_heart_like.setOnClickListener(new View.OnClickListener() {
            int checked = data.get(position).getIs_save();
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String user_id = sharedPreferences.getString("user_id", "0");
            @Override
            public void onClick(View v) {
                if (checked == 0){
                    GetApi getApi = new GetApi(cm.saveAreaByUser() + user_id + "/" + data.get(position).getId(), view.getContext(), new OnEventListener() {
                        @Override
                        public void onSuccess(JSONArray object) {
                            cm.showToast(view.getContext(), "Đã lưu lại tin", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            cm.showToast(view.getContext(), "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT);
                        }
                    });
                    holder.img_heart_like.setImageResource(R.drawable.heart_active);
                    checked = 1;
                    holder.img_heart_like.startAnimation(animation);
                }
                else{
                    GetApi getApi = new GetApi(cm.deleteAreaByUser() + user_id + "/" + data.get(position).getId(), view.getContext(), new OnEventListener() {
                        @Override
                        public void onSuccess(JSONArray object) {
                            cm.showToast(view.getContext(), "Đã huỷ theo dõi tin này", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            cm.showToast(view.getContext(), "Lỗi! Vui lòng thử lại", Toast.LENGTH_SHORT);
                        }
                    });
                    holder.img_heart_like.setImageResource(R.drawable.heart);
                    checked =0;
                    holder.img_heart_like.startAnimation(animation);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null){
            return 0;
        }
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name_area_like,txt_time_liked;
        ImageView img_heart_like;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txt_name_area_like = (TextView) itemView.findViewById(R.id.name_area_like);
            txt_time_liked = (TextView) itemView.findViewById(R.id.time_liked);
            img_heart_like = itemView.findViewById(R.id.item_heart_area);
        }
    }
}

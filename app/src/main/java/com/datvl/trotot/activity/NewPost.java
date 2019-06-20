package com.datvl.trotot.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.datvl.trotot.OnEventListener;
import com.datvl.trotot.R;
import com.datvl.trotot.api.GetApi;
import com.datvl.trotot.common.Common;
import com.datvl.trotot.model.Area;
import com.datvl.trotot.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.android.volley.VolleyLog.TAG;


public class NewPost extends AppCompatActivity {

    private ImageView image1, image2, image3;
    private Bitmap bp1, bp2, bp3;
    private Button btnSendPost;
    private Common cm;
    private Uri filePath1, filePath2, filePath3;
    EditText edt_td, edt_nd, edt_price, edt_scale;
    Spinner spn_area;
    int area_id = 0;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        final Spinner spin_address = (Spinner) findViewById(R.id.spinner_post_address);
        final List<Area> listArea = new ArrayList<>();
        image1  = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        btnSendPost = findViewById(R.id.btn_send_post);
        edt_td = findViewById(R.id.edt_post_name);
        edt_nd = findViewById(R.id.edt_post_content);
        edt_price = findViewById(R.id.edt_post_price);
        edt_scale = findViewById(R.id.edt_post_scale);



        mStorageRef = FirebaseStorage.getInstance().getReference();

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoto(1);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoto(2);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoto(3);
            }
        });

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
                ArrayAdapter<Area> adapter=new ArrayAdapter<Area>
                        (
                                getApplication(),
                                android.R.layout.simple_spinner_item,
                                listArea
                        );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_address.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        spin_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                area_id = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String td = String.valueOf(edt_td.getText());
                String nd = String.valueOf(edt_nd.getText());
                String price = String.valueOf(edt_price.getText());
                String scale = String.valueOf(edt_scale.getText());

                GetApi getApi = new GetApi(cm.getUrlNewPost(td, nd,price, "", scale, cm.getUserID(getApplication())), getApplication(), new OnEventListener() {
                    @Override
                    public void onSuccess(JSONArray object) {

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "onFailure: " + e);
                    }
                });

                sendPostToServer();
            }
        });

    }

    protected void makePhoto(int id) {
        Intent intentCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intentCam, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                bp1 = (Bitmap) data.getExtras().get("data");
                filePath1 = data.getData();
                this.image1.setImageBitmap(rotateBitmap(bp1, 90));
                this.image1.setPadding(5, 5, 5, 5);
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                bp2 = (Bitmap) data.getExtras().get("data");
                filePath2 = data.getData();
                this.image2.setImageBitmap(rotateBitmap(bp2, 90));
                this.image2.setPadding(5, 5, 5, 5);
            }
        }

        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                bp3 = (Bitmap) data.getExtras().get("data");
                filePath3 = data.getData();
                this.image3.setImageBitmap(rotateBitmap(bp3, 90));
                this.image3.setPadding(5, 5, 5, 5);
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    protected void sendPostToServer() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bp1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte [] byte_arr = stream.toByteArray();


        if(byte_arr != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            Log.d("band", "sendPostToServer: ");
            StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

            riversRef.putBytes(byte_arr).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.hide();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.hide();
                }
            });
        }
    }
}
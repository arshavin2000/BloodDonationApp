package tn.esprit.blooddonationapp.post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import tn.esprit.blooddonationapp.R;

public class NewPost extends AppCompatActivity {
    ProgressDialog pd;

    Button add,gallery;
    EditText text_post;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_post);

        text_post = findViewById(R.id.text_post);
        imageView = findViewById(R.id.img_post);
        add = findViewById(R.id.btn_add);
        gallery = findViewById(R.id.btn_galerry);

        String path =getIntent().getStringExtra("path");
        final File imgFile = new  File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Log.i("NEW POST","ADD POST ACTION");
                AndroidNetworking.initialize(getApplicationContext());
                AndroidNetworking.upload("http://10.10.10.6:3000/api/uploadfile")
                        .addMultipartFile("uploadfile",imgFile)
                        .addMultipartParameter("key","value")
                        .setTag("uploadFile")
                        .setPriority(Priority.LOW)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @Override
                            public void onProgress(long bytesUploaded, long totalBytes) {

                            }
                        })
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    Log.i("REP",response.getString("msg").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                                Log.e("ERR",anError.getCause()+"");
                            }
                        });

            }
        });

    }


}

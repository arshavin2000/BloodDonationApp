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
import tn.esprit.blooddonationapp.Service.PostService;
import tn.esprit.blooddonationapp.model.Comment;
import tn.esprit.blooddonationapp.model.Post;
import tn.esprit.blooddonationapp.model.User;
import tn.esprit.blooddonationapp.util.UserUtils;
import tn.esprit.blooddonationapp.util.Util;

public class NewPost extends AppCompatActivity {
    ProgressDialog pd;

    Button add,gallery;
    EditText text_post;
    TextView txt_username;
    ImageView imageView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_post);

        txt_username = findViewById(R.id.txt_username);
        text_post = findViewById(R.id.text_post);
        imageView = findViewById(R.id.img_post);
        add = findViewById(R.id.btn_add);
        gallery = findViewById(R.id.btn_galerry);

         user = new User(
                UserUtils.getUser(getApplicationContext()).getId(),
                UserUtils.getUser(getApplicationContext()).getFirstName(),
                UserUtils.getUser(getApplicationContext()).getLastName()

        );

        txt_username.setText(user.getFirstname()+" "+user.getLastname());
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
                //Post post = new Post(1,);
Log.i("NEW POST","ADD POST ACTION");
                AndroidNetworking.initialize(getApplicationContext());
                AndroidNetworking.upload("http://192.168.1.11:3000/api/uploadfile")
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
                                    String fileName = response.getString("file").toString();
                                    Log.i("REP",fileName);

                                    Post post = new Post(1,
                                            fileName,text_post.getText().toString(),
                                            new User("","","haffez"),
                                            "",2,2
                                            );
                                  //  Comment comment = new Comment("","","");
                                   // post.getComments().add(comment);

                                    PostService ps = new PostService();

                                    ps.addPost(post);
                                    onBackPressed();


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

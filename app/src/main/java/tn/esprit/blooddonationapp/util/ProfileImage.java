package tn.esprit.blooddonationapp.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import tn.esprit.blooddonationapp.Service.PostService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.model.Post;
import tn.esprit.blooddonationapp.model.User;


public class ProfileImage    {


    public static void getFacebookOrGoogleProfilePicture(String url, Context context , ImageView imageView)  {

        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    public static void uploadNumberPhoneImage(final Context context, final File imgFile, final ImageView imageView)
    {
        AndroidNetworking.initialize(context);
        AndroidNetworking.upload("http://196.203.252.226:9090/api/uploadfile")
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
                            String fileName = response.getString("file");
                            Log.i("REP",fileName);

                            Donor donor =  UserUtils.getUser(context);
                            donor.setUrlImage("http://196.203.252.226:9090/static/images/"+fileName);
                            Toast.makeText(context,"Profile picture added successfully",Toast.LENGTH_SHORT).show();
                            getFacebookOrGoogleProfilePicture("http://196.203.252.226:9090/static/images/"+fileName,context,imageView);


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






}

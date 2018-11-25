package tn.esprit.blooddonationapp.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;



public class ProfileImage    {


    public static void getFacebookOrGoogleProfilePicture(String url, Context context , ImageView imageView)  {

        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }






}

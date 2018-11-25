package tn.esprit.blooddonationapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;

public class ProfileImage {

    public static Bitmap getFacebookProfilePicture(String url) throws IOException {
        URL imageURL = new URL(url);
        return BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

    }
}

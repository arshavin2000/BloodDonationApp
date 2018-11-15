package tn.esprit.blooddonationapp.pictures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileImage {






    public static Bitmap getFacebookProfilePicture(String url) throws IOException {
        URL imageURL = new URL(url);
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }
}

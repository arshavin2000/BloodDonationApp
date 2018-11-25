package tn.esprit.blooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import tn.esprit.blooddonationapp.util.ProfileImage;

public class UserPostsActivity extends AppCompatActivity {

    private ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activities);

        test = findViewById(R.id.image);

        ProfileImage.getFacebookProfilePicture("http://graph.facebook.com/2725426277471383/picture?type=large", getApplicationContext(), test);




    }
}

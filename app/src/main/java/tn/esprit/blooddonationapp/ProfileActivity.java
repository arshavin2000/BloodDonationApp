package tn.esprit.blooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.DataHolder;
import tn.esprit.blooddonationapp.util.ProfileImage;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profile;
    private TextView email , number , blood , username;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = findViewById(R.id.profile_image);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        blood = findViewById(R.id.blood);
        username =findViewById(R.id.username);
        save = findViewById(R.id.save);

       final  Donor donor = DataHolder.getInstance().getDonor();



        username.setText(donor.getFirstName() + " " + donor.getLastName());
        email.setText(donor.getEmail());
        number.setText(donor.getNumber());
        blood.setText(donor.getBloodGroup());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Your code to run in GUI thread here
                ProfileImage.getFacebookOrGoogleProfilePicture(donor.getUrlImage(), getApplicationContext(), profile);
            }//public void run() {
        });

        save.setEnabled(false);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }
}

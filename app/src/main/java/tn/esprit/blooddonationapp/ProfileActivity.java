package tn.esprit.blooddonationapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.data.DBHandler;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.DataHolder;
import tn.esprit.blooddonationapp.util.ProfileImage;
import tn.esprit.blooddonationapp.util.Validator;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profile;
    private EditText email , number , blood ,place;
    private Button save;
    private TextView username;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        activity =this;

        profile = findViewById(R.id.profile_image);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        blood = findViewById(R.id.blood);
        username =findViewById(R.id.username);
        save = findViewById(R.id.save);
        place = findViewById(R.id.location);

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

        number.addTextChangedListener(new TextWatcher() {
                                          @Override
                                          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                          }

                                          @Override
                                          public void onTextChanged(CharSequence s, int start, int before, int count) {

                                              if (s.toString().trim().length() == 0) {
                                                  save.setEnabled(false);
                                                  number.setError("The phone number field is required");

                                              } else if (!Validator.isValidPhoneNumber(s.toString())) {
                                                  save.setEnabled(false);
                                                  number.setError("A valid phone number is required");
                                              } else {
                                                  save.setEnabled(true);
                                              }
                                          }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    save.setEnabled(false);
                    email.setError("The email field is required");

                }else if(!Validator.isValidEmail(s))
                {
                    save.setEnabled(false);
                    email.setError("A valid email is required");
                }
                else {
                    save.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        blood.setFocusable(false);
        place.setFocusable(false);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DBHandler dbHandler = new DBHandler(getApplicationContext());
                Donor newDonor = donor ;
                newDonor.setEmail(email.getText().toString().trim());
                newDonor.setNumber(number.getText().toString());
                dbHandler.updateDonor(newDonor);
                DonorService donorService =new DonorService(getApplicationContext(),activity);
                donorService.updateUser(donor);



            }
        });

    }
}

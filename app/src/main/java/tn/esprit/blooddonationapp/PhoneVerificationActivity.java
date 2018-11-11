package tn.esprit.blooddonationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;


public class PhoneVerificationActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private EditText mCountry, mNumber , mSMS;
    private Button mSave , mVerify;
    private String codeSent;
    private static final String TAG = "MOBILE-PHONE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        firebaseAuth = FirebaseAuth.getInstance();
        mCountry = findViewById(R.id.country_number);
        mNumber = findViewById(R.id.phone_number);
        mSave  = findViewById(R.id.save);
        mVerify = findViewById(R.id.validate);
        mSMS = findViewById(R.id.sms);
        mSMS.setVisibility(View.VISIBLE);
        mVerify.setVisibility(View.VISIBLE);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });







    }


}

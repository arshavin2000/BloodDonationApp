package tn.esprit.blooddonationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PhoneVerificationActivity extends AppCompatActivity {


    private EditText mCountry, mNumber , mSMS;
    private Button mSave , mVerify;
    private String codeSent;
    private static final String TAG = "MOBILE-PHONE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

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

               /* Intent in = new Intent(PhoneVerificationActivity.this, VerifyMobile.class);
                in.putExtra("app_id", "YOUR_APP_ID_HERE");
                in.putExtra("access_token","YOUR_ACCESS_TOKEN_HERE");
                in.putExtra("mobile", "mobile number for verification");

                startActivityForResult(in,VerifyMobile.REQUEST_CODE);*/

            }
        });


        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });







    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
// TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
       /* if (arg0 == VerifyMobile.REQUEST_CODE) {
            String message = arg2.getStringExtra("message");
            int result=arg2.getIntExtra("result", 0);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }*/

    }


}

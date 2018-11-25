package tn.esprit.blooddonationapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.Validator;

public class BecomeDonorActivity extends AppCompatActivity {

    private EditText mEmail , mName , mNumber;
    private RadioButton mMale, mFemale;
    private Donor donor;
    private Button mSave;
    private RadioGroup mBloodGroup , mGenderGroup;
    private RadioButton mBlood , mGender;
    private ProgressBar progressBar;
    private Activity activity;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_donor);

        Log.e("BECOME_DONÒR", "onCreate: " );

        activity=this;

        mEmail = findViewById(R.id.email);
        mName = findViewById(R.id.name);
        mMale = findViewById(R.id.male);
        mFemale = findViewById(R.id.female);
        mSave = findViewById(R.id.save);
        mNumber = findViewById(R.id.number);
        mBloodGroup = findViewById(R.id.blood_group);
        mGenderGroup = findViewById(R.id.gender_group);
        progressBar = findViewById(R.id.progress_bar);





        donor = (Donor) getIntent().getSerializableExtra("donor");


        if(donor != null  ) {
            if (donor.getFirstName() != null && donor.getLastName() != null)
                mName.setText(donor.getFirstName() + " " + donor.getLastName());
            if(donor.getEmail()!= null)
            mEmail.setText(donor.getEmail());
            if(donor.getNumber()!= null)
                mNumber.setText(donor.getNumber());
            if (donor.getGender() != null) {
                if (donor.getGender().equals("male"))
                    mMale.setChecked(true);

                else if (donor.getGender().equals("female"))
                    mFemale.setChecked(true);
            }
            if(donor.getGender() == null){
                donor.setGender("male");

            }
            donor.setBloodGroup("A+");

        }



            mNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.toString().trim().length()==0){
                        mSave.setEnabled(false);
                        mNumber.setError("The phone number field is required");

                    } else if(!Validator.isValidPhoneNumber(s.toString()))
                    {
                        mSave.setEnabled(false);
                        mNumber.setError("A valid phone number is required");
                    }
                    else {
                        mSave.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        if(mEmail.getText().toString().trim().equals("")   )

        {
            mSave.setEnabled(false);
            mEmail.setError("The email field is required");

        }
        else if(mNumber.getText().toString().trim().equals(""))
        {
            mSave.setEnabled(false);
            mNumber.setError("The phone number field is required");

        }
        else if(mName.getText().toString().trim().equals(""))
        {
            mSave.setEnabled(false);
            mName.setError("The name field is required");


        }




            mEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.toString().trim().length()==0){
                        mSave.setEnabled(false);
                        mEmail.setError("The email field is required");

                    }else if(!Validator.isValidEmail(s))
                    {
                        mSave.setEnabled(false);
                        mEmail.setError("A valid email is required");
                    }
                    else {
                        mSave.setEnabled(true);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });



            mName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.toString().trim().length()==0){
                        mSave.setEnabled(false);
                        mName.setError("The name field is required");

                    }
                    else {
                        mSave.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });





        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mGender = findViewById(i);
                switch(mGender.getId())
                {
                    case R.id.female:
                        if(mGender.isChecked())
                        {
                            donor.setGender("female");
                        }
                        break;
                    case R.id.male:
                        if(mGender.isChecked())
                        {
                            donor.setGender("male");
                        }
                        break;
                    default:{
                        donor.setGender("other");
                    }
                }
            }
        });

        mBloodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mBlood = findViewById(i);
                switch(mBlood.getId())
                {
                    case R.id.a_plus:{
                        if(mBlood.isChecked()){
                            donor.setBloodGroup("A+");
                        }
                    }
                    break;
                    case R.id.a_moins:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("A-");
                    }
                    break;
                    case R.id.b_plus:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("B+");
                    }
                    break;
                    case R.id.b_moins:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("B-");
                    }
                    break;
                    case R.id.o_moins:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("O-");
                    }
                    break;
                    case R.id.o_plus:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("O+");
                    }
                    break;
                    case R.id.ab_moins:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("AB-");
                    }
                    break;
                    case R.id.ab_plus:{
                        if(mBlood.isChecked())
                            donor.setBloodGroup("AB+");
                    }
                    break;
                    default:{
                        donor.setBloodGroup("A+");
                    }
                }
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donor.setEmail(mEmail.getText().toString());

                String name = mName.getText().toString();
                String [] tab = name.split(" ");
                String firstname;
                String lastname ;

                if(tab.length <3){
                    firstname =  tab[0];
                    lastname = tab[1];

                }else if (tab.length>3){
                    firstname =  tab[0]+tab[1];
                    lastname = tab[2];
                }
                else{
                    firstname =  tab[0];
                    lastname = "";

                }


                donor.setFirstName(firstname);
                donor.setLastName(lastname);
                donor.setNumber("+216"+mNumber.getText().toString().trim());


                DonorService donorService = new DonorService(getApplicationContext(),activity);
                donorService.isPhoneNumberExist("+216"+mNumber.getText().toString().trim(),mEmail.getText().toString().trim(),donor,progressBar);




            }
        });



    }





}

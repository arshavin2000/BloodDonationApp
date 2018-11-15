package tn.esprit.blooddonationapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.model.Donor;

public class BecomeDonorActivity extends AppCompatActivity {

    private EditText mEmail , mName , mNumber;
    private RadioButton mMale, mFemale;
    private Donor donor;
    private Button mSave;
    private RadioGroup mBloodGroup , mGenderGroup;
    private RadioButton mBlood , mGender;
    private ProgressBar progressBar;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_donor);

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
        }



        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBloodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        mBlood = findViewById(i);
                        switch(mBlood.getId())
                        {
                            case R.id.a_plus:{
                                if(mBlood.isChecked())
                                    donor.setBloodGroup("A+");
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
                                donor.setBloodGroup("");
                            }
                        }
                    }
                });
                Log.d("EMAIL", "onCreate: "+ mEmail.getText().toString());
                donor.setEmail(mEmail.getText().toString());
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
                String name = mName.getText().toString();
                String [] tab = name.split(" ");
                String firstname;
                String lastname ;
                if(tab.length <3)
                {
                    firstname =  tab[0];
                    lastname = tab[1];
                }
                else
                {
                    firstname =  tab[0]+tab[1];
                    lastname = tab[2];
                }
                donor.setFirstName(firstname);
                donor.setLastName(lastname);
                donor.setNumber(mNumber.getText().toString().trim());

                DonorService donorService = new DonorService(BecomeDonorActivity.this);
                donorService.addUser(donor,progressBar);
                finish();




            }
        });



    }
}

package tn.esprit.blooddonationapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import tn.esprit.blooddonationapp.model.Donor;

public class BecomeDonorActivity extends AppCompatActivity {

    private EditText mEmail , mName;
    private RadioGroup mGender;
    private RadioButton mMale, mFemale;
    private Donor donor;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_donor);

        mEmail = findViewById(R.id.email);
        mName = findViewById(R.id.name);
        mGender = findViewById(R.id.gender_group);
        mMale = findViewById(R.id.male);
        mFemale = findViewById(R.id.female);



        donor = (Donor) getIntent().getSerializableExtra("donor");
        mName.setText(donor.getFirstName() + " " +donor.getLastName() );
        mEmail.setText(donor.getEmail());



        if(donor.getGender().equals("male"))
            mMale.setChecked(true);

        else if(donor.getGender().equals("female"))
            mFemale.setChecked(true);


    }
}

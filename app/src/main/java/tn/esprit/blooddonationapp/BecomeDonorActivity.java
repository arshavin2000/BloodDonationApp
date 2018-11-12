package tn.esprit.blooddonationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import tn.esprit.blooddonationapp.model.Donor;

public class BecomeDonorActivity extends AppCompatActivity {

    private EditText mEmail , mName;
    private RadioButton mMale, mFemale;
    private Donor donor;
    private Button mSave;


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



        donor = (Donor) getIntent().getSerializableExtra("donor");
        mName.setText(donor.getFirstName() + " " +donor.getLastName() );
        mEmail.setText(donor.getEmail());



        if(donor.getGender() != null) {
            if (donor.getGender().equals("male"))
                mMale.setChecked(true);

            else if (donor.getGender().equals("female"))
                mFemale.setChecked(true);
        }


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BecomeDonorActivity.this,WelcomeActitvity.class);
                startActivity(intent);
                finish();

            }
        });



    }
}

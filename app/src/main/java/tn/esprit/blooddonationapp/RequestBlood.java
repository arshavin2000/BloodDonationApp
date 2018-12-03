package tn.esprit.blooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;

import tn.esprit.blooddonationapp.Service.RequestService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.model.Request;
import tn.esprit.blooddonationapp.util.UserUtils;

public class RequestBlood extends AppCompatActivity {

    private Button save;
    private EditText name, place;
    private RadioGroup mBloodGroup;
    private RadioButton mBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);


        final Donor donor = UserUtils.getUser(getApplicationContext());
        final Request request = new Request();



        save = findViewById(R.id.save);
        name = findViewById(R.id.name);
        place = findViewById(R.id.place);
        mBloodGroup = findViewById(R.id.blood_group);


        name.setText(donor.getFirstName() + " "+ donor.getLastName());


        mBloodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mBlood = findViewById(i);
                switch(mBlood.getId())
                {
                    case R.id.a_plus:{
                        if(mBlood.isChecked()){
                            request.setBloodgroup("A+");
                        }
                    }
                    break;
                    case R.id.a_moins:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("A-");
                    }
                    break;
                    case R.id.b_plus:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("B+");
                    }
                    break;
                    case R.id.b_moins:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("B-");
                    }
                    break;
                    case R.id.o_moins:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("O-");
                    }
                    break;
                    case R.id.o_plus:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("O+");
                    }
                    break;
                    case R.id.ab_moins:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("AB-");
                    }
                    break;
                    case R.id.ab_plus:{
                        if(mBlood.isChecked())
                            request.setBloodgroup("AB+");
                    }
                    break;
                    default:{
                        request.setBloodgroup("A+");
                    }
                }
            }
        });





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request.setPlace(place.getText().toString());
                request.setDonor(donor);
                RequestService requestService = new RequestService();
                try {
                    requestService.addRequest(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });



    }
}

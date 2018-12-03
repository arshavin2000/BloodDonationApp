package tn.esprit.blooddonationapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;

import tn.esprit.blooddonationapp.Service.RequestService;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.model.Request;
import tn.esprit.blooddonationapp.util.UserUtils;


public class RequestBloodFragment extends Fragment {


    private Button save;
    private EditText name, place;
    private RadioGroup mBloodGroup;
    private RadioButton mBlood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       final  View view = inflater.inflate(R.layout.fragment_request_blood, container, false);


        final Donor donor = UserUtils.getUser(getContext());
        final Request request = new Request();



        save = view.findViewById(R.id.save);
        name = view.findViewById(R.id.name);
        place = view.findViewById(R.id.place);
        mBloodGroup = view.findViewById(R.id.blood_group);


        name.setText(donor.getFirstName() + " "+ donor.getLastName());


        mBloodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mBlood = view.findViewById(i);
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


        Log.d("DONORRRRR", "onCreate: " +donor);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request.setPlace(place.getText().toString());
                request.setDonor(donor);
                RequestService requestService = new RequestService(getContext(),getActivity());
                try {
                    requestService.addRequest(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });




        return view ;
    }
}

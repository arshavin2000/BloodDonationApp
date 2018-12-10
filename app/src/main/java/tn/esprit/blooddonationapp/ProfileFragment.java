package tn.esprit.blooddonationapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.data.DBHandler;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.ProfileImage;
import tn.esprit.blooddonationapp.util.UserUtils;
import tn.esprit.blooddonationapp.util.Validator;

public class ProfileFragment extends Fragment {

    private ImageView profile;
    private EditText email , number ;
    private Button save;
    private TextView username , request, blood, answer;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        activity =getActivity();

        profile = view.findViewById(R.id.profile_image);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        blood = view.findViewById(R.id.blood);
        username =view.findViewById(R.id.username);
        save = view.findViewById(R.id.save);
        request = view.findViewById(R.id.req);
        answer = view.findViewById(R.id.answer);


        final Donor donor = UserUtils.getUser(getContext());




        username.setText(donor.getFirstName() + " " + donor.getLastName());
        email.setText(donor.getEmail());
        number.setText(donor.getNumber());
        blood.setText(donor.getBloodGroup());
        request.setText(donor.getRequest()+"");
        answer.setText(donor.getAnswer()+"");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Your code to run in GUI thread here
                ProfileImage.getFacebookOrGoogleProfilePicture(donor.getUrlImage(), getContext(), profile);
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

                } else if (Validator.isValidPhoneNumber(s.toString())) {
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

                }else if(Validator.isValidEmail(s))
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




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DBHandler dbHandler = new DBHandler(getContext());
                Donor newDonor = donor ;
                newDonor.setEmail(email.getText().toString().trim());
                newDonor.setNumber(number.getText().toString());
                dbHandler.updateDonor(newDonor);
                DonorService donorService =new DonorService(getContext(),activity);
                donorService.updateUser(donor);



            }
        });



        return view ;
    }


}

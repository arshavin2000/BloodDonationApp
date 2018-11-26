package tn.esprit.blooddonationapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Double longitude,latitude;
    Geocoder geocoder;
    List<Address> addresses;



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

         geocoder = new Geocoder(this, Locale.getDefault());





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

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude =location.getLatitude();
                longitude = location.getLongitude();

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    place.setText(address+" "+" "+ country );

                } catch (IOException e) {
                    e.printStackTrace();
                }



                Log.d("Location", "onLocationChanged: " + location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }
        }
    }
}

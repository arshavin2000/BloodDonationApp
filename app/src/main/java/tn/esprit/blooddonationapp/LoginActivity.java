package tn.esprit.blooddonationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import tn.esprit.blooddonationapp.model.Donor;

public class LoginActivity extends AppCompatActivity {

    private  Button mGoogle , mPhoneNumber , mFacebook;
    private CallbackManager callbackManager;
    private String LOG_TAG ="FB";
    private Animation animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);

        mFacebook = findViewById(R.id.facebook);
        mGoogle = findViewById(R.id.google);
        mPhoneNumber = findViewById(R.id.phone);


        //final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //final boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        mGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

            }
        });

        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                Intent intent  = new Intent(LoginActivity.this,PhoneVerificationActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("user_photos", "email",
                                "user_birthday", "public_profile", "user_gender"));



            // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess (LoginResult loginResult){
                    // App code

                   // final Donor donor=new Donor();
                 getFbInfo();

                }

                @Override
                public void onCancel () {
                    // App code

                    Toast.makeText(getApplicationContext(), "Facebook Cancel", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onError (FacebookException exception){
                    // App code
                    Toast.makeText(getApplicationContext(), "Facebook" + exception.toString(), Toast.LENGTH_LONG).show();


                }
            });

        }

    });


}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbInfo() {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        try {
                            Log.d(LOG_TAG, "fb json object: " + object);
                            Log.d(LOG_TAG, "fb graph response: " + response);

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String gender = object.getString("gender");
                           // String birthday = object.getString("birthday");
                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";


                            String email=null;
                            if (object.has("email")) {
                                email = object.getString("email");
                            }


                            Donor d = new Donor();
                            d.setId(Long.parseLong(id));
                            d.setFirstName(first_name);
                            d.setLastName(last_name);
                            d.setGender(gender);
                            d.setUrlImage(image_url);
                            if(email!=null)
                                d.setEmail(email);
                            else
                                d.setEmail("");


                            Intent intent = new Intent(LoginActivity.this, BecomeDonorActivity.class);
                            intent.putExtra("donor",d);
                            startActivity(intent);
                            finish();









                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }



}
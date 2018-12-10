package tn.esprit.blooddonationapp.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;



import java.util.Arrays;

import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.util.UserUtils;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager  callbackManager;
    private Animation animation;
    public  GoogleSignInClient mGoogleSignInClient;
    //private static final int RC_FACEBOOK = 1;
    private static final int RC_GOOGLE = 2;
    public static int RC_PHONE = 3;
    private GoogleActivity googleActivity;
    private PhoneNumberActivity phoneNumberActivity;
    private Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

         Button mPhoneNumber, mFacebook , mGoogle;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        activity =this;
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        mFacebook = findViewById(R.id.facebook);
        mGoogle = findViewById(R.id.google);
        mPhoneNumber = findViewById(R.id.phone);
        googleActivity = new GoogleActivity(getApplicationContext(),activity);




        mGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                if(UserUtils.isNetworkAvailable(getApplicationContext()))
                {
                    UserUtils.alertDialog(activity,"Please check  your connection !");
                }
                else {
                    // Configure sign-in to request the user's ID, email address, and basic
                    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    // Build a GoogleSignInClient with the options specified by gso.
                    mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                    signIn();

                }


            }
        });

        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                if(UserUtils.isNetworkAvailable(getApplicationContext()))
                {
                    UserUtils.alertDialog(activity,"Please check  your connection !");
                }
                else {
                    phoneNumberActivity = new PhoneNumberActivity(getApplicationContext(), activity);
                    phoneLogin(view);
                }

            }
        });

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);


                if (UserUtils.isNetworkAvailable(getApplicationContext())) {
                    UserUtils.alertDialog(activity, "Please check your connection !");
                } else {

                    LoginManager.getInstance().logInWithReadPermissions(
                            LoginActivity.this,
                            Arrays.asList("user_photos", "email",
                                    "user_birthday", "public_profile", "user_gender"));


                    // Callback registration
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code

                            // final DonorService donorService=new DonorService();
                            FacebookActivity facebookActivity = new FacebookActivity(getApplicationContext(), activity);
                            facebookActivity.getFbInfo();


                        }

                        @Override
                        public void onCancel() {
                            // App code

                            Toast.makeText(getApplicationContext(), "Facebook Cancel", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                            Toast.makeText(getApplicationContext(), "Facebook" + exception.toString(), Toast.LENGTH_LONG).show();


                        }
                    });

                }
            }

        });




    }







   @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
       // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleActivity.updateUI(account);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

            callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleActivity.handleSignInResult(task);
        }
        else if (requestCode == RC_PHONE) {
            phoneNumberActivity.getCurrentAccount();

        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE);
    }


    public void phoneLogin(@Nullable View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.CODE
        UIManager uiManager = new SkinManager(

                SkinManager.Skin.TRANSLUCENT,
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? getResources().getColor(R.color.colorPrimary,null):getResources().getColor(R.color.colorPrimary)),
                R.drawable.bg,
                SkinManager.Tint.WHITE,
                0.55
        );
        /*If you want default country code*/
        configurationBuilder.setDefaultCountryCode("TN");
        configurationBuilder.setUIManager(uiManager);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
        startActivityForResult(intent, RC_PHONE);
    }

    public void call(){

        System.out.println("hbdhbhdghvdhvhdvhd");
    }



}
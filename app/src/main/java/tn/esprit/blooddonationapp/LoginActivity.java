package tn.esprit.blooddonationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import tn.esprit.blooddonationapp.model.Donor;

public class LoginActivity extends AppCompatActivity {

    private Button mPhoneNumber, mFacebook;
    private Button mGoogle;
    private CallbackManager callbackManager;
    private String LOG_TAG = "FB";
    private Animation animation;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_FACEBOOK = 1;
    private static final int RC_GOOGLE = 2;
    public static int RC_PHONE = 3;
    private static final String TAG = "MOBILE-PHONE";
    private static final String TAG_GOOGLE = "GOOGLE_LOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        mFacebook = findViewById(R.id.facebook);
        mGoogle = findViewById(R.id.google);
        mPhoneNumber = findViewById(R.id.phone);


        //final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //final boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        // Set the dimensions of the sign-in button.

       // mGoogle.setSize(SignInButton.SIZE_STANDARD);
        mGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                signIn();


            }
        });

        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                com.facebook.accountkit.AccessToken accessToken = AccountKit.getCurrentAccessToken();

                if (accessToken != null) {
                    //Handle Returning User
                } else {
                    //Handle new or logged out user
                }
                phoneLogin(view);

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
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        // final Donor donor=new Donor();
                        getFbInfo();

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

        });


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


                            String email = null;
                            if (object.has("email")) {
                                email = object.getString("email");
                            }


                            Donor d = new Donor();
                            d.setId(id);
                            d.setFirstName(first_name);
                            d.setLastName(last_name);
                            d.setGender(gender);
                            d.setUrlImage(image_url);
                            if (email != null)
                                d.setEmail(email);
                            else
                                d.setEmail("");


                            Intent intent = new Intent(LoginActivity.this, BecomeDonorActivity.class);
                            intent.putExtra("donor", d);
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


    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            startActivity(new Intent(LoginActivity.this, BecomeDonorActivity.class));
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE);
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
            handleSignInResult(task);
        }
        else if (requestCode == RC_PHONE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Log.d(TAG, "onActivityResult: "+loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success1:" + loginResult.getAccessToken().getAccountId();
                    // Success! Start your next activity...

                } else {
                    toastMessage = String.format(
                            "Success3:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            // Get Account Kit ID
                            String accountKitId = account.getId();

                            // Get phone number
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            String phoneNumberString="";
                            if (phoneNumber != null) {
                                phoneNumberString = phoneNumber.toString();
                                Log.d(TAG, "onSuccess2: " + phoneNumberString);
                            }

                            // Get email
                            String email = account.getEmail();

                            Donor donor = new Donor();
                            donor.setEmail(email);
                            donor.setId(accountKitId);
                            donor.setNumber(phoneNumberString);
                            Intent intent = new Intent(LoginActivity.this, BecomeDonorActivity.class);
                            intent.putExtra("donor", donor);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(final AccountKitError error) {
                            // Handle Error
                            Log.d(TAG, "onError: ");
                        }
                    });
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.


            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
            Donor donor = new Donor();
            donor.setEmail(account.getEmail());
            donor.setId(account.getId());
            donor.setFirstName(account.getGivenName());
            donor.setLastName(account.getFamilyName());
            donor.setUrlImage(account.getPhotoUrl().toString());
            Log.d("aasba", donor.toString());
            Intent intent = new Intent(LoginActivity.this, BecomeDonorActivity.class);
            intent.putExtra("donor", donor);
            startActivity(intent);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG_GOOGLE, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void signUpPhone()
    {
        AccountKit.logOut();
    }


    public void phoneLogin(View view) {
        final Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, RC_PHONE);
    }

}
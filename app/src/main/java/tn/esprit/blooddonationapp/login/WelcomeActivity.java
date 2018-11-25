package tn.esprit.blooddonationapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccountKit;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;

import tn.esprit.blooddonationapp.BecomeDonorActivity;
import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.RequestBlood;
import tn.esprit.blooddonationapp.UserPostsActivity;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.DataHolder;
import tn.esprit.blooddonationapp.util.ProfileImage;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GoogleApiClient mGoogleApiClient;
    private TextView email, username;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_actitvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        email = header.findViewById(R.id.email);
        username = header.findViewById(R.id.username);
        image = header.findViewById(R.id.image);

        if (DataHolder.getInstance().getDonor() != null) {
            final Donor donor = DataHolder.getInstance().getDonor();
            email.setText(donor.getEmail());
            username.setText(donor.getFirstName() + " " + donor.getLastName());

            Thread  thread = new Thread() {
                public void run() {
                    try {
                        image.setImageBitmap(ProfileImage.getFacebookProfilePicture(donor.getUrlImage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome_actitvity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(WelcomeActivity.this, BecomeDonorActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_blood) {
            Intent intent = new Intent(WelcomeActivity.this, RequestBlood.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(WelcomeActivity.this, UserPostsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

            logOut();
            SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
            editor.putBoolean("login", false);
            editor.apply();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logOut() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (isLoggedIn()) {
            LoginManager.getInstance().logOut();
            Intent i = new Intent(getApplicationContext(), SplashScreenActivity.class);
            startActivity(i);
        }
        if (acct != null) {

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // ...
                            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), SplashScreenActivity.class);
                            startActivity(i);
                        }
                    });

        } else {
            AccountKit.logOut();
            com.facebook.accountkit.AccessToken accessToken = AccountKit.getCurrentAccessToken();
            if (accessToken == null)
                Log.e("PHONE_LOGOUT", "Still Logged in...");
            Intent intent = new Intent(WelcomeActivity.this, SplashScreenActivity.class);
            startActivity(intent);
        }
        finish();


    }


    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


}

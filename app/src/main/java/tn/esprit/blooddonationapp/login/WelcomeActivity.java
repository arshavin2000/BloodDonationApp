package tn.esprit.blooddonationapp.login;


import android.content.Intent;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.TaskStackBuilder;

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

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.util.ArrayList;

import tn.esprit.blooddonationapp.BloodNeedsFragment;
import tn.esprit.blooddonationapp.MapFragment;
import tn.esprit.blooddonationapp.ProfileFragment;
import tn.esprit.blooddonationapp.RequestFragment;
import tn.esprit.blooddonationapp.data.DBHandler;
import tn.esprit.blooddonationapp.post.NewPost;
import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.post.FileListeAdapter;
import tn.esprit.blooddonationapp.post.ListPostFragment;
import tn.esprit.blooddonationapp.util.DataHolder;

import tn.esprit.blooddonationapp.util.ProfileImage;
import tn.esprit.blooddonationapp.util.UserUtils;
import tn.esprit.blooddonationapp.util.Util;

import com.pusher.pushnotifications.PushNotifications;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GoogleApiClient mGoogleApiClient;
    private Donor donor;
    private TextView email, username;
    private ImageView image;


    public static final String REGISTRATION_PROCESS = "registration";
    public static final String MESSAGE_RECEIVED = "message_received";


    // IMAGE GALLERY
    private final static int FILE_REQUEST_CODE = 1;
    private FileListeAdapter fileListAdapter;
    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_actitvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);


        PushNotifications.start(getApplicationContext(), "ab4bd9cb-feeb-44e0-bc22-aca7f7bcce78");
        PushNotifications.subscribe(UserUtils.getUser(getApplicationContext()).getBloodGroup());

        email = header.findViewById(R.id.email);
        username = header.findViewById(R.id.username);
        image = header.findViewById(R.id.image);
        donor = UserUtils.getUser(getApplicationContext());

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container, new ListPostFragment()).commit();

        Log.e("GETUSER", "onCreate: " + donor.toString());
        if (donor != null) {
            email.setText(donor.getEmail());
            username.setText(donor.getFirstName() + " " + donor.getLastName());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Your code to run in GUI thread here
                    ProfileImage.getFacebookOrGoogleProfilePicture(donor.getUrlImage(), getApplicationContext(), image);
                }//public void run() {
            });

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Intent intent = new Intent(WelcomeActivity.this, FilePickerActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
// Adds the back stack
                stackBuilder.addParentStack(FilePickerActivity.class);
                stackBuilder.addNextIntent(intent);

                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setSelectedMediaFiles(mediaFiles)
                        .enableImageCapture(true)
                        .setShowVideos(false)
                        .setSkipZeroSizeFiles(true)
                        .setMaxSelection(1)
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            DataHolder.getInstance().setDonor(donor);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_blood) {
            DataHolder.getInstance().setDonor(donor);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container,new MapFragment()).commit();

        } else if (id == R.id.nav_home) {

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container, new ListPostFragment()).commit();

        } else if (id == R.id.nav_manage) {


            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.container, new BloodNeedsFragment()).commit();


        } else if (id == R.id.nav_share) {


            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.container,new RequestFragment()).commit();



        } else if (id == R.id.nav_logout) {

            logOut();
            SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
            editor.putBoolean("login", false);
            editor.apply();

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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
                        public void onResult(@NonNull Status status) {
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
        DBHandler dbHandler = new DBHandler( getApplicationContext());
        dbHandler.deleteDonor();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

            MediaFile f = files.get(0);
            f.getName();
            String path = f.getPath();
            Intent intent = new Intent(WelcomeActivity.this, NewPost.class);


            intent.putExtra("path", path);
            startActivity(intent);

            mediaFiles.clear();

        }
    }


}

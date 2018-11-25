package tn.esprit.blooddonationapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import tn.esprit.blooddonationapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progress_bar);
        new loginVerification().execute(false);

    }


    private class loginVerification extends AsyncTask<Boolean, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return prefs.getBoolean("login", false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setVisibility(View.GONE);
            if(!aBoolean){
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                }
            else
            {
                Intent i = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }


}


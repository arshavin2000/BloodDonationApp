package tn.esprit.blooddonationapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewPost extends AppCompatActivity {
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_post);

        //final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);
        final EditText text_post = findViewById(R.id.text_post);
//        alert.setView(alertLayout);

        //      alert.setCancelable(false);
        //final AlertDialog dialog = alert.create();
        //dialog.show();
        Button cancel = findViewById(R.id.cancel);
        Button share = findViewById(R.id.share_action);
        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostData(text_post).execute();
            }
        });


    }

    private class PostData extends AsyncTask<String, String, String> {

        private EditText text_post;
        private String method;

        public PostData(EditText text_post) {
            this.text_post = text_post;
            this.method = method;
        }

        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try {

                URL url = new URL("http://192.168.43.71:3000/api/posts");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);


                JSONObject jsonParam = new JSONObject();
                jsonParam.put("postImage", "/newImg");
                jsonParam.put("postText", text_post.getText().toString());
                Log.i("JSON", jsonParam.toString());


                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }




        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            Toast.makeText(getApplicationContext(), "Your post created!",
                    Toast.LENGTH_LONG).show();

        }
    }
}

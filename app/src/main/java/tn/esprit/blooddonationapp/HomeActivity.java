package tn.esprit.blooddonationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;


import tn.esprit.blooddonationapp.model.Post;

public class HomeActivity extends AppCompatActivity  {
    ArrayList<Post> postList;
    PostAdapter adapter ;
    Button add_post;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    ProgressDialog pd;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView listView = (ListView) findViewById(R.id.list);


            new JsonTask(listView).execute("http://192.168.1.17:3000/api/posts");

        //Add PostService


    }




    private class JsonTask extends AsyncTask<String, String, String> {

        private ListView ls;
        public JsonTask(ListView listView) {
            ls=listView;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(HomeActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            URL url;
            StringBuffer response = new StringBuffer();
            try {
                url = new URL("http://192.168.43.71:3000/api/posts");
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("invalid url");
            }

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(false);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                // handle the response
                int status = conn.getResponseCode();
                if (status != 200) {
                    throw new IOException("PostService failed with error code " + status);
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                        System.out.println("--------------------------------------------------------------------------");
                        System.out.println(inputLine);
                    }
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }

                //Here is your json in string format
                return  response.toString();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            try {
                JSONObject obj = new JSONObject(result);

                postList = new ArrayList<Post>();

                JSONArray arr= obj.getJSONArray("data");
                for (int i=0; i < arr.length(); i++) {
                    JSONObject jsonObject= arr.getJSONObject(i);
                    String numberLikes = jsonObject.get("numberLikes").toString();
                    String numberComments = jsonObject.get("NumberComments").toString();
                    String timePost = jsonObject.get("timePost").toString();
                    String postImage = jsonObject.get("postImage").toString();
                    String postText = jsonObject.get("postText").toString();
                    postList.add(new Post(postImage, postText, "Haffez Med", "2 Hour",
                            Integer.parseInt(numberLikes), Integer.parseInt(numberComments)));

                }
                adapter = new PostAdapter(postList, getApplicationContext());
                ls.setAdapter(adapter);
                add_post = findViewById(R.id.add_post);
                add_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, NewPost.class);
                        startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
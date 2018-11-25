package tn.esprit.blooddonationapp.Service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import tn.esprit.blooddonationapp.BecomeDonorActivity;
import tn.esprit.blooddonationapp.login.WelcomeActivity;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.DataHolder;

public class DonorService {

    private static final String HttpUrl = "http://10.0.2.2:3000/api/donors";
    private Activity activity;
    private Context context;





    public DonorService(Context context,Activity activity){

        this.context=context;
        this.activity = activity;


    }


    public void addUser(final Donor donor, final ProgressBar progressBar)
    {



        progressBar.setVisibility(View.VISIBLE);
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(activity,WelcomeActivity.class);
                        activity.startActivity(intent);
                        activity.finish();

                        // Showing response message coming from server.
                        Log.d("VOLLEY", "onResponse: "+ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressBar.setVisibility(View.GONE);

                        // Showing error message if something goes wrong.
                        Log.d("VOLLEY", "onErrorResponse: "+ volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<>();
                // Adding All values to Params.
                params.put("id", donor.getId());
                params.put("firstname", donor.getFirstName());
                params.put("lastname", donor.getLastName());
                params.put("email", donor.getEmail());
                params.put("number", donor.getNumber());
                params.put("bloodgroup", donor.getBloodGroup());
               // params.put("gender", donor.getGender());





                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

    public void isUserExist(final String id,final  Donor d)
    {


        String HttpVerifyUrl =HttpUrl+"/"+id;




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,HttpVerifyUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                // Log.d("RESPONSE", "onResponse: "+ response.getJSONObject("data").getString("id"));


                try {
                    if (response.getString("data").equals("false")) {
                        Intent intent = new Intent(context, BecomeDonorActivity.class);
                        DataHolder.getInstance().setDonor(d);
                        context.startActivity(intent);
                        activity.finish();

                    }
                    else
                    {
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        DataHolder.getInstance().setDonor(d);
                        context.startActivity(intent);
                        activity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG", error.toString());
                DataHolder.getInstance().setExist(false);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);


    }

    public void isGoogleUserExist(final String id)
    {


        String HttpVerifyUrl =HttpUrl+"/"+id;




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,HttpVerifyUrl,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                // Log.d("RESPONSE", "onResponse: "+ response.getJSONObject("data").getString("id"));


                try {
                    if (response.getString("data").equals("false")) {
                        Intent intent = new Intent(context, BecomeDonorActivity.class);
                        context.startActivity(intent);
                        activity.finish();

                    }
                    else
                    {
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        context.startActivity(intent);
                        activity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG", error.toString());
                DataHolder.getInstance().setExist(false);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);


    }




}
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import tn.esprit.blooddonationapp.BecomeDonorActivity;
import tn.esprit.blooddonationapp.login.WelcomeActivity;
import tn.esprit.blooddonationapp.model.Donor;

public class DonorService {

    private static final String HttpUrl = "http://10.0.2.2:3000/api/donors";
    private Activity activity;


    public DonorService(Activity activity){
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
                params.put("firstname", "Achref");
                params.put("lastname", "Meghirbi");




                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }



}

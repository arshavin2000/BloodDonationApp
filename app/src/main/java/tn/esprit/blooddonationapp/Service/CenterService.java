package tn.esprit.blooddonationapp.Service;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.blooddonationapp.model.Center;

public class CenterService {

    private Context context;
    private Activity activity;


    public CenterService(Context context , Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public List<Center> getCenters() {

         final List<Center> centers = new ArrayList<>();


        final String URL ="http://10.0.2.2:3000/api/centers";

        StringRequest stringrequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jarray  = jsonObject.getJSONArray("data");

                            Log.d("Center Response", "onResponse: "+response);
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject object = jarray.getJSONObject(i);


                                Center center =new Center();
                                center.setName(object.getString("name"));
                                center.setAddress(object.getString("address"));
                                center.setTel(object.getString("tel"));
                                center.setFax(object.getString("fax"));
                                center.setSite(object.getString("site"));

                                centers.add(center);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        requestQueue.add(stringrequest);

        return centers;
    }
}

package tn.esprit.blooddonationapp.Service;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tn.esprit.blooddonationapp.CallBack;
import tn.esprit.blooddonationapp.login.WelcomeActivity;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.UserUtils;


public class RequestService {


    private static final String HttpUrl = "http://196.203.252.226:9090/api/";


    private Context context;
    private Activity activity;

    public RequestService(Context context,Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public void addRequest(final tn.esprit.blooddonationapp.model.Request request) throws JSONException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(request);
        final JSONObject r = new JSONObject(jsonString);

        AndroidNetworking.post(HttpUrl+"request")
                .addJSONObjectBody(r)
                .setTag("addRequest")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DonorService donorService = new DonorService(context,activity);
                       /* Log.d("REQUEST NUMBER", "onResponse: "+ request.getDonor().getRequest());
                        donor.setRequest(donor.getRequest()+1);
                        DBHandler dbHandler = new DBHandler(context);
                        dbHandler.updateDonor(donor);*/
                        Donor donor = UserUtils.getUser(context);

                        donorService.updateUser(donor);
                        Intent intent = new Intent(context,WelcomeActivity.class);
                        context.startActivity(intent);
                        activity.finish();
                        Log.i("add request Service REP",response.toString());

                        String bloodGroup = getBloodGroup(request.getBloodgroup());


                        AndroidNetworking.post(HttpUrl+"notification/"+bloodGroup+"/"+donor.getFirstName()+"/"+donor.getLastName()+"/"+request.getBloodgroup())
                                .addBodyParameter(donor)
                                .setTag("addRequest")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i("Notification REP",response.toString());


                                    }
                                    @Override
                                    public void onError(ANError error) {
                                        Log.e("Notification Error ",error.getMessage());

                                    }
                                });

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("add request service ","Error");

                    }
                });

    }
    public String getBloodGroup(String bloodGroup){

        switch (bloodGroup){
            case "B+" :
                return "BP";
            case "B-" :
                return "BM";
            case "A+" :
                return "AP";
            case "A-" :
                return "AM";
            case "O+" :
                return "OP";
            case "O-" :
                return "OM";
            case "AB+" :
                return "ABP";
            case "AB-" :
                return "ABM";
            default:
                return bloodGroup;
        }


    }



        public void  getRequests(final CallBack callback) {



            StringRequest stringrequest = new StringRequest(Request.Method.GET, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jarray  = jsonObject.getJSONArray("data");
                                int  k =0;

                                Log.d("Center Response", "onResponse: "+response);
                                for (int i = 0; i < jarray.length(); i++) {



                                    k++;

                                }
                                callback.onSuccess(k);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            callback.onFail(error.toString());
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(activity);

            requestQueue.add(stringrequest);




    }

}

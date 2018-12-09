package tn.esprit.blooddonationapp.Service;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.pusher.pushnotifications.PushNotifications;

import org.json.JSONException;
import org.json.JSONObject;
import tn.esprit.blooddonationapp.login.WelcomeActivity;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.UserUtils;


public class RequestService {


    private static final String HttpUrl = "http://192.168.1.12:3000/api/";


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

Log.i("JSON R", r.toString());

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
                                        Log.e("Notification Error ",error.getErrorBody());

                                    }
                                });

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("add request service ",error.getCause().toString());

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

            default:
                return bloodGroup;


        }


    }

}

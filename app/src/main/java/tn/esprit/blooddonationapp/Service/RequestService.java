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
import org.json.JSONException;
import org.json.JSONObject;

import tn.esprit.blooddonationapp.data.DBHandler;
import tn.esprit.blooddonationapp.login.WelcomeActivity;
import tn.esprit.blooddonationapp.model.Donor;
import tn.esprit.blooddonationapp.util.UserUtils;


public class RequestService {


    private Context context;
    private Activity activity;

    public RequestService(Context context,Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    private static final String HttpUrl = "http://10.0.2.2:3000/api/request";


    public void addRequest(final tn.esprit.blooddonationapp.model.Request request) throws JSONException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(request);
        JSONObject r = new JSONObject(jsonString);



        AndroidNetworking.post(HttpUrl)
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
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("add request service ",error.getCause().toString());

                    }
                });

    }

}

package tn.esprit.blooddonationapp.Service;



import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestService {

    private static final String HttpUrl = "http://10.0.2.2:3000/api/request";


    public void addRequest(tn.esprit.blooddonationapp.model.Request request) throws JSONException {
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
                        Log.i("add request Service REP",response.toString());
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.e("add request service ",error.getCause().toString());

                    }
                });

    }

}

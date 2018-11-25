package tn.esprit.blooddonationapp.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONException;
import org.json.JSONObject;
import tn.esprit.blooddonationapp.Service.DonorService;
import tn.esprit.blooddonationapp.model.Donor;

class FacebookActivity {

    private Context context;
    private Activity activity;
    private String LOG_TAG = "FB";





      FacebookActivity(Context context,Activity activity){

          this.activity =activity;
          this.context = context;

    }


     void getFbInfo() {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        try {
                            Log.d(LOG_TAG, "fb json object: " + object);
                            Log.d(LOG_TAG, "fb graph response: " + response);

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String gender = object.getString("gender");
                            // String birthday = object.getString("birthday");
                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";


                            String email = null;
                            if (object.has("email")) {
                                email = object.getString("email");
                            }


                            Donor d = new Donor();
                            d.setId(id);
                            d.setFirstName(first_name);
                            d.setLastName(last_name);
                            d.setGender(gender);
                            d.setUrlImage(image_url);
                            if (email != null)
                                d.setEmail(email);
                            else
                                d.setEmail("");

                            DonorService donorService = new DonorService(context,activity);
                            donorService.isUserExist(id,d);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }
}

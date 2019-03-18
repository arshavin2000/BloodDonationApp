package tn.esprit.blooddonationapp.Service;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tn.esprit.blooddonationapp.model.Post;

public class PostService {


    public void addPost(Post post) throws JSONException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(post);
            JSONObject request = new JSONObject(jsonString);



        AndroidNetworking.post("http://41.226.11.252:11808/api/posts")
                .addJSONObjectBody(request)
                .setTag("addPost")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("AddPost Service REP",response.toString());
                        }
                    @Override
                    public void onError(ANError error) {
                        Log.e("AddPost PostService",error.getCause().toString());

                    }
                });

    }
}

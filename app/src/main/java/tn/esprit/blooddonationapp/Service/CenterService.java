package tn.esprit.blooddonationapp.Service;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.blooddonationapp.CallBack;
import tn.esprit.blooddonationapp.CenterCallback;
import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Center;
import tn.esprit.blooddonationapp.util.DataHolder;

public class CenterService {

    private Context context;
    private Activity activity;
    private List<Center> centers = new ArrayList<>();
    ItemizedOverlayWithFocus<OverlayItem> mOverlay;



    public CenterService(Context context , Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public void getCenters(final MapboxMap mapboxMap , CenterCallback callBack) {

  final List<Center> centers = new ArrayList<>();


        final String URL ="http://196.203.252.226:9090/api/centers";

        StringRequest stringrequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jarray  = jsonObject.getJSONArray("data");
                            List<Marker> alMarkerGT = new ArrayList<Marker>();


                            Log.d("Center Response", "onResponse: "+response);
                            for (int i = 1; i < jarray.length(); i++) {
                                final JSONObject object = jarray.getJSONObject(i);


                                Center center =new Center();
                                center.setName(object.getString("name"));
                                center.setAddress(object.getString("address"));
                                center.setTel(object.getString("tel"));
                                center.setFax(object.getString("fax"));
                                center.setSite(object.getString("site"));
                                centers.add(center);




                            }
                            callBack.onSuccess(centers);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        DataHolder.getInstance().setCenters(centers);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        callBack.onFail(error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        requestQueue.add(stringrequest);


    }



}

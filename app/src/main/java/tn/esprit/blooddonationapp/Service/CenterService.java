package tn.esprit.blooddonationapp.Service;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.blooddonationapp.R;
import tn.esprit.blooddonationapp.model.Center;
import tn.esprit.blooddonationapp.util.DataHolder;

public class CenterService {

    private Context context;
    private Activity activity;
    private List<Center> centers = new ArrayList<>();



    public CenterService(Context context , Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }

    public void getCenters(final MapView map) {

  final List<Marker> markers = new ArrayList<>();


        final String URL ="http://196.203.252.226:9090/api/centers";

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



                                    Marker marker = new Marker(map);
                                    GeoPoint ok = getLocationFromAddress(context,object.getString("address") );
                                    assert ok != null;
                                    marker.setPosition(ok);
                                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                    marker.setIcon(context.getResources().getDrawable(R.drawable.ic_place_black_24dp));
                                    marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                                            return true;
                                        }
                                    });

                                    markers.add(marker);




                            }
                            map.getOverlays().clear();
                            for(int i = 0 ; i<markers.size();i++)
                            {
                                map.getOverlays().add(markers.get(i));

                            }
                            map.invalidate();




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
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        requestQueue.add(stringrequest);


    }

    public static GeoPoint getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GeoPoint geoPoint=null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return geoPoint;
    }
}

package tn.esprit.blooddonationapp;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import tn.esprit.blooddonationapp.Service.CenterService;
import tn.esprit.blooddonationapp.model.Center;


public class MapV2Fragment extends Fragment implements OnMapReadyCallback , MapboxMap.OnMarkerClickListener ,PermissionsListener{


    private MapView mapView;
    // variables for adding location layer
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Location originLocation;
    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private DirectionsRoute currentRoute;
    private NavigationMapRoute navigationMapRoute;
    private Button button;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Mapbox.getInstance(Objects.requireNonNull(getContext()), getString(R.string.access_token));
        View view = inflater.inflate(R.layout.fragment_mapv2, container, false);

        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        destinationMarker = null;
        button = view.findViewById(R.id.start);
        button.setEnabled(false);
        button.setOnClickListener(v -> {
            NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                    .directionsRoute(currentRoute)
                    .shouldSimulateRoute(true)
                    .build();
            // Call this method with Context from within an Activity
            NavigationLauncher.startNavigation(Objects.requireNonNull(getActivity()), options);
        });



        return  view;

    }





    @Override
    public void onMapReady(MapboxMap map) {


        mapboxMap = map;
        CenterService centerService = new CenterService(getContext(), getActivity());
        centerService.getCenters(mapboxMap, new CenterCallback() {
            @Override
            public void onSuccess(List<Center> centers) {
                for(Center center : centers) {
                    StringBuilder info = new StringBuilder();
                    info.append("TEL: ").append(center.getTel()).append("\n").append("FAX: ").append(center.getFax()).append("\n").append("SITE: ").append(center.getSite());
                    GeoPoint ok = dosmt(center.getAddress());
                    mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(ok.getLatitude(), ok.getLongitude()))
                            .title(center.getName())
                            .snippet(info.toString())

                    );
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
        enableLocationComponent();
        originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());

        mapboxMap.setOnMarkerClickListener(this);




    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    private void getRoute(Point origin, Point destination) {
        assert Mapbox.getAccessToken() != null;
        NavigationRoute.builder(getContext())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Timber.d("Response code: %s", response.code());

                        if (response.body() == null) {
                            Timber.e("No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Timber.e("No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.updateRouteVisibilityTo(false);
                            navigationMapRoute.updateRouteArrowVisibilityTo(false);

                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable throwable) {
                        Timber.e("Error: %s", throwable.getMessage());
                    }
                });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {



        if(destinationMarker != null)
            destinationMarker.hideInfoWindow();
            destinationMarker = marker;
            marker.showInfoWindow(mapboxMap, mapView);
            Point destinationPosition = Point.fromLngLat(marker.getPosition().getLongitude(), marker.getPosition().getLatitude());
            // variables for calculating and drawing a route
            Point originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
            getRoute(originPosition, destinationPosition);
            button.setEnabled(true);
            button.setBackgroundResource(R.color.mapboxBlue);


        return true;
    }



    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(Objects.requireNonNull(getContext()))) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(getContext());
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            originLocation = locationComponent.getLastKnownLocation();

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), "User location permission explanation", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent();
        } else {
            Toast.makeText(getContext(), "User location permission not granted", Toast.LENGTH_LONG).show();
            Objects.requireNonNull(getActivity()).finish();
        }
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
    @WorkerThread
    public GeoPoint dosmt(String a)
    {
        GeoPoint ok = getLocationFromAddress(getContext(),a );
        return  ok;

    }

}

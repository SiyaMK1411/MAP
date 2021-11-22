package com.miniproject.projectc2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miniproject.projectc2s.databinding.ActivityMapsBinding;

public class maps extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener  {

private GoogleMap mMap;
private ActivityMapsBinding binding;
private GoogleApiClient client;
    private Location lastLocation;
private Marker currentLocationMarker;
public static final int REQUEST_LOCATION_CODE = 99;
        int PROXIMITY_RADIUS = 10000;
        double latitude, longitude;
      Button btnMapGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btnMapGoogle=(Button) findViewById(R.id.btnMap);



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.map);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                NavigationActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.law:

                        startActivity(new Intent(getApplicationContext(),
                                laws.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.map:

                        return true;


                    case R.id.video:
                        startActivity(new Intent(getApplicationContext(),
                                video.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.helpline:
                        startActivity(new Intent(getApplicationContext(),
                                helpline.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        btnMapGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMapGoogle.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.maps,new GoogleMapsActivity()).commit();
            }
        });
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
        case REQUEST_LOCATION_CODE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //permission granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        if (client == null) {
        buildGoogleApiClient();
        }
        mMap.setMyLocationEnabled(true);
        }
        } else //permission denied
        {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
        }
        return;
        }
        }

/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */
@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        }

        }

protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

        client.connect();
        }

@Override
public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;
        if (currentLocationMarker != null) {
        currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here!");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));

        if (client != null) {
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
        }


public void onClick(View v) {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        switch (v.getId()) {
        case R.id.B_search: {
        EditText tf_location = (EditText) findViewById(R.id.TF_location);
        String location = tf_location.getText().toString();
        List<Address> addressList = null;
        MarkerOptions mo = new MarkerOptions();
        if (!location.equals("")) {
        Geocoder geocoder = new Geocoder(this);
        try {
        addressList = geocoder.getFromLocationName(location, 5);
        } catch (IOException e) {
        e.printStackTrace();
        }

        for (int i = 0; i < addressList.size(); i++) {
        Address myAddress = addressList.get(i);
        LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
        mo.position(latLng);
        mo.title("Your search result.");
        mMap.addMarker(mo);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        }
        }
        break;

        case R.id.B_hospital:
        mMap.clear();
        String hospital = "hospital";
        String url = getUrl(latitude, longitude, hospital);
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(maps.this, "Showing nearby Hospitals", Toast.LENGTH_LONG).show();
        break;

        case R.id.B_police:
        mMap.clear();
        String police = "police";
        url = getUrl(latitude, longitude, police);
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(maps.this, "Showing nearby Police Stations", Toast.LENGTH_LONG).show();
        break;
        }

        }

private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyBl-fFTdztmBLML5eM9hKFgxq16g1WlEQA");

        return googlePlaceUrl.toString();
        }

@Override
public void onConnected(@Nullable Bundle bundle) {
    LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest,this);
        }
        }

public boolean checkLocationPermission()
        {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
        {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
        }
        else
        {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
        }
        return false;
        }
        else
        return true;
        }


@Override
public void onConnectionSuspended(int i) {

        }

@Override
public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
        }
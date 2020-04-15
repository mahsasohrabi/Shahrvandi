package com.shahrvandi.shahrvandi.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shahrvandi.shahrvandi.DialogLocation;
import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.R;

public class LocationActivity extends MyActivity implements OnMapReadyCallback {

    LocationManager locationManager;
    FloatingActionButton floatingActionButton;
    Button btnConfirm;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        btnConfirm=findViewById(R.id.btn_confirm);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView imgLocation=findViewById(R.id.img_location);
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMapReady(mMap);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkEnabled()) {
                    setUpLocation();
                } else {
                    DialogLocation dialogLocation = new DialogLocation(LocationActivity.this);
                    dialogLocation.show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setUpLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3, (LocationListener) this);
    }
    Boolean checkEnabled(){
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Boolean check=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (check) return true;
        else return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        VisibleRegion visibleRegion = mMap.getProjection()
                .getVisibleRegion();
        Point x = mMap.getProjection().toScreenLocation(
                visibleRegion.farRight);
        Point y = mMap.getProjection().toScreenLocation(
                visibleRegion.nearLeft);
        Point centerPoint = new Point(x.x / 2, y.y / 2);
        LatLng centerFromPoint = mMap.getProjection().fromScreenLocation(
                centerPoint);

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(28.919979, 50.830009);
//        mMap.addMarker(new MarkerOptions().position(location).title("مکان شما!"));
//                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.group_10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        Log.d("center map",centerFromPoint+"map");

        moveCamera(location);
        mMap.setMaxZoomPreference(14);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f));
    }

    private void moveCamera(LatLng location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15));
    }
    public interface IHassan{
        void recive();
    }
}

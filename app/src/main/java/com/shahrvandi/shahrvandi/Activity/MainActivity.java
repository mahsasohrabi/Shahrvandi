package com.shahrvandi.shahrvandi.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mvc.imagepicker.ImagePicker;
import com.shahrvandi.shahrvandi.DialogLocation;
import com.shahrvandi.shahrvandi.Fragment.AddSubjectFragment;
import com.shahrvandi.shahrvandi.Fragment.SubjectFragment;
import com.shahrvandi.shahrvandi.IAddAttachment;
import com.shahrvandi.shahrvandi.ISubjectSelected;
import com.shahrvandi.shahrvandi.Model.Subject;
import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.OnActivityResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends MyActivity implements OnMapReadyCallback, LocationListener,
        View.OnClickListener, ISubjectSelected, IAddAttachment {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    private FloatingActionButton floatingActionButton;
    private Button btnConfirm;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ImageView imgToolbar;
    private LinearLayout navUserInfo, navReport, navMessagesOfSystem, navAbout, navInfo, navExit;
    private ProgressBar navPbExit;
    private TextView tvToolbar;
    private FusedLocationProviderClient client;
    private GoogleMap mMap;
    private AlertDialog alertDialog;
    public static final int SECOND_PIC_REQ = 1313;
    public static final int GALLERY_ONLY_REQ = 1212;
    public static final String CACHED_IMG_KEY = "img_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) Log.d("currentLocation", location.toString());
            }
        });
        init();
        click();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//      actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDrawer();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.commit();
        tvToolbar.setText("کجا هستید؟");
        imgToolbar.setImageResource(R.drawable.icon_search_filled);
    }

    private void click() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "location", Toast.LENGTH_SHORT).show();
                if (checkEnabled()) {
                    setUpLocation();
                    Toast.makeText(MainActivity.this, "location", Toast.LENGTH_SHORT).show();
                    DialogLocation dialogLocation = new DialogLocation(MainActivity.this);
                    dialogLocation.show();
                } else {
                    DialogLocation dialogLocation = new DialogLocation(MainActivity.this);
                    dialogLocation.show();
                }
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMapReady(mMap);

                SubjectFragment subjectFragment = new SubjectFragment();
                subjectFragment.subjectSelected = MainActivity.this;
                subjectFragment.show(getSupportFragmentManager(), subjectFragment.getTag());

            }
        });
    }

    private void initDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    private void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView imgLocation = findViewById(R.id.img_location);
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMapReady(mMap);
            }
        });
        floatingActionButton = findViewById(R.id.floatingActionButton);
        imgToolbar = findViewById(R.id.img_toolbar);
        btnConfirm = findViewById(R.id.btn_confirm);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        tvToolbar = findViewById(R.id.tv_toolbar);
        navUserInfo = findViewById(R.id.nav_user);
        navReport = findViewById(R.id.nav_report);
        navMessagesOfSystem = findViewById(R.id.nav_messages_of_system);
        navAbout = findViewById(R.id.nav_about);
        navInfo = findViewById(R.id.nav_info);
        navExit = findViewById(R.id.nav_exit);

        navUserInfo.setOnClickListener(this);
        navReport.setOnClickListener(this);
        navMessagesOfSystem.setOnClickListener(this);
        navAbout.setOnClickListener(this);
        navExit.setOnClickListener(this);
        navPbExit = findViewById(R.id.pbExit);
        navPbExit.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        try {
            if (checkEnabled()) {
                setUpLocation();
            }
        } catch (Exception e) {
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_user:
                Intent intentUser = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intentUser);
                tvToolbar.setText("اطلاعات کاربری");
                imgToolbar.setImageResource(R.drawable.ic_arrow_up);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_report:
                Intent intentReport = new Intent(MainActivity.this, FollowReportActivity.class);
                startActivity(intentReport);
                tvToolbar.setText("پیگیری گزارش");
                imgToolbar.setImageResource(R.drawable.ic_arrow_up);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_messages_of_system:
                Intent intentMessage = new Intent(MainActivity.this, SystemMessageActivity.class);
                startActivity(intentMessage);
                tvToolbar.setText("پیام های سامانه 137");
                imgToolbar.setImageResource(R.drawable.ic_arrow_up);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_about:
                tvToolbar.setText("درباره 137");
                imgToolbar.setImageResource(R.drawable.ic_arrow_up);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_exit:
                finish();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setUpLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3, (LocationListener) this);
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
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
        VisibleRegion visibleRegion = mMap.getProjection()
                .getVisibleRegion();
        Point x = mMap.getProjection().toScreenLocation(
                visibleRegion.farRight);
        Point y = mMap.getProjection().toScreenLocation(
                visibleRegion.nearLeft);
        Point centerPoint = new Point(x.x / 2, y.y / 2);
        LatLng centerFromPoint = mMap.getProjection().fromScreenLocation(centerPoint);
        LatLng location = new LatLng(35.840019, 50.939091);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        Log.d("center map",centerFromPoint+"map");
        moveCamera(location);
        mMap.setMaxZoomPreference(18f);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f));
    }

    private void moveCamera(LatLng location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AddSubjectFragment.arriveRequestCode(requestCode);
    }


    @Override
    public void onSubjectSelected() {
        AddSubjectFragment addSubjectFragment=new AddSubjectFragment();
        addSubjectFragment.addAttachment = MainActivity.this;
        addSubjectFragment.show(getSupportFragmentManager(),addSubjectFragment.getTag());
    }

    @Override
    public void onAddAttachment() {

//        ImagePicker.pickImage(AddSubjectFragment.this, "Select your image:");
//        showAlertDialogButtonClicked();
    }
    public void showAlertDialogButtonClicked() {

        Rect displayRectangle = new Rect();
        Window window = getWindow();
        LinearLayout uploadImage,uploadVideo,uploadSound;
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.activity_upload, null);
        uploadImage=customLayout.findViewById(R.id.cell_upload_image);
        uploadVideo=customLayout.findViewById(R.id.cell_upload_video);
        uploadSound=customLayout.findViewById(R.id.cell_upload_sound);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFolder();
            }
        });
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        uploadSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        customLayout.setMinimumWidth((int)(displayRectangle.width() * 1f));
        customLayout.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(customLayout);
        alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        // add a button
        builder.setPositiveButton("انتخاب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                LinearLayout editText = customLayout.findViewById(R.id.cell_upload_video);
            }
        });
    }

    public void openFolder(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,1);
        alertDialog.dismiss();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        Log.d("currentLocation","currentLocation : getLatitude"+location.getLatitude()+"getLongitude :"+location.getLongitude() );
        moveCamera(latLng);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("currentLocation","currentLocation " );

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

package com.example.ontheway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class mac_register extends AppCompatActivity {

    //Location code
    EditText latitudeTextView, longitTextView;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    //...........

    private EditText name;
    private EditText content;
    private EditText city;
    private EditText district;
    private EditText skill;
    private EditText email;
    private Button btn;
    private ImageView map;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_register);

        //location lcode
        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        //...........................
        map=findViewById(R.id.map);
        name=findViewById(R.id.name);
        content=findViewById(R.id.contect);
        city=findViewById(R.id.city);
        district=findViewById(R.id.district);
        skill=findViewById(R.id.skill);
        email=findViewById(R.id.email);
        btn=findViewById(R.id.btn_regi);

//        Intent intent = getIntent();
//        String up1 = intent.getStringExtra("upd");
//        btn.setText(up1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = name.getText().toString();
                String content1 = content.getText().toString();
                String city1 = city.getText().toString();
                String district1 = district.getText().toString();
                String skill1 = skill.getText().toString();
                String email1 = email.getText().toString();
                String lat = latitudeTextView.getText().toString();
                String lot = longitTextView.getText().toString();

                if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(content1) || TextUtils.isEmpty(city1) || TextUtils.isEmpty(district1) ||
                        TextUtils.isEmpty(skill1) || TextUtils.isEmpty(email1)|| TextUtils.isEmpty(lat)|| TextUtils.isEmpty(lot))
                {
                    Toast.makeText(mac_register.this,"Empty field Are not Allowed",Toast.LENGTH_SHORT).show();


//                    if (content1 == null ||content1.length() > 10 && content1.length() < 10)
//                    {
//                        Toast.makeText(mac_register.this,"Enter correct Contect",Toast.LENGTH_SHORT).show();
//                    }
                }
                else if (content.getText().toString().length()<10 || content1.length()>10)
                {

                    // am_checked=0;
                    Toast.makeText(mac_register.this,"Enter correct Contect",Toast.LENGTH_SHORT).show();
                }
//
                else{
                    progressDialog =new ProgressDialog(mac_register.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Register...");
                    progressDialog.show();
                    ragitermac();
                    //regiet mac autho
                }


            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private void ragitermac() {
        HashMap m =new HashMap();
        m.put("name",name.getText().toString());
        m.put("contact",content.getText().toString());
        m.put("city",city.getText().toString());
        m.put("district",district.getText().toString());
        m.put("skill",skill.getText().toString());
        m.put("email",email.getText().toString());
        m.put("latitudeTextView",latitudeTextView.getText().toString());
        m.put("longitTextView",longitTextView.getText().toString());

        FirebaseFirestore.getInstance().collection("student").add(m).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getApplicationContext(),"Save Welcome yor member",Toast.LENGTH_SHORT).show();
//
//
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
//
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }
}
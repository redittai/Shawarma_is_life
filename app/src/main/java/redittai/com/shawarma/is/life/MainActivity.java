package redittai.com.shawarma.is.life;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowLongClickListener, GoogleMap.OnInfoWindowClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    TextView tvLat, tvLon;
    double myLat, myLon;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    Location mLastLocation;
    LatLng me;
    Button add, show;
    public static Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("name").child("store detailes");
    public static List<shawarma> res = new ArrayList<shawarma>();
    public static double Rank;

    public static boolean permissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // layout objects


        //permissions

//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
        //Location methods


        add = (Button) findViewById(R.id.MainBtnADD);
        show = (Button) findViewById(R.id.MainBtnSHOW);
        add.setText(R.string.add);
        show.setText(R.string.show);

        // map methods

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //fireBase methods
        //retreaving data frome database
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {

                    shawarma sh1 = ds1.getValue(shawarma.class);
                    LatLng pos = new LatLng(sh1.getLat(), sh1.getLon());
                    Rank = sh1.getRank();
                    Rank = Double.parseDouble(new DecimalFormat("##.##").format(Rank));
                    mMap.addMarker(new MarkerOptions().position(pos).title(sh1.getName()).snippet("לאפה: " + sh1.getLaffa() + " פיתה: " + sh1.getPita() + "כשר/ לא כשר: " + sh1.getKosher() + ", דירוג: " + Rank));
                    boolean flag = false;
                    for (int i = 0; i < res.size(); i++) {
                        if (sh1.getName().equals(res.get(i).getName())) {
                            flag = true;
                        }

                    }
                    if (flag == false) {
                        res.add(sh1);
                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                shawarma sh1 = dataSnapshot.getValue(shawarma.class);
                LatLng pos = new LatLng(sh1.getLat(), sh1.getLon());

                mMap.addMarker(new MarkerOptions().position(pos).title(sh1.getName()).snippet("לאפה: " + sh1.getLaffa() + " פיתה: " + sh1.getPita() + "כשר/ לא כשר: " + sh1.getKosher() + ", דירוג: " + Rank));

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // google API location services
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //  buildGoogleApiClient();
        // Log.i("a3","API 2");


        locationRequest = new LocationRequest();
        locationRequest.setInterval(60 * 1000);
        locationRequest.setFastestInterval(30 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Log.i("a3", "2");

        requestLocationUpdates();
        Log.i("a3", "3");

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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);

    }

    private void requestLocationUpdates() {

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
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "connection faild", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLat = location.getLatitude();
        myLon = location.getLongitude();
        me = new LatLng(myLat, myLon);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 15));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(me)      // Sets the center of the map to location user
                .zoom(15)                   // Sets the zoom
                .bearing(00)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("a3", "API 1");

        buildGoogleApiClient();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionGranted) {
            if (googleApiClient.isConnected()) {
                requestLocationUpdates();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (permissionGranted)
            googleApiClient.disconnect();
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //google map section
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // if (permissionGranted)
        googleApiClient.connect();

        if (permissionGranted)

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
        try {
            mMap.setMyLocationEnabled(true);
        } catch (Exception e) {
            Log.i("a7",e.getMessage());
        }


        if (mLastLocation != null) {
            me = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            me = new LatLng(32.7926, 35.036328);
        }


        mMap.getUiSettings();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 10));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(me)      // Sets the center of the map to location user
                .zoom(15)                   // Sets the zoom
                .bearing(00)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        try {
            requestLocationUpdates();
        } catch (Exception e) {
            Log.i("a2", e.getMessage());
        }


        me = new LatLng(myLat, myLon);


        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        marker.showInfoWindow();
        return false;
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        try {
            double lat, lon;
            lat = marker.getPosition().latitude;
            lon = marker.getPosition().longitude;
            String url = "waze://?ll=" + lat + "," + lon + "&navigate=yes";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            startActivity(intent);
        } catch (Exception ex) {
            Log.i("a7", ex.toString());
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ratedialog cdd = new ratedialog(this, marker.getTitle());
        cdd.show();
    }


    public void click(View v) {
        Intent in1 = null;
        switch (v.getId()) {

            case R.id.MainBtnADD:
                in1 = new Intent(getApplicationContext(), AddShawarma.class);
                break;
            case R.id.MainBtnSHOW:
                if (me != null) {
                    setDistance(res, me);
                }
                Collections.sort(res, new Comparator<shawarma>() {
                    @Override
                    public int compare(shawarma o1, shawarma o2) {
                        if (o1.getDistance() > o2.getDistance())
                            return 1;
                        else if (o1.getDistance() < o2.getDistance())
                            return -1;
                        else
                            return 0;
                    }
                });
                //  Log.i("a7", me.toString());
                in1 = new Intent(getApplicationContext(), shawarmaList.class);
                break;
        }
        try {
            startActivity(in1);
        } catch (Exception e) {
            Log.i("a2", e.getMessage());
        }
    }

    public static void setDistance(List<shawarma> res, LatLng me) {
        for (int i = 0; i < res.size(); i++) {
            double lat, lon;
            lat = res.get(i).getLat();
            lon = res.get(i).getLon();
            LatLng loc = new LatLng(lat, lon);
            Location locA = new Location("myLocation");
            Location locB = new Location("store Location");
            locA.setLatitude(me.latitude);
            locA.setLongitude(me.longitude);
            locB.setLatitude(lat);
            locB.setLongitude(lon);
            float distance = (locA.distanceTo(locB)) / 1000;
            res.get(i).setDistance(distance);
            Log.i("a9", "store name: " + res.get(i).getName() + " distance is: " + distance);
        }
    }


}

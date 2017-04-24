package com.exmo.exmo_test1;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.exmo.exmo_test1.Entities.EventLocation;
import com.exmo.exmo_test1.Entities.Schedule;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.exmo.exmo_test1.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class MapActivity extends NavBar implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraIdleListener ,GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference dbSCheduleRef;
    private ArrayList<EventLocation> eventLocations;
    private ArrayList<String> eventLocationKey;
    private SupportMapFragment mapFragment;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ArrayList<Marker> stallMarkers;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        setTitle("Map");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //zoom to Mora
        LatLng mora = new LatLng(6.796834, 79.900737);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mora));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);

        getData();

        addPermanentMarkers(); //this method add permanent markers like markers for depratment
        stallMarkers = new ArrayList<>();
        //Add markers from the data obtained from the db


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null){
            mMap.setOnMarkerClickListener(this);
        }
    }

    //this method add permanent markers like markers for depratment
    private void addPermanentMarkers() {
        addPermanentMarkerHelper(6.796975, 79.900260, "Computer Science and Engineering");
        addPermanentMarkerHelper(6.796404, 79.899769, "Earth Resource Engineering");


    }

    private void addPermanentMarkerHelper(Double lat, Double lang, String title){
        LatLng location = new LatLng(lat, lang);
        mMap.addMarker(new MarkerOptions().position(location).
                title(title).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
    }



    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //google map
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,
                        this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

    }

    //google map
    @Override
    public void onConnected(Bundle connectionHint) {
        mapFragment.getMapAsync(this);

    }


    //get necessary data from db
    private void getData(){
        dbSCheduleRef = FirebaseDatabase.getInstance().getReference().child("eventLocation");

        eventLocations = new ArrayList<>();
        eventLocationKey = new ArrayList<>();

        dbSCheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()){
                    eventLocations.add(snapShot.getValue(EventLocation.class));
                    eventLocationKey.add(snapShot.getKey());
                }
                int i=0;
                for (EventLocation eventLocation: eventLocations){
                    LatLng location = new LatLng(eventLocation.getLat(), eventLocation.getLang());
                    MarkerOptions markerOpt = new MarkerOptions().position(location).visible(false);
                    Marker marker =mMap.addMarker(markerOpt);
                    marker.setTag(i);
                    stallMarkers.add(marker);

                    i+=1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onCameraIdle () {

        Log.e("zoom",""+mMap.getCameraPosition().zoom);
        if (mMap.getCameraPosition().zoom>18.1){
            for (Marker marker: stallMarkers){
                marker.setVisible(true);
            }
        }else{
            for (Marker marker: stallMarkers){
                marker.setVisible(false);
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        boolean found = false;
        for (Marker m:stallMarkers){
            if (m.getTag()==marker.getTag()){
                found=true;
                break;
            }
        }
        if (!found){
            return false;
        }

        Snackbar snackbar1 = Snackbar.make(drawer,
                eventLocations.get((Integer)marker.getTag()).getTitle(), Snackbar.LENGTH_INDEFINITE).
                setAction("more detail", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d=new Intent(MapActivity.this,UnitStallActivity.class);
                d.putExtra("tag",eventLocationKey.get((Integer)marker.getTag()));
                startActivity(d);
            }
        });
        snackbar1.show();
        return false;
    }

   /* private void customizeMap(){
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("error", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("error", "Can't find style. Error: ", e);
        }
    }*/

}

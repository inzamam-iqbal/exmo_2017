package com.uom.exhibition;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.uom.exhibition.Entities.EventLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uom.exhibition.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapActivity extends NavBar implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

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
    private ArrayList<Marker> permanentMarkers;
    private String keyStall;
    private String keyDept;
    ArrayList<String> allDeps;

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

        allDeps = new ArrayList<>();
        allDeps.add("Chemical & Process Engineering");
        allDeps.add("Civil Engineering");
        allDeps.add("Computer Science & Engineering");
        allDeps.add("Earth Resource Engineering");
        allDeps.add("Electrical Engineering");
        allDeps.add("Electronics & Telecommunication Engineering");
        allDeps.add("Fashion Design & Product Development");
        allDeps.add("Material Science & Engineering");
        allDeps.add("Mechanical Engineering");
        allDeps.add("Textile & Clothing Technology");
        allDeps.add("Transport & Logistic Management");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        LatLng mora = new LatLng(6.796834, 79.900737);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mora));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);

        getData();

        addPermanentMarkers();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(0,200,0,0);

       /* LatLngBounds newarkBounds = new LatLngBounds(
                new LatLng(6.796526, 79.899392),       // South west corner
                new LatLng(6.797205, 79.900822));      // North east corner
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .positionFromBounds(newarkBounds);

        mMap.addGroundOverlay(newarkMap);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null){
            mMap.setOnMarkerClickListener(this);
        }

        keyStall=getIntent().getStringExtra("KeyStall");
        keyDept=getIntent().getStringExtra("KeyDept");


        Log.e("keyStall",keyStall+"");
        Log.e("keyDept",keyDept+"");

    }

    private void zoomToMarker(String key) {

        Log.e("zoomToMarker",key+"");
        Marker selected  = null;



        for (Marker marker: permanentMarkers){

            Log.e("mrker tag",marker.getTag().toString()+"");
            if (marker.getTag().toString().equals(key)){
                selected = marker;
            }
        }

        if(selected == null){
            for (Marker marker:stallMarkers){
                if (eventLocationKey.get((Integer) marker.getTag()).equals(key)){
                    selected = marker;
                }
            }
        }


        if (selected != null){
            Log.e("selected", selected.getTag().toString());
            LatLng loc =selected.getPosition();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.animateCamera(CameraUpdateFactory.zoomTo((float) 20));
        }

    }

    //this method add permanent markers like markers for depratment
    private void addPermanentMarkers() {
        permanentMarkers = new ArrayList<>();
        addPermanentMarkerHelper(6.796054, 79.899458,0+"");
        addPermanentMarkerHelper(6.798377, 79.902621,1+"");
        addPermanentMarkerHelper(6.7969679, 79.9004262,2+"");
        addPermanentMarkerHelper(6.796531, 79.899025,3+"");
        addPermanentMarkerHelper(6.796726, 79.900117,4+"");
        addPermanentMarkerHelper(6.796615, 79.901273,5+"");
        addPermanentMarkerHelper(6.798501, 79.901825,6+"");
        addPermanentMarkerHelper(6.796455, 79.899744,7+"");
        addPermanentMarkerHelper(6.796427, 79.898916,8+"");
        addPermanentMarkerHelper(6.798329, 79.901297,9+"");
        addPermanentMarkerHelper(6.797804, 79.901847,10+"");



    }

    private void addPermanentMarkerHelper(Double lat, Double lang,String tag){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        LatLng location = new LatLng(lat, lang);
        MarkerOptions opt = new MarkerOptions().position(location).
                icon(icon);
        Marker m =  mMap.addMarker(opt);
        m.setTag(tag);
        permanentMarkers.add(m);
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
        stallMarkers = new ArrayList<>();

        dbSCheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()){
                    eventLocations.add(snapShot.getValue(EventLocation.class));
                    eventLocationKey.add(snapShot.getKey());
                }

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker1);
                int i=0;
                for (EventLocation eventLocation: eventLocations){
                    LatLng location = new LatLng(eventLocation.getLat(), eventLocation.getLang());
                    MarkerOptions markerOpt = new MarkerOptions().
                            position(location).
                            icon(icon).
                            visible(false);
                    Marker marker =mMap.addMarker(markerOpt);
                    marker.setTag(i);
                    stallMarkers.add(marker);

                    i+=1;
                }

                if(keyStall!=null){
                    zoomToMarker(keyStall);
                }
                if(keyDept!=null){
                    zoomToMarker(keyDept);
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
        if (stallMarkers != null){
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

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        boolean foundStall = false;
        for (Marker m:stallMarkers){
            if (m.getTag()==marker.getTag()){
                foundStall =true;
                break;
            }
        }
        if (foundStall){
            Snackbar snackbar1 = Snackbar.make(drawer,
                    eventLocations.get((Integer)marker.getTag()).getTitle(), Snackbar.LENGTH_INDEFINITE).
                    setAction("More", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent d=new Intent(MapActivity.this,UnitStallActivity.class);
                            d.putExtra("tag",eventLocationKey.get((Integer)marker.getTag()));
                            d.putExtra("stallName",eventLocations.get((Integer)marker.getTag()).getTitle());
                            startActivity(d);
                        }
                    });
            snackbar1.show();
        }

        boolean foundDep = false;
        for (Marker m:permanentMarkers){
            if (m.getTag()==marker.getTag()){
                foundDep =true;
                break;
            }
        }
        if (foundDep){
            Snackbar snackbar1 = Snackbar.make(drawer,
                    allDeps.get(Integer.parseInt(marker.getTag().toString())), Snackbar.LENGTH_INDEFINITE).
                    setAction("More", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent d=new Intent(MapActivity.this,UnitDepartmentActivity.class);
                            d.putExtra("DepNum",marker.getTag().toString());
                            startActivity(d);
                        }
                    });
            snackbar1.show();
        }

        return false;
    }


}


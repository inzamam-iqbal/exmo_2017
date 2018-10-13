package com.moratuwa.exhibition;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.moratuwa.exhibition.Entities.EventLocation;
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
import com.moratuwa.exhibition.Parent.NavBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moratuwa.exhibition.utils.FloorSelectionMarkerTag;
import com.moratuwa.exhibition.utils.GroundOverlayTag;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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
    private ArrayList<Marker> floorSelectionMarkers;
    private boolean locked = false;
    private FloatingActionButton reloadBtn;
    private ConcurrentHashMap<String, GroundOverlayTag> groundOverlayTags;
    private ArrayList<GroundOverlay> groundOverlays;

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

        reloadBtn = (FloatingActionButton) findViewById(R.id.fab);

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

        groundOverlays = new ArrayList<>();
        groundOverlayTags = new ConcurrentHashMap<>();

        appplyOverlay();

        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locked=false;
                LatLng mora = new LatLng(6.796834, 79.900737);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mora));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            }
        });

    }

    private void appplyOverlay() {
        putOverlay(6.796437,79.899995,6.795623,79.899292,R.drawable.chem_map0,0,0,true);

        putOverlay(6.798928,79.903243,6.798161,79.902293,R.drawable.civil_map0,1,0,true);
        putOverlay(6.798095,79.902830,6.797800,79.902401,R.drawable.civilres_map0,1,0,true);
        putOverlay(6.798095,79.902830,6.797800,79.902401,R.drawable.civilres_map1,1,1,false);
        putOverlay(6.798095,79.902830,6.797800,79.902401,R.drawable.civilres_map2,1,2,false);

        putOverlay(6.797205,79.900803,6.796535,79.899407,R.drawable.cse_map2,2,2,false);

        putOverlay(6.796587  ,79.900103 ,6.796247,79.899433 ,R.drawable.er_map1,3,1,false);

        putOverlay(6.797155,79.900797,6.796544,79.899407,R.drawable.ee_map0, 4, 0,true);
        putOverlay(6.797155,79.900797,6.796544,79.899407,R.drawable.ee_map1,4,1,false);

        putOverlay(6.796831  ,79.901717 ,6.796320,79.901031 ,R.drawable.entc_map0,5,0,true);
        putOverlay(6.796831  ,79.901717 ,6.796320,79.901031 ,R.drawable.entc_map1,5,6,false);
        putOverlay(6.796831  ,79.901717 ,6.796320,79.901031 ,R.drawable.entc_map2,5,1,false);
        putOverlay(6.796831  ,79.901717 ,6.796320,79.901031 ,R.drawable.entc_map3,5,2,false);
        putOverlay(6.796831  ,79.901717 ,6.796320,79.901031 ,R.drawable.entc_map4,5,3,false);
        //have to replace with correct one
        putOverlay(6.796831  ,79.901717 ,6.796320,79.901031 ,R.drawable.entc_map4,5,7,false);

        putOverlay(6.798671  ,79.902051 ,6.798283,79.901593 ,R.drawable.fd_map0,6,0,true);
        putOverlay(6.798671  ,79.902051 ,6.798283,79.901593 ,R.drawable.fd_map1,6,1,false);

        putOverlay(6.796587  ,79.900103 ,6.796247,79.899433 ,R.drawable.mat_map0,7,0,true);
        putOverlay(6.796587  ,79.900103 ,6.796247,79.899433 ,R.drawable.mat_map2,7,2,false);

        putOverlay(6.796888  ,79.899531 ,6.795640,79.898581 ,R.drawable.mech_map0,8,0,true);
        putOverlay(6.796888  ,79.899531 ,6.795640,79.898581 ,R.drawable.mech_map1,8,1,false);
        putOverlay(6.796888  ,79.899531 ,6.795640,79.898581 ,R.drawable.mech_map2,8,2,false);

        putOverlay(6.798475  ,79.901992 ,6.797855,79.901139 ,R.drawable.tex_map0,9,0,true);
        putOverlay(6.798475  ,79.901992 ,6.797855,79.901139 ,R.drawable.tex_map1,9,1,false);

        putOverlay(6.797881,79.902263,6.797665,79.901698,R.drawable.tlm_map0,10,0,true);
        putOverlay(6.797881,79.902263,6.797665,79.901698,R.drawable.tlm_map1,10,1,false);
        putOverlay(6.797881,79.902263,6.797665,79.901698,R.drawable.tlm_map2,10,2,false);
        putOverlay(6.797881,79.902263,6.797665,79.901698,R.drawable.tlm_map3,10,3,false);
    }

    private void putOverlay(double north, double east, double south, double west, int map,
                            int departmentId, int floor, boolean state) {
        LatLngBounds newarkBounds = new LatLngBounds(
                new LatLng(south,west),       // South west corner
                new LatLng(north, east));      // North east corner
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(map))
                .positionFromBounds(newarkBounds);

        GroundOverlayTag tag = null;
        GroundOverlay ground = mMap.addGroundOverlay(newarkMap);
        if (floor == 6){
            tag = new GroundOverlayTag(String.valueOf(0.5),String.valueOf(departmentId));
        }else if (floor == 7){
            tag = new GroundOverlayTag(String.valueOf(3.5),String.valueOf(departmentId));
        }else{
            tag = new GroundOverlayTag(String.valueOf(floor),String.valueOf(departmentId));
        }

        groundOverlayTags.put(ground.getId(), tag);
        groundOverlays.add(ground);
        ground.setVisible(state);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null){
            mMap.setOnMarkerClickListener(this);
        }

        keyStall=getIntent().getStringExtra("KeyStall");
        keyDept=getIntent().getStringExtra("KeyDept");

        if (mMap!= null){
            mMap.clear();
            stallMarkers.clear();
            getData();
        }


        Log.e("keyStall",keyStall+"");
        Log.e("keyDept",keyDept+"");

    }

    private void zoomToMarker(String key) {

        Log.e("zoomToMarker",key+"");
        Marker selected  = null;



        for (Marker marker: permanentMarkers){
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
            keyStall =null;
            keyDept =null;

        }

    }

    //this method add permanent markers like markers for depratment
    private void addPermanentMarkers() {
        permanentMarkers = new ArrayList<>();
        floorSelectionMarkers = new ArrayList<>();
        //departments
        addPermanentMarkerHelper(6.796054, 79.899458,0+"");
        addPermanentMarkerHelper(6.798377, 79.902621,1+"");
        addPermanentMarkerHelper(6.7969679, 79.9004262,2+"");
        addPermanentMarkerHelper(6.796445, 79.899844,3+"");
        addPermanentMarkerHelper(6.796726, 79.900117,4+"");
        addPermanentMarkerHelper(6.796615, 79.901273,5+"");
        addPermanentMarkerHelper(6.798501, 79.901825,6+"");
        addPermanentMarkerHelper(6.796455, 79.899744,7+"");
        addPermanentMarkerHelper(6.796427, 79.898916,8+"");
        addPermanentMarkerHelper(6.798329, 79.901297,9+"");
        addPermanentMarkerHelper(6.797804, 79.901847,10+"");

        //building floor selection marker
        //cse and ee
        addFloorSelectionMarkerHelper(6.797141, 79.900033,0,4);
        addFloorSelectionMarkerHelper(6.797138, 79.900241,1,4);
        addFloorSelectionMarkerHelper(6.797135, 79.900411,2,2);

        //mech
        addFloorSelectionMarkerHelper(6.795755, 79.898797,0,8);
        addFloorSelectionMarkerHelper(6.795758, 79.898936,1,8);
        addFloorSelectionMarkerHelper(6.795742, 79.899087,2,8);

        //material and earth
        addFloorSelectionMarkerHelper(6.796414, 79.900088,0,7);
        addFloorSelectionMarkerHelper(6.796358, 79.900088,1,3);
        addFloorSelectionMarkerHelper(6.796278, 79.900077,2,7);

        //civil reserch
        addFloorSelectionMarkerHelper(6.798093, 79.902612,0,1);
        addFloorSelectionMarkerHelper(6.798088, 79.902683,1,1);
        addFloorSelectionMarkerHelper(6.798077, 79.902768,2,1);

        //entc
        addFloorSelectionMarkerHelper(6.796878, 79.901298,0,5);
        addFloorSelectionMarkerHelper(6.796814, 79.901365,6,5);
        addFloorSelectionMarkerHelper(6.796772, 79.901437,1,5);
        addFloorSelectionMarkerHelper(6.796744, 79.901497,2,5);
        addFloorSelectionMarkerHelper(6.796717, 79.901559,3,5);
        addFloorSelectionMarkerHelper(6.796688, 79.901613,7,5);

        //fd
        addFloorSelectionMarkerHelper(6.798658, 79.901825,0,6);
        addFloorSelectionMarkerHelper(6.798653, 79.901919,1,6);

        //tex
        addFloorSelectionMarkerHelper(6.798459, 79.901436,0,9);
        addFloorSelectionMarkerHelper(6.798467, 79.901554,1,9);

        //tlm
        addFloorSelectionMarkerHelper(6.797869, 79.902008,0,10);
        addFloorSelectionMarkerHelper(6.797810, 79.902006,1,10);
        addFloorSelectionMarkerHelper(6.797749, 79.902000,2,10);
        addFloorSelectionMarkerHelper(6.797667, 79.901995,3,10);


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

    private void addFloorSelectionMarkerHelper(Double lat, Double lang, int floorNumber,int depId){
        Integer[] floorMarkers = {R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.three,
        R.drawable.four, R.drawable.five, R.drawable.zeropointfive,R.drawable.threepointfive};
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(floorMarkers[floorNumber]);
        LatLng location = new LatLng(lat, lang);
        MarkerOptions opt = new MarkerOptions().position(location).
                icon(icon);
        Marker m =  mMap.addMarker(opt);
        m.setVisible(false);
        if (floorNumber == 6){
            m.setTag(new FloorSelectionMarkerTag(String.valueOf(0.5),String.valueOf(depId)));
        }else if (floorNumber == 7){
            m.setTag(new FloorSelectionMarkerTag(String.valueOf(3.5),String.valueOf(depId)));
        }else{
            m.setTag(new FloorSelectionMarkerTag(String.valueOf(floorNumber),String.valueOf(depId)));
        }

        floorSelectionMarkers.add(m);

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
                if (stallMarkers.size() == 0){
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
                        marker.setTag(new Integer(i));
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onCameraIdle () {


        Log.e("zoom",""+mMap.getCameraPosition().zoom);
        Log.e("locked", String.valueOf(locked));
        Log.e("stallMarkers", stallMarkers.size()+" size");
        if (stallMarkers != null && !locked){
            if (mMap.getCameraPosition().zoom>18.1){
                for (Marker marker: stallMarkers){
                    marker.setVisible(true);
                }
            }else{
                for (Marker marker: stallMarkers){
                    marker.setVisible(false);
                    Log.e("markerhide",String.valueOf(marker.isVisible()));
                }
            }
        }else if (stallMarkers != null && mMap.getCameraPosition().zoom<17.8){
            for (Marker marker: stallMarkers){
                marker.setVisible(false);
            }
            locked = false;
        }

        if (mMap.getCameraPosition().zoom>18.1){
            for (Marker marker: floorSelectionMarkers){
                marker.setVisible(true);
            }
        }else{
            for (Marker marker: floorSelectionMarkers){
                marker.setVisible(false);
            }
        }

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.getTag() instanceof String){
            //department marker
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
        }else if (marker.getTag() instanceof Integer){
            //stall markers
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
        }else if (marker.getTag() instanceof FloorSelectionMarkerTag){
            //floor selection markers
            locked = true;
            FloorSelectionMarkerTag markerTagTemp = (FloorSelectionMarkerTag) marker.getTag();

            for (GroundOverlay ground: groundOverlays){
                if (groundOverlayTags.get(ground.getId()).getFloor().equals(markerTagTemp.getFloor()) &&
                        groundOverlayTags.get(ground.getId()).getDepartmentId().equals(markerTagTemp.getDepartmentId())){
                    ground.setVisible(true);
                }else{
                    ground.setVisible(false);
                }
                Log.e(markerTagTemp.getDepartmentId()+" "+markerTagTemp.getFloor(),
                        groundOverlayTags.get(ground.getId()).getFloor()+" "+groundOverlayTags.get(ground.getId()).getDepartmentId() );
            }

            for (Marker m: stallMarkers){
                EventLocation temp = eventLocations.get((Integer) m.getTag());

                if (temp.getFloor().equals((markerTagTemp.getFloor())) &&
                        temp.getDepartment().equals(markerTagTemp.getDepartmentId())){
                    m.setVisible(true);
                    continue;
                }else{
                    m.setVisible(false);
                }
            }
        }


        /*boolean foundStall = false;
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
        }*/

        return false;
    }


}


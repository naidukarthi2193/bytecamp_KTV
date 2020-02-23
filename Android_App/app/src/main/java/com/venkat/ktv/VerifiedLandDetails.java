package com.venkat.ktv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

public class VerifiedLandDetails extends AppCompatActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener{

    GoogleMap gmap;
    CheckBox checkBox;
    SeekBar seekRed,seekGreen,seekBlue;
    Button btdraw,btClear,btUpload;
    TextView uidnum,currHec,area1,a1,a2,a3,a4;
    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();
    private String docuid;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_land_details);

        Bundle bundle = getIntent().getExtras();
        docuid = bundle.getString("docid");

        checkBox = findViewById(R.id.check_box);
        seekRed = findViewById(R.id.seek_red);
        seekBlue = findViewById(R.id.seek_blue);
        seekGreen = findViewById(R.id.seek_green);
        btdraw = findViewById(R.id.bt_draw);
        btClear = findViewById(R.id.bt_clear);
        btUpload = findViewById(R.id.bt_upload);
        uidnum = findViewById(R.id.uidno);
        currHec = findViewById(R.id.alx1);
        area1 = findViewById(R.id.alx);
        a1 = findViewById(R.id.al1);
        a2 = findViewById(R.id.al2);
        a3 = findViewById(R.id.al3);
        a4 = findViewById(R.id.al4);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
        getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

//        SupportMapFragment supportMapFragment = (SupportMapFragment)
//                getSupportFragmentManager().findFragmentById(R.id.google_map);
//        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        Intent intent = getIntent();
        //final String UID = intent.getStringExtra("UID");
        final String UID = "987417411813";
        final DocumentReference docRef=db.collection("117A10920").document(docuid).collection("Lands")
                .document("PolyMarks");
        final DocumentReference pointref = db.collection(UID).document("point");
        final CollectionReference geoFencedRef = db.collection("GeoFenced");
        final CollectionReference polyMrks = db.collection("117A10920").document(docuid).collection("Lands");

        final DocumentReference LnMrks = db.collection("117A10920").document(docuid).collection("Lands")
                .document("LandMarks");

        final DocumentReference docRef_curr_area=db.collection("117A10920").document(docuid).collection("Lands")
                .document("Point");
        final DocumentReference docRef_exp_area=db.collection("117A10920").document(docuid).collection("Lands")
                .document("currArea");

        docRef_curr_area.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()){
                    String curarea = documentSnapshot.getString("area");
                    currHec.setText(curarea);
                }
            }
        });

        docRef_exp_area.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()){
                    String exparea = documentSnapshot.getString("CurrHect");
                    currHec.setText(exparea);
                }
            }
        });

        LnMrks.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()){
                    String la1 = documentSnapshot.getString("land1");
                    String la2 = documentSnapshot.getString("land2");
                    String la3 = documentSnapshot.getString("land3");
                    String la4 = documentSnapshot.getString("land4");

                    Log.d("tagg", la1);

                    a1.setText(la1);
                    a2.setText(la2);
                    a3.setText(la3);
                    a4.setText(la4);
                }
            }
        });

//        polyMrks.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                            List<Double> latPoints1 = (List<Double>) documentSnapshot.get("latList");
//                            List<Double> lonPoints1 = (List<Double>) documentSnapshot.get("lonList");
//                           // Log.d("yoooo", String.valueOf(latPoints1.get(1)));
//                            for (int i = 0; i<latPoints1.size(); i++){
//
//                                LatLng glatLng = new LatLng(latPoints1.get(i),lonPoints1.get(i));
//                                latLngList.add(glatLng);
//
//
////                                GeoGeolatlng.add(new LatLng(latPoints1.get(i),lonPoints1.get(i)));
//                            }
////
//                        }
//
//                    }
//                });
//
//        if(polygon!=null) polygon.remove();
//
//        final PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList).clickable(true);
//        polygon = gmap.addPolygon(polygonOptions);
//        polygon.setStrokeColor(R.color.grey);
//        if (checkBox.isChecked())
//            polygon.setFillColor(R.color.grey);

        final PolygonOptions polygonOptions = new PolygonOptions();
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()) {
                    List<Double> latPoints = (List<Double>) documentSnapshot.get("latList");
                    List<Double> lonPoints = (List<Double>) documentSnapshot.get("lonList");
                    //Toast.makeText(MainActivity.this, latPoints.size(), Toast.LENGTH_SHORT).show();
                    Log.d("yooo", String.valueOf(latPoints.size()));

                    for(int i = 0; i < latPoints.size(); i++){

//                        String lat = latPoints.get(i);
//                        String lon = lonPoints.get(i);
                        Double lat1= latPoints.get(i);
                        Double longi1= lonPoints.get(i);
                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,longi1), 15));
//                                String area = documentSnapshot.getString("area");
//                                area1.setText(area);
//                        Log.d("yooo", String.valueOf(longi1));
//                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat1,longi1));
//                        Marker marker = gmap.addMarker(markerOptions);
                        polygonOptions.add(new LatLng(lat1, longi1));
                    }
                    if(polygon!=null) polygon.remove();
                    polygon = gmap.addPolygon(polygonOptions);
                    polygon.setStrokeColor(R.color.grey);
                    polygon.setFillColor(R.color.grey);
//                    String lat = documentSnapshot.getString("lati");
//                    String lon = documentSnapshot.getString("longi");
//                    Double lat1=Double.parseDouble(lat);
//                    Double longi1=Double.parseDouble(lon);
//                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,longi1), 15));
//                    String area = documentSnapshot.getString("area");
//                    area1.setText(area);
//
//

//
//                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat1,longi1));
//                    Marker marker = gmap.addMarker(markerOptions);
//                    //markerList.add(marker);
                    //mCurrLocationMarker = gmap.addMarker(markerOptions);
//                    gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                        @Override
//                        public void onMapClick(LatLng latLng) {
//                            Log.d("yyy", "onMapClick: ");
//                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker32));
//                            Marker marker = gmap.addMarker(markerOptions);
//                            latLngList.add(latLng);
//                            //markerList.add(marker);
//
//                        }
//                    });
                }
            }
        });

        gmap.setMapType(gmap.MAP_TYPE_HYBRID);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

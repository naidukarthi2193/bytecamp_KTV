package com.venkat.ktv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {

    GoogleMap gmap;
    CheckBox checkBox;
    SeekBar seekRed,seekGreen,seekBlue;
    Button btdraw,btClear,btUpload;
    TextView uidnum,currHec,area1,a1,a2,a3,a4;
    private String docuid;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<LatLng> GeofencedlatLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    List<Double> latList = new ArrayList<>();
    List<Double> lonList = new ArrayList<>();
    List<LatLng> GeoGeolatlng = new ArrayList<LatLng>();
    int red=0 ,green =0 ,blue=0;
    int flag ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        supportMapFragment.getMapAsync((OnMapReadyCallback) this);


//        Intent intent = getIntent();
//        final String UID = intent.getStringExtra("UID");
        //Toast.makeText(getApplicationContext(),UID,Toast.LENGTH_SHORT).show();
        final String UID = "987417411813";

        uidnum.setText(UID);

        //final DocumentReference notebookRef = db.collection(UID).document("Land");
        final DocumentReference notebookRef = db.collection("GeoFenced").document();
        final DocumentReference pointref = db.collection(UID).document("point");
        final DocumentReference pdref = db.collection("117A1092").document("personDetails");
        final DocumentReference LnMrks = db.collection("117A10920").document(docuid).collection("Lands")
                .document("LandMarks");
        final DocumentReference polyMrks = db.collection("117A10920").document(docuid).collection("Lands")
                .document("PolyMarks");

        LnMrks.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String la1 = documentSnapshot.getString("land1");
                        String la2 = documentSnapshot.getString("land2");
                        String la3 = documentSnapshot.getString("land3");
                        String la4 = documentSnapshot.getString("land4");

                        a1.setText(la1);
                        a2.setText(la2);
                        a3.setText(la3);
                        a4.setText(la4);
                    }
                });

//        pointref.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()){
//                            List<String> latPoints = (List<String>) documentSnapshot.get("longiList");
//                            List<String> lonPoints = (List<String>) documentSnapshot.get("longiList");
//                            //Toast.makeText(MainActivity.this, latPoints.size(), Toast.LENGTH_SHORT).show();
//                            Log.d("yooo", String.valueOf(latPoints.size()));
//                            for(int i = 0; i < latPoints.size(); i++){
//
//                                String lat = latPoints.get(i);
//                                String lon = lonPoints.get(i);
//                                Double lat1=Double.parseDouble(lat);
//                                Double longi1=Double.parseDouble(lon);
////                                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,longi1), 15));
////                                String area = documentSnapshot.getString("area");
////                                area1.setText(area);
//                                Log.d("yooo", String.valueOf(lat1));
//                                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat1,longi1));
//                                Marker marker = gmap.addMarker(markerOptions);
//                            }
////                            Toast.makeText(MainActivity.this, latPoints.size(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        //final DocumentReference notebookRef=db.collection(UID).document();
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("aaa", "onClick: "+flag);
                //if (flag == 1) {
                for (LatLng ll : latLngList) {
                    double lat1 = ll.latitude;
                    double log1 = ll.longitude;
                    latList.add(lat1);
                    lonList.add(log1);

                }
                coordModel coordModel1 = new coordModel(latList, lonList);
                polyMrks.set(coordModel1);

                Toast.makeText(getApplicationContext(), "UPLOADED", Toast.LENGTH_SHORT).show();
                //}
//                else{
//                    Toast.makeText(getApplicationContext(), "Not Accurate", Toast.LENGTH_SHORT).show();
//                }
            }

        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (polygon == null)return;
                    polygon.setFillColor(R.color.grey);


                }else {
                    polygon.setFillColor(Color.TRANSPARENT);
                }
            }
        });

        btdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(polygon!=null) polygon.remove();

                PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList).clickable(true);
                polygon = gmap.addPolygon(polygonOptions);
                polygon.setStrokeColor(R.color.grey);
                if (checkBox.isChecked())
                    polygon.setFillColor(R.color.grey);

                Toast.makeText(getApplicationContext(),String.valueOf( SphericalUtil.computeArea(latLngList)),Toast.LENGTH_SHORT).show();
                currHec.setText(String.valueOf( SphericalUtil.computeArea(latLngList)));
                float f1 = Float.parseFloat(String.valueOf(area1.getText()));
                float f2 = Float.parseFloat((String) currHec.getText());
                //float f3 = (f1/f2)*100;
                float f3 = ((Math.abs(f1 - f2))/f1)*100;
                //Toast.makeText(getApplicationContext(), (int) f3,Toast.LENGTH_SHORT);
                Log.d("aaa", "onClick: "+ f3);
                if(f3<10){
                    flag = 1;
                }


            }
        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polygon!=null)polygon.remove();
                for (Marker marker : markerList)marker.remove();
                latLngList.clear();
                markerList.clear();
                latList.clear();
                lonList.clear();
//                checkBox.setChecked(false);
                seekRed.setProgress(0);
                seekGreen.setProgress(0);
                seekBlue.setProgress(0);


            }
        });
        seekRed.setOnSeekBarChangeListener(this);
        seekGreen.setOnSeekBarChangeListener(this);
        seekBlue.setOnSeekBarChangeListener(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        Intent intent = getIntent();
        //final String UID = intent.getStringExtra("UID");
        final String UID = "987417411813";
        final DocumentReference docRef=db.collection(UID).document("point");
        final DocumentReference pointref = db.collection(UID).document("point");
        final CollectionReference geoFencedRef = db.collection("GeoFenced");

        geoFencedRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            List<Double> latPoints1 = (List<Double>) documentSnapshot.get("latList");
                            List<Double> lonPoints1 = (List<Double>) documentSnapshot.get("lonList");
                            //Log.d("yoooo", String.valueOf(latPoints1.get(1)));
                            for (int i = 0; i<latPoints1.size(); i++){

                                LatLng glatLng = new LatLng(latPoints1.get(i),lonPoints1.get(i));
                                GeofencedlatLngList.add(glatLng);
//                                GeoGeolatlng.add(new LatLng(latPoints1.get(i),lonPoints1.get(i)));
                            }
//                            GeoGeolatlng.add(GeofencedlatLngList);
//                            GeofencedlatLngList.clear();


                            //GeofencedlatLngList.clear();

                            //List<LatLng> latLngList1 = new ArrayList<>();
                        }
                        //Log.d("yoooo", String.valueOf(GeoGeolatlng.get(1)));
//                        for (LatLng ll : GeoGeolatlng.get(1)) {
//                            double lat1 = ll.latitude;
//                            double log1 = ll.longitude;
//                            //latList.add(lat1);
//                            Log.d("yoooo", String.valueOf("lat "+lat1));
//                            Log.d("yoooo", String.valueOf("lon "+log1));
//                            //lonList.add(log1);
//
//                        }
//                        for(int j =0; j<GeoGeolatlng.size(); j++){
//                            if(polygon!=null) polygon.remove();
//
//                            PolygonOptions polygonOptions = new PolygonOptions().addAll(GeoGeolatlng.get(j)).clickable(true);
//                            polygon = gmap.addPolygon(polygonOptions);
//                            polygon.setStrokeColor(R.color.grey);
//                            if (checkBox.isChecked())
//                                polygon.setFillColor(R.color.grey);
//                        }
                    }
                });

        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()) {
                    List<String> latPoints = (List<String>) documentSnapshot.get("latiList");
                    List<String> lonPoints = (List<String>) documentSnapshot.get("longiList");
                    //Toast.makeText(MainActivity.this, latPoints.size(), Toast.LENGTH_SHORT).show();
                    Log.d("yooo", String.valueOf(latPoints.size()));
                    for(int i = 0; i < latPoints.size(); i++){

                        String lat = latPoints.get(i);
                        String lon = lonPoints.get(i);
                        Double lat1=Double.parseDouble(lat);
                        Double longi1=Double.parseDouble(lon);
                        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,longi1), 15));
//                                String area = documentSnapshot.getString("area");
//                                area1.setText(area);
                        Log.d("yooo", String.valueOf(longi1));
                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat1,longi1));
                        Marker marker = gmap.addMarker(markerOptions);
                    }
//                    String lat = documentSnapshot.getString("lati");
//                    String lon = documentSnapshot.getString("longi");
//                    Double lat1=Double.parseDouble(lat);
//                    Double longi1=Double.parseDouble(lon);
//                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat1,longi1), 15));
                    String area = documentSnapshot.getString("area");
                    area1.setText(area);
//
//

//
//                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat1,longi1));
//                    Marker marker = gmap.addMarker(markerOptions);
//                    //markerList.add(marker);
                    //mCurrLocationMarker = gmap.addMarker(markerOptions);
                    gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            Log.d("yyy", "onMapClick: ");
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker32));
                            Marker marker = gmap.addMarker(markerOptions);
                            latLngList.add(latLng);
                            markerList.add(marker);

                        }
                    });
                }
            }
        });

        gmap.setMapType(gmap.MAP_TYPE_HYBRID);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seek_red:
                red = progress;
                break;
            case R.id.seek_green:
                green = progress;
                break;
            case R.id.seek_blue:
                blue = progress;
                break;

        }
        if(polygon != null){
            polygon.setStrokeColor(R.color.grey);
            if (checkBox.isChecked())
                polygon.setFillColor(R.color.grey);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}


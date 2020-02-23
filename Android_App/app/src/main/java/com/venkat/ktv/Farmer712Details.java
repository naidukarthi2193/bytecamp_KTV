package com.venkat.ktv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Farmer712Details extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView Name,Phno,District,Village,Taluka,DIv,SurveyNo;
    private Button GeofenceApply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer712_details);



        Name = findViewById(R.id.nameTV);
        Phno = findViewById(R.id.phnoTV);
        District = findViewById(R.id.distTV);
        Village = findViewById(R.id.villageTV);
        Taluka = findViewById(R.id.talukaTV);
        DIv = findViewById(R.id.divTV);
        GeofenceApply = findViewById(R.id.apply_geofenceBtn);

        Intent intent = getIntent();
        String UID = intent.getStringExtra("UID");
        DocumentReference personRef = db.collection(UID).document("personDetails");
        Toast.makeText(this,UID+" 7/12", Toast.LENGTH_SHORT).show();

        personRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String taluka = documentSnapshot.getString("taluka");
                            String phno = documentSnapshot.getString("Phnum");
                            String district = documentSnapshot.getString("district");
                            String village = documentSnapshot.getString("village");
                            String div = documentSnapshot.getString("division");


                            Name.setText(name);
                            Taluka.setText(taluka);
                            District.setText(district);
                            Phno.setText(phno);
                            Village.setText(village);
                            DIv.setText(div);


                            //Map<String, Object> note = documentSnapshot.getData();
                            Toast.makeText(Farmer712Details.this, name+" "+taluka, Toast.LENGTH_SHORT).show();

                            Log.d("yeah", "onSuccess: "+name+taluka);
                            //textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                        } else {
                            Toast.makeText(Farmer712Details.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        GeofenceApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Farmer712Details.this,MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}

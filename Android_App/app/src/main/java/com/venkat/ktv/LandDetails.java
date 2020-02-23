package com.venkat.ktv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LandDetails extends AppCompatActivity {
    public LandDetailsAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colref_LDetailsLogs = db.collection("117A10920");
    private String docuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);

        Query query = colref_LDetailsLogs.orderBy("name", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<LandDetailsModel> options = new FirestoreRecyclerOptions.Builder<LandDetailsModel>()
                .setQuery(query,LandDetailsModel.class)
                .build();

        adapter = new LandDetailsAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.rv_LandDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new LandDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String Status = documentSnapshot.getString("status");
                docuid = documentSnapshot.getId();
                if(Status.equals("Verified")){
                    Toast.makeText(LandDetails.this,"Verified", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LandDetails.this, VerifiedLandDetails.class);
                    intent.putExtra("docid", docuid);
                    startActivity(intent);
                }
                else if(Status.equals("Applied")){
                    Toast.makeText(LandDetails.this,"Verified", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LandDetails.this, VerifiedLandDetails.class);
                    intent.putExtra("docid", docuid);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LandDetails.this, MainActivity.class);
                    intent.putExtra("docid", docuid);
                    startActivity(intent);
                }
//                String num = documentSnapshot.getString("number");
//                String path = documentSnapshot.getReference().getPath();


            }
        });

    }

    public String getDocuid(){
        return docuid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}

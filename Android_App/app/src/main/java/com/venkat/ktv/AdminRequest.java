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

public class AdminRequest extends AppCompatActivity {

    public AdminRequestAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colref_LDetailsLogs = db.collection("Admin");
    private String docuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_request);

        Query query = colref_LDetailsLogs.orderBy("status", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<AdminRequestModel> options = new FirestoreRecyclerOptions.Builder<AdminRequestModel>()
                .setQuery(query,AdminRequestModel.class)
                .build();

        adapter = new AdminRequestAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.rv_AdminDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdminRequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                String Status = documentSnapshot.getString("status");
                docuid = documentSnapshot.getId();
                String uid = documentSnapshot.getString("uid");
                String userdocid = documentSnapshot.getString("docuid");
                String admStatus = documentSnapshot.getString("status");

                if(admStatus.equals("Verification Done")){
                    Toast.makeText(AdminRequest.this,"Already Verified", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(AdminRequest.this, AdminAccptReq.class);
                    intent.putExtra("docid", docuid);
                    intent.putExtra("docid1", uid);
                    intent.putExtra("docid2", userdocid);
                    startActivity(intent);
                }
                //Toast.makeText(AdminRequest.this,docuid,Toast.LENGTH_SHORT).show();


//                if(Status.equals("Verified")){
//                    Toast.makeText(AdminRequest.this,"Verified",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AdminRequest.this, VerifiedLandDetails.class);
//                    intent.putExtra("docid", docuid);
//                    startActivity(intent);
//                }
//                else{
//                    Intent intent = new Intent(AdminRequest.this, MainActivity.class);
//                    intent.putExtra("docid", docuid);
//                    startActivity(intent);
//                }
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

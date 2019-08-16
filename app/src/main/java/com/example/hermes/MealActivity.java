package com.example.hermes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealActivity extends AppCompatActivity {

    //final int ITEM_SIZE = 3;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Intent intent = null;
    private String callCabinCrew = null;
    private String seatNumber = null;
    private Map<String, Object> docData = null;
    private Map<String, Object> adminDocData = new HashMap<>();

    private String activityName = "meal";

    private FirebaseFirestore firebaseStoreDB = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        intent = getIntent();
        seatNumber = intent.getExtras().getString("seatNumber");
        docData = (Map<String, Object>) intent.getExtras().getSerializable("docData");

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("docData", (Serializable) docData);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void beef_bnt(View v){
        Toast.makeText(getApplicationContext(),"소고기 기내식을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).child("value").setValue("beef");
        docData.put(activityName, "beef");
        setDocData();
    }
    public void seafood_bnt(View v){
        Toast.makeText(getApplicationContext(),"해물 기내식을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).child("value").setValue("seafood");
        docData.put(activityName, "seafood");
        setDocData();
    }
    public void no_bnt(View v){
        Toast.makeText(getApplicationContext(),"기내식을 안먹겠습니다를 누르셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).child("value").setValue("no");
        docData.put(activityName, "no");
        setDocData();
    }

    public void setDocData(){
        firebaseStoreDB.collection("customer_meal")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot value,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot document : value) {
                            callCabinCrew = document.get("callCabinCrew").toString();
                            docData.put("callCabinCrew",callCabinCrew);
                        }
                    }
                });

        firebaseStoreDB.collection("customer_meal").document(seatNumber)
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }

                });

    }

}




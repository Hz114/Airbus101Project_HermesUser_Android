package com.example.hermes;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class FoodsActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Intent intent = null;

    private String seatNumber = null;
    private String food = null;

    private String strCount;
    private int count;
    private Map<String, Object> docData = new HashMap<>();
    private Map<String, Object> adminDocData = new HashMap<>();

    private FirebaseFirestore firebaseStoreDB = FirebaseFirestore.getInstance();

    private String activityName = "foods";
    //private List <String> foods = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        intent = getIntent();
        seatNumber = intent.getExtras().getString("seatNumber");
        //count = intent.getExtras().getInt("count");
        //docData = (Map<String, Object>) intent.getExtras().getSerializable("docData");

        firebaseStoreDB.collection("admin")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot value,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot document : value) {
                            count = Integer.parseInt(document.get("count").toString());
                        }
                    }
                });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Toast.makeText(getApplicationContext(),"처음화면으로 값을 보냈어요", Toast.LENGTH_LONG).show();

        Intent resultIntent = new Intent();
        //intentResult.putExtra("docData", (Serializable) docData);
        //intentResult.putExtra("data", (Serializable) foods);
        resultIntent.putExtra("count", count);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cracker_bnt(View v){
        Toast.makeText(getApplicationContext(),"과자를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("cracker");
        food = "cracker";
        setDocData();
    }
    public void ramen_bnt(View v){
        Toast.makeText(getApplicationContext(),"라면을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("ramen");
        food = "ramen";
        setDocData();
    }
    public void ice_cream_bnt(View v){
        Toast.makeText(getApplicationContext(),"아이스크림을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("ice cream");
        food = "ice cream";
        setDocData();
    }
    public void peanut_bnt(View v){
        Toast.makeText(getApplicationContext(),"땅콩을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("peanut");
        food = "peanut";
        setDocData();
    }
    public void bread_bnt(View v) {
        Toast.makeText(getApplicationContext(), "빵을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("bread");
        food = "bread";
        setDocData();
    }
    public void setDocData(){
        docData.put("order", food);
        docData.put("category", activityName);
        docData.put("seatNumber",seatNumber);
        if(count<10){
            strCount = "00"+count;
        }else if(count<100){
            strCount = "0"+count;
        }
        firebaseStoreDB.collection("customer_order").document("customer"+strCount)
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        count++;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }

                });
    }


    public void setCountDocData(){
        adminDocData.put("stop","false");
        firebaseStoreDB.collection("admin").document("admin")
                .set(adminDocData)
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









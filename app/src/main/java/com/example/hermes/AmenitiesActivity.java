package com.example.hermes;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
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

public class AmenitiesActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Intent intent = null;

    private String seatNumber = null;
    private String amenity = null;

    private String strCount;
    private int count;
    private Map<String, Object> docData = new HashMap<>();
    private Map<String, Object> adminDocData = new HashMap<>();

    private FirebaseFirestore firebaseStoreDB = FirebaseFirestore.getInstance();

    private String activityName = "amenities";
    private List amenities  = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);

        intent = getIntent();
        seatNumber = intent.getExtras().getString("seatNumber");
        //docData = (Map<String, Object>) intent.getExtras().getSerializable("docData");
        //count = intent.getExtras().getInt("count");

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
                            count++;
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
        //resultIntent.putExtra("count", count);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void earphones_bnt(View v){
        Toast.makeText(getApplicationContext(),"이어폰을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("earphones");
        //amenities.add("earphones");
        amenity = "earphones";
        setDocData();
        setCountDocData();
    }
    public void blanket_bnt(View v){
        Toast.makeText(getApplicationContext(),"담요를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("blanket");
        //amenities.add("blanket");
        amenity = "blanket";
        setDocData();
        setCountDocData();
    }
    public void pillow_bnt(View v){
        Toast.makeText(getApplicationContext(),"베게를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("pillow");
        //amenities.add("pillow");
        amenity = "pillow";
        setDocData();
        setCountDocData();
    }
    public void slippers_bnt(View v){
        Toast.makeText(getApplicationContext(),"슬리퍼를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("slippers");
        //amenities.add("slippers");
        amenity = "slippers";
        setDocData();
        setCountDocData();
    }
    public void eye_patch_bnt(View v){
        Toast.makeText(getApplicationContext(),"안대를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("eye patch");
        //amenities.add("eye patch");
        amenity = "eye patch";
        setDocData();
        setCountDocData();
    }
    public void earplug_bnt(View v){
        Toast.makeText(getApplicationContext(),"귀마개를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("earplug");
        //amenities.add("earplug");
        amenity = "earplug";
        setDocData();
        setCountDocData();
    }
    public void newspaper_bnt(View v){
        Toast.makeText(getApplicationContext(),"신문을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("newspaper");
        //amenities.add("newspaper");
        amenity = "newspaper";
        setDocData();
        setCountDocData();
    }
    public void pen_bnt(View v){
        Toast.makeText(getApplicationContext(),"펜을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("pen");
        //amenities.add("pen");
        amenity = "pen";
        setDocData();
        setCountDocData();
    }

    public void setDocData(){
        docData.put("order", amenity);
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
        adminDocData.put("count",count);
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

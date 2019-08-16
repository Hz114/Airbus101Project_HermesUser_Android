package com.example.hermes;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseFirestore firebaseStoreDB = FirebaseFirestore.getInstance();
    private Map<String, Object> docData = new HashMap<>();
    private Map<String, Object> adminDocData = new HashMap<>();

    private String seatNumber = "B11";
    private String callCabin = "false";

    //private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference.child(seatNumber).child("call cabin crew").child("value").setValue(callCabin);

        docData.put("seatNumber", seatNumber);
        docData.put("callCabinCrew", callCabin);

        //mDdok = mPool.load(this, R.raw.beep_1, 1);

        /**화면 멈춤(STOP SYSTEM)**/
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
                            //adminDocData.put("stop","false");
                            /**
                            if (!document.get("count").toString().equals("0")) {
                                count = Integer.parseInt(document.get("count").toString());
                            }
                             **/
                            if (document.get("stop").toString().equals("true")) {
                                Toast.makeText(getApplicationContext(),"화면이 멈춥니다", Toast.LENGTH_LONG).show();
                                ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
                                tone.startTone(ToneGenerator.TONE_DTMF_5, 500);
                                adminDocData.put("stop","true");
                                stopActivity();
                            }
                        }
                    }
                });


        /* 버튼 사용법
        Button movie = (Button)findViewById(R.id.movie_bnt);

        movie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"준비중입니다", Toast.LENGTH_LONG).show();
            }

        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(getApplicationContext(), String.valueOf(resultCode), Toast.LENGTH_LONG).show();
        if(requestCode==1) {
            if(resultCode == RESULT_OK){
                //Toast.makeText(getApplicationContext(),"intent 성공", Toast.LENGTH_LONG).show();
                /**
                count = data.getExtras().getInt("count");
                adminDocData.put("count", String.valueOf(count));
                setCountDocData();
                 **/
            }else{
                //Toast.makeText(getApplicationContext(),"intent 실패", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode==2){
            if(resultCode == RESULT_OK){
                //Toast.makeText(getApplicationContext(),"intent 성공", Toast.LENGTH_LONG).show();
                docData = (Map<String, Object>) data.getExtras().getSerializable("docData");
            }else{
                //Toast.makeText(getApplicationContext(),"intent 실패", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void info_flight_bnt(View v){
        Toast.makeText(getApplicationContext(),"준비중입니다", Toast.LENGTH_LONG).show();
    }
    public void movie_bnt(View v){
        Toast.makeText(getApplicationContext(),"준비중입니다", Toast.LENGTH_LONG).show();
    }
    public void music_bnt(View v){
        Toast.makeText(getApplicationContext(),"준비중입니다", Toast.LENGTH_LONG).show();
    }
    public void meal_bnt(View v){
        Intent intent_meal = new Intent(this,MealActivity.class);
        intent_meal.putExtra("seatNumber",seatNumber);
        intent_meal.putExtra("docData", (Serializable)docData);
        startActivityForResult(intent_meal,2);
    }
    public void beverages_bnt(View v){
        Intent intent_beverages = new Intent(this,BeveragesActivity.class);
        intent_beverages.putExtra("seatNumber",seatNumber);
        //intent_beverages.putExtra("count", count);
        startActivityForResult(intent_beverages,1);
    }
    public void food_bnt(View v){
        Intent intent_foods = new Intent(this,FoodsActivity.class);
        intent_foods.putExtra("seatNumber",seatNumber);
        //intent_foods.putExtra("count", count);
        startActivityForResult(intent_foods,1);
    }
    public void amenities_bnt(View v){
        Intent intent_amenities = new Intent(this, AmenitiesActivity.class);
        intent_amenities.putExtra("seatNumber",seatNumber);
        //intent_amenities.putExtra("count", count);
        startActivityForResult(intent_amenities,1);
    }
    public void pills_bnt(View v){
        Intent intent_pills = new Intent(this, PillsActivity.class);
        intent_pills.putExtra("seatNumber",seatNumber);
        //intent_pills.putExtra("count", count);
        startActivityForResult(intent_pills,1);
    }
    public void call_cabin_crew_bnt(View v){
        if(callCabin == "false"){
            docData.put("callCabinCrew", true);
        }else{
            docData.put("callCabinCrew", false);
        }

        if(!docData.containsKey("meal")){
            docData.put("meal", "no");
        }

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

        if(callCabin == "false") {
            Toast.makeText(getApplicationContext(), "승무원을 불렀습니다", Toast.LENGTH_LONG).show();
            databaseReference.child(seatNumber).child("call cabin crew").child("value").setValue("yes");
            callCabin = "true";
        }else{
            Toast.makeText(getApplicationContext(), "승무원을 부른걸 취소합니다", Toast.LENGTH_LONG).show();
            databaseReference.child(seatNumber).child("call cabin crew").child("value").setValue("no");
            callCabin = "false";
        }
    }

    public void stopActivity(){
        Intent intent_stop = new Intent(this, StopActivity.class);
        startActivity(intent_stop);
    }
/**
    public void setCountDocData(){
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

 **/
}

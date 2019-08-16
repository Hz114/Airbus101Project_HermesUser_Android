package com.example.hermes;

import android.content.Intent;
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

public class PillsActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Intent intent = null;

    private String seatNumber = null;
    private String pill = null;

    private String strCount;
    private int count;
    private Map<String, Object> docData = new HashMap<>();
    private Map<String, Object> adminDocData = new HashMap<>();

    private FirebaseFirestore firebaseStoreDB = FirebaseFirestore.getInstance();

    private String activityName = "pills";
    private List pills  = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills);

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

    public void paregoric_bnt(View v){
        Toast.makeText(getApplicationContext(),"지사제를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("paregoric");
        pills.add("paregoric");
        pill = "paregoric";
        setDocData();
    }
    public void digestive_bnt(View v){
        Toast.makeText(getApplicationContext(),"소화제를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("digestive");
        //pills.add("digestive");
        pill = "digestive";
        setDocData();
    }
    public void sedative_bnt(View v){
        Toast.makeText(getApplicationContext(),"진정제를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("sedative");
        //pills.add("sedative");
        pill = "sedative";
        setDocData();
    }
    public void fever_reducer_bnt(View v){
        Toast.makeText(getApplicationContext(),"해열제를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("fever reducer");
        //pills.add("fever reducer");
        pill = "fever reducer";
        setDocData();
    }
    public void painkiller_bnt(View v){
        Toast.makeText(getApplicationContext(),"진통제를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("painkiller");
        pills.add("painkiller");
        pill = "painkiller";
        setDocData();
    }
    public void ointment_bnt(View v){
        Toast.makeText(getApplicationContext(),"연고를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("ointment");
        pills.add("ointment");
        pill = "ointment";
        setDocData();
    }

    public void setDocData(){
        docData.put("order", pill);
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

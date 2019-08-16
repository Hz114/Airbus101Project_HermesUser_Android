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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeveragesActivity extends AppCompatActivity {

    //final int ITEM_SIZE = 6;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Intent intent = null;

    private String seatNumber = null;
    private String beverage = null;

    private String strCount;
    private int count;

    private Map<String, Object> docData = new HashMap<>();
    private Map<String, Object> adminDocData = new HashMap<>();

    private FirebaseFirestore firebaseStoreDB = FirebaseFirestore.getInstance();

    private String activityName = "beverages";
    private List beverages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);


        intent = getIntent();
        seatNumber = intent.getExtras().getString("seatNumber");
        //docData = (Map<String, Object>) intent.getExtras().getSerializable("docData");
        //count = intent.getExtras().getInt("count");

/*
        GridView gridView = (GridView) findViewById(R.id.gridview);

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item(R.drawable.airplane_img, "WATER","물을 주문하셨습니다", "beverage_water");
        item[1] = new Item(R.drawable.airplane_img, "GREEN TEA","녹차를 주문하셨습니다", "beverage_green_tea");
        item[2] = new Item(R.drawable.airplane_img, "COFFEE","커피를 주문하셨습니다", "beverage_coffee");
        item[3] = new Item(R.drawable.airplane_img, "JUICE","주스를 주문하셨습니다", "beverage_juice");
        item[4] = new Item(R.drawable.airplane_img, "WINE","와인을 주문하셨습니다", "beverage_wine");
        item[5] = new Item(R.drawable.airplane_img, "BEER","맥주를 주문하셨습니다", "beverage_beer");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }
*/
        //gridView.setAdapter(new GridAdapter(getApplicationContext(), items, R.layout.activity_beverages));
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

    public void water_bnt(View v){
        Toast.makeText(getApplicationContext(),"물을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("water");
        beverages.add("water");
        beverage = "water";
        setDocData();
    }
    public void green_tea_bnt(View v){
        Toast.makeText(getApplicationContext(),"녹차를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("green tea");
        beverages.add("green tea");
        beverage = "green tea";
        setDocData();
    }
    public void coffee_bnt(View v){
        Toast.makeText(getApplicationContext(),"커피를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("coffee");
        beverages.add("coffee");
        beverage = "coffee";
        setDocData();
    }
    public void juice_bnt(View v){
        Toast.makeText(getApplicationContext(),"주스를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("juice");
        beverages.add("juice");
        beverage = "juice";
        setDocData();
    }
    public void wine_bnt(View v){
        Toast.makeText(getApplicationContext(),"와인을 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("wine");
        beverages.add("wine");
        beverage = "wine";
        setDocData();
    }
    public void beer_bnt(View v){
        Toast.makeText(getApplicationContext(),"맥주를 주문하셨습니다", Toast.LENGTH_LONG).show();
        databaseReference.child(seatNumber).child(activityName).push().setValue("beer");
        beverages.add("beer");
        beverage = "beer";
        setDocData();
    }

    public void setDocData(){
        docData.put("order", beverage);
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

}

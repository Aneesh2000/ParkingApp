package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Details extends AppCompatActivity implements OnCarSelected, AdapterView.OnItemSelectedListener {

    Button txtCarSelected;
    private static final int COLUMNS =2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    int Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow);
        //setTitle(title);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        CollectionReference subjectsRef1 = fStore.collection("Spinner1");
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final List<String> subjects1 = new ArrayList<>();
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        subjectsRef1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("Day");
                        subjects1.add(subject);
                    }
                    adapter1.notifyDataSetChanged();
                }
            }
        });

        CollectionReference subjectsRef2 = fStore.collection("Spinner2");
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final List<String> subjects2 = new ArrayList<>();
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        subjectsRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("Time");
                        subjects2.add(subject);
                    }
                    adapter2.notifyDataSetChanged();
                }
            }
        });


        txtCarSelected = (Button) findViewById(R.id.txt_car_selected);


        List<CarItem> items = new ArrayList<>();
        for (int i=0; i<10; i++) {


                items.add(new NormalItem(String.valueOf(i)));

        }

        GridLayoutManager manager = new GridLayoutManager(this,COLUMNS);
        RecyclerView recyclerView = findViewById(R.id.mt_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);



        AirplaneAdapter adapter = new AirplaneAdapter(this, items);
        recyclerView.setAdapter(adapter);

        txtCarSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Count!=0){
                    Intent intent = new Intent(Details.this, QRGenerator.class);
                    String count1 = Integer.toString(Count);
                    intent.putExtra("Count", count1);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Select atleast one slot", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    @Override
    public void onCarSelected(int count) {
        Count = count;
        txtCarSelected.setText("Book "+count+" Car Slots");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        if(text == "Today" || text == "Tomorrow"){
            Context text1 = parent.getContext();
        }
        if(text == "8:30AM - 12:30PM" || text == "1:30PM -5:30PM"){
            Context text2 = parent.getContext();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

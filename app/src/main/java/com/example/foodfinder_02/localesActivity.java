package com.example.foodfinder_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodfinder_02.clases.LocalesAdapter;
import com.example.foodfinder_02.clases.locales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class localesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocalesAdapter localesAdapter;
    private List<locales> localesList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locales);

        recyclerView = findViewById(R.id.recyclerViewLocales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        localesAdapter = new LocalesAdapter(new ArrayList<>());
        recyclerView.setAdapter(localesAdapter);

        database = FirebaseDatabase.getInstance();
        // Obtén una referencia a la base de datos de Firebase
        databaseReference = database.getReference("locales");

        // Agrega un listener para escuchar cambios en la base de datos
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<locales> locales = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    locales local = snapshot.getValue(locales.class);
                    locales.add(local);
                }
                localesAdapter.setLocalList(locales);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja el error si es necesario
            }
        });
    }
}
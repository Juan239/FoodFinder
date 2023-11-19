package com.example.foodfinder_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class infoLocalActivity extends AppCompatActivity {
    private Button btnGuardarInfo;
    private EditText ETnombre, ETdescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_local);

        btnGuardarInfo = findViewById(R.id.btnGuardarInfoLocal);
        ETnombre = findViewById(R.id.ETnombreLocal);

        // Obtén la instancia de FirebaseAuth y usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            btnGuardarInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ETnombre.setText(userEmail);
                }
            });

        } else {
            startActivity(new Intent(infoLocalActivity.this, MenuPrincipal.class));
        }
    }
}
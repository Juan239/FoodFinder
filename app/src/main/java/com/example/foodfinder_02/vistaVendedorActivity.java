package com.example.foodfinder_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class vistaVendedorActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private CardView infoLocal, ubicacion, horario, cerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_vendedor);
        auth = FirebaseAuth.getInstance();
        //Una vez haga el ingreso de informacion, debo llamar a la base de datos desde aca y mostrar el nombre del local arriba

        infoLocal = findViewById(R.id.informacionLocal);
        ubicacion = findViewById(R.id.establecerUbicacion);
        horario = findViewById(R.id.establecerHorario);
        cerrarSesion = findViewById(R.id.cerrarSesion);

        infoLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irInfoLocal();
            }
        });

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irEstablecerUbicacion();
            }
        });

        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irEstablecerHorario();
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }

    private void irInfoLocal(){
        startActivity(new Intent(this, infoLocalActivity.class));
    }
    private void irEstablecerUbicacion(){
        startActivity(new Intent(this, establecerHorarioActivity.class));
    }
    private void irEstablecerHorario(){
        startActivity(new Intent(this, establecerHorarioActivity.class));
    }
    private void cerrarSesion(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MenuPrincipal.class));
    }
}
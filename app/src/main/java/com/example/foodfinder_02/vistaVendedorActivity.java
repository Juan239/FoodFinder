package com.example.foodfinder_02;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class vistaVendedorActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private CardView infoLocal, ubicacion, horario, cerrarSesion;
    private int contador = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contador++;
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
        // Eliminar la preferencia de autenticación
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("estaLogueado");
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MenuPrincipal.class));
    }

    @Override
    public void onBackPressed() {
        /*Supuestamente al eliminar el start activity se soluciona el problema de generar muchos activities al volver atras
          pero al menos la primera vez que lo probe al iniciar sesion por primera vez y querer volver atras se cerraba todo
          no afectaba al inicio de sesion, pero la idea es que vuelva al menu principal y no se cierre todo
         */
        startActivity(new Intent(vistaVendedorActivity.this, MenuPrincipal.class));
        finish();
    }

}
package com.example.foodfinder_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodfinder_02.clases.rUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrarActivity extends AppCompatActivity {
    Button btnRegistrar;
    EditText ETnombreLocal, ETcorreo, ETpassword;
    DatabaseReference reference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        btnRegistrar = findViewById(R.id.btnRegistro);
        ETnombreLocal = findViewById(R.id.registroNombreLocal);
        ETcorreo = findViewById(R.id.registroCorreoElectronico);
        ETpassword = findViewById(R.id.registroPassword);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Registro", "Inicio del registro");
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Usuarios");

                String nombreLocal = ETnombreLocal.getText().toString();
                String correo = ETcorreo.getText().toString();
                String password = ETpassword.getText().toString();

                rUsuario usuario = new rUsuario(nombreLocal, correo, password);
                reference.child(nombreLocal).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(registrarActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registrarActivity.this, vistaVendedorActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(registrarActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Guarda el usuario en la base de datos Firebase con el correo como clave


            }
        });
    }
}
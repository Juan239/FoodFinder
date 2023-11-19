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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrarActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    Button btnRegistrar;
    EditText ETcorreo, ETpassword;
    DatabaseReference reference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        auth = FirebaseAuth.getInstance();
        btnRegistrar = findViewById(R.id.btnRegistro);
        ETcorreo = findViewById(R.id.registroCorreoElectronico);
        ETpassword = findViewById(R.id.registroPassword);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Registro", "Inicio del registro");
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Usuarios");

                String correo = ETcorreo.getText().toString();
                String password = ETpassword.getText().toString();

                if(correo.isEmpty()){
                    ETcorreo.setError("El correo no puede estar en blanco");
                }
                if(password.isEmpty()){
                    ETpassword.setError("Escriba su contraseña");
                }
                else{
                    auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(registrarActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(registrarActivity.this, vistaVendedorActivity.class));
                            } else {
                                Toast.makeText(registrarActivity.this, "Registro fallido" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



                // Guarda el usuario en la base de datos Firebase con el correo como clave


            }
        });
    }
}
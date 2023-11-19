package com.example.foodfinder_02;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class vendedorFragment extends Fragment {
    private Button iniciar;
    private EditText ETnombreLocal, ETcontrasenia;
    private FirebaseAuth auth;
    private TextView registrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vendedor, container, false);
        auth = FirebaseAuth.getInstance();
        ///////////////////////////////////////////////////////////////////////////////////////////////7
        //Esto es para recibir el texto de los editText y verificarlo al presionar el boton
        ETnombreLocal = rootView.findViewById(R.id.nombreDelLocal);
        ETcontrasenia = rootView.findViewById(R.id.password);
        iniciar = rootView.findViewById(R.id.loginButton);
        registrar = rootView.findViewById(R.id.txtRegistrarse);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validarnombre() | !validarContrasenia()){

                }
                else{
                    String correo = ETnombreLocal.getText().toString();
                    String password = ETcontrasenia.getText().toString();

                    auth.signInWithEmailAndPassword(correo, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getActivity(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), vistaVendedorActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Hubo un error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), registrarActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }



    public boolean validarnombre(){
        String val = ETnombreLocal.getText().toString();
        if(val.isEmpty()){
            ETnombreLocal.setError("El correo no puede estar vacío");
            return false;
        }else{
            ETnombreLocal.setError(null);
            return true;
        }
    }

    public boolean validarContrasenia(){
        String val = ETcontrasenia.getText().toString();
        if(val.isEmpty()){
            ETcontrasenia.setError("La contraseña no puede estar vacia");
            return false;
        }else{
            ETcontrasenia.setError(null);
            return true;
        }
    }
}
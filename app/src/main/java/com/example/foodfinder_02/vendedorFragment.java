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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class vendedorFragment extends Fragment {
    Button iniciar;
    private EditText ETnombreLocal, ETcontrasenia;
    TextView registrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vendedor, container, false);

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
                    revisarUsuario();
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

    public void revisarUsuario(){
        String nombreLocal = ETnombreLocal.getText().toString().trim();
        String contrasenia = ETcontrasenia.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        //Esta parte debe cambiar, no deberia de tener que tomar el nombre de usuario, sino el correo
        Query chequearUsuario = reference.orderByChild("nombre").equalTo(nombreLocal);

        chequearUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //seguir con esto
                    ETnombreLocal.setError(null);
                    //El error ocurre aca, intenta buscar un "hijo" de correo electronico, pero en la base de datos el "padre" es el nombre de usuario
                    String contraseniaDesdeBD = snapshot.child(nombreLocal).child("contrasenia").getValue(String.class);
                    if( Objects.equals(contraseniaDesdeBD, contrasenia)){
                        ETcontrasenia.setError(null);
                        Intent intent2 = new Intent(getActivity(), vistaVendedorActivity.class);
                        startActivity(intent2);
                    }
                    else {
                        Toast.makeText(getActivity(), "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                        ETcontrasenia.requestFocus();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                    ETnombreLocal.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
package com.example.foodfinder_02;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class vendedorFragment extends Fragment {
    Button iniciar;
    EditText username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vendedor, container, false);

        ///////////////////////////////////////////////////////////////////////////////////////////////7
        //Esto es para recibir el texto de los editText y verificarlo al presionar el boton
        username = rootView.findViewById(R.id.username);
        iniciar = rootView.findViewById(R.id.loginButton);


        View.OnClickListener errorCorreo = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().equals("juan")){
                    username.setError("error");
                    Toast.makeText(getActivity(), username.getText().toString(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Sesion iniciada", Toast.LENGTH_LONG).show();
                }
            }
        };
        //
        iniciar.setOnClickListener(errorCorreo);

        ///////////////////////////////////////////////////////////////////////////

        return rootView;
    }
}
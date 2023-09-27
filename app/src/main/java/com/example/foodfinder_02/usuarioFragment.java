package com.example.foodfinder_02;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class usuarioFragment extends Fragment {

    CardView mapa;
    CardView locales;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_usuario, container, false);

        //Asignar el elemento xml a las variables
        mapa = rootView.findViewById(R.id.botonMapa);
        locales = rootView.findViewById(R.id.locales);

        //Crear los listener de los dos botones
        //Listener del mapa
        View.OnClickListener irAMapa = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        };

        //Listener de los locales
        //Este falta cambiarlo para que entre al activity de locales
        View.OnClickListener irALocales = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        };


        //Asignar a cada boton el listener correspondiente
        mapa.setOnClickListener(irAMapa);
        locales.setOnClickListener(irALocales);

        return rootView;
    }





}
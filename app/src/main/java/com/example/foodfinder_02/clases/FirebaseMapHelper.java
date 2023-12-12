package com.example.foodfinder_02.clases;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMapHelper {
    private Context context;
    private DatabaseReference databaseReference;
    private GoogleMap googleMap;
    private Map<String, Marker> markers = new HashMap<>();

    public FirebaseMapHelper(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("tu_nodo_en_firebase");
    }

    public void agregarMarcadoresAlMapa() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpiar marcadores existentes en el mapa si es necesario
                for (Marker marker : markers.values()) {
                    marker.remove();
                }
                markers.clear();

                // Iterar sobre los datos en Firebase y agregar marcadores al mapa
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtener la información de cada coordenada
                    Map<String, Object> coordenada = (Map<String, Object>) snapshot.getValue();

                    // Extraer la latitud y longitud
                    double latitud = Double.parseDouble(coordenada.get("latitud").toString());
                    double longitud = Double.parseDouble(coordenada.get("longitud").toString());

                    // Crear un LatLng object
                    LatLng ubicacion = new LatLng(latitud, longitud);

                    // Obtener otros datos personalizados
                    String id = snapshot.getKey();  // La clave del nodo puede ser útil como identificador único
                    String nombre = coordenada.get("nombre").toString();  // Personaliza según tus datos
                    String descripcion = coordenada.get("descripcion").toString();  // Personaliza según tus datos

                    // Añadir marcador al mapa
                    agregarMarcadorAlMapa(ubicacion, id, nombre, descripcion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores en la lectura de datos desde Firebase
                Toast.makeText(context, "Error al obtener datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarMarcadorAlMapa(LatLng ubicacion, String id, String nombre, String descripcion) {
        // Verificar si ya existe un marcador con la misma clave (id)
        if (markers.containsKey(id)) {
            // Actualizar la posición del marcador existente
            markers.get(id).setPosition(ubicacion);
        } else {
            // Crear un nuevo marcador
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(ubicacion)
                    .title(nombre)
                    .snippet(descripcion);

            Marker marker = googleMap.addMarker(markerOptions);
            markers.put(id, marker);
        }
    }
}

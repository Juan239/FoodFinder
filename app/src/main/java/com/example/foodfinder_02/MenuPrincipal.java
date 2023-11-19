package com.example.foodfinder_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.foodfinder_02.databinding.ActivityMainBinding;
import com.example.foodfinder_02.databinding.ActivityMenuPrincipalBinding;

public class MenuPrincipal extends AppCompatActivity {
    ActivityMenuPrincipalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        // Verificar el estado de autenticación
        boolean isLoggedIn = sharedPreferences.getBoolean("estaLogueado", false);

        replaceFragment(new usuarioFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.irMapa) {
                replaceFragment(new usuarioFragment());
            } else if (itemId == R.id.vendedor) {
                if(isLoggedIn){
                    startActivity(new Intent(MenuPrincipal.this, vistaVendedorActivity.class));
                    finish();
                }else{
                    replaceFragment(new vendedorFragment());
                }
            } else if (itemId == R.id.ayuda) {
                replaceFragment(new ayudaFragment());
            }
            return true;
        });
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
package com.example.foodfinder_02.clases;

public class rUsuario {
    private String nombre;
    private String correo;
    private String contrasenia;

    public rUsuario() {
        // Constructor vacío requerido por Firebase
    }

    public rUsuario(String nombre, String correo, String contrasenia) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}

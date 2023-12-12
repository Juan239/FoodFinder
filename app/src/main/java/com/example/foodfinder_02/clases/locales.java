package com.example.foodfinder_02.clases;

public class locales {
    private String id;
    private static String nombre;
    private static String descripcion;
    private String imageUrl;

    public locales(){

    }

    public locales(String id, String nombre, String descripcion, String imageUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

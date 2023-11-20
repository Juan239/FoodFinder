package com.example.foodfinder_02.clases;

public class dataClass {
    private String imageURL, nombreLocal, descripcionLocal;

    public dataClass(){

    }
    public dataClass(String nombreLocal, String descripcionLocal){
        this.nombreLocal = nombreLocal;
        this.descripcionLocal = descripcionLocal;
    }
    public dataClass(String imageURL, String nombreLocal, String descripcionLocal){
        this.imageURL = imageURL;
        this.nombreLocal = nombreLocal;
        this.descripcionLocal = descripcionLocal;
    }
    public String getImageURL(){
        return imageURL;
    }

    public String getNombreLocal(){
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public String getDescripcionLocal() {
        return descripcionLocal;
    }

    public void setDescripcionLocal(String descripcionLocal) {
        this.descripcionLocal = descripcionLocal;
    }
}

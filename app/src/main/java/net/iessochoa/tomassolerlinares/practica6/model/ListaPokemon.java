package net.iessochoa.tomassolerlinares.practica6.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListaPokemon {
    //le cambiamos el nombre original del JSON
    @SerializedName("next")
    private String uriSiguientes;
    @SerializedName("results")
    private ArrayList<Pokemon> listaPokemon;

    public String getUriSiguientes() {
        return uriSiguientes;
    }

    public void setUriSiguientes(String uriSiguientes) {
        this.uriSiguientes = uriSiguientes;
    }

    public ArrayList<Pokemon> getListaPokemon() {
        return listaPokemon;
    }

    public void setListaPokemon(ArrayList<Pokemon> listaPokemon) {
        this.listaPokemon = listaPokemon;
    }
}
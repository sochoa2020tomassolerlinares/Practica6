package net.iessochoa.tomassolerlinares.practica6.network;

import net.iessochoa.tomassolerlinares.practica6.model.ListaPokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WebServicePokeApi {
    //llamada con parámetros
    @GET("pokemon")
    Call<ListaPokemon> getListaPokemon(@Query("limit") int limit, @Query("offset")
            int offset);
    //llamada que permite recibir un observable de RxJava
    /* @GET("pokemon")
    Observable<ListaPokemon> getListaPokemon(@Query("limit") int limit, @Query("offset") int offset);*/
    //llamada que devuelve el primer listado de pokemon
    @GET("pokemon")
    Call<ListaPokemon> getListaPokemon();
    //llamada que permite realizar una petición con un url completa. Utilizaremos esta opción para recibir los siguientes
    @GET
    Call<ListaPokemon> getListaPokemon(@Url String siguientes);
}
package net.iessochoa.tomassolerlinares.practica6.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.iessochoa.tomassolerlinares.practica6.model.ListaPokemon;
import net.iessochoa.tomassolerlinares.practica6.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static final String TAG = "";
    //implementamos Singleton
    private static volatile NetworkService INSTANCE;
    //objeto retrofit que realiza las tareas del servicio web
    private Retrofit retrofit;
    //contiene los métodos del CRUD del servicio. En nuestro caso sólo una consulta
    private WebServicePokeApi servicioWebPokemon;
    //lista de pokemon que va recuperando. Los pokemon los traemos de 20 en 20 y los añadimos al final de esta lista
    private ArrayList<Pokemon> listaPokemonApi;
    //El objeto mutableLiveData, permite mantener el observer en la UI de usuario cuando se modifique la lista
    //y actualizar el recyclerView
    private MutableLiveData<List<Pokemon>> listaPokemonApiLiveData;
    //lista de pokemon que traemos de Servicio Web en cada llamada
    ListaPokemon pokemonRespuestaApi;

    //Singleton
    public static NetworkService getInstance() {
        if (INSTANCE == null) {
            synchronized (NetworkService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetworkService();
                }
            }
        }
        return INSTANCE;
    }

    private NetworkService() {
        iniciaRetrofit();
        //la lista inicial se encuentra vacía
        listaPokemonApi = new ArrayList<Pokemon>();
        listaPokemonApiLiveData = new MutableLiveData<>();
    }

    private void iniciaRetrofit() {
        //iniciamos retrofit
        retrofit = new Retrofit.Builder()
                //base del servicio web
                .baseUrl("https://pokeapi.co/api/v2/")
                //conversor de JSON, existen otros(XML...)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //creamos el servicio web desde la interface creada anteriormente
        servicioWebPokemon = retrofit.create(WebServicePokeApi.class);
        //traemos los 20 primeros pokemon
        getSiguientesPokemonsApi();
    }

    /**
     * Este método nos devuelve la lista de pokemon actualizada con los
     * siguientes pokemon desde offset
     * desde qué indice de pokemon queremos traer los pokemon(ejemplo: desde el
     * pokemon 80 del servicio)
     */
    public void getSiguientesPokemonsApi() {
        //creamos la llamada al servicio con los siguientes 20 pokemon desde el indice offset
        Call<ListaPokemon> listaPokemonApiCall;
        if (pokemonRespuestaApi == null)//es la primera llamada con la url basica
            http://pokeapi.co/api/v2/pokemon
                    listaPokemonApiCall = servicioWebPokemon.getListaPokemon();
        else
            //es siguiente llamada, le pasamos la url. Por ejemplo:
            https://pokeapi.co/api/v2/pokemon?offset=120&limit=20

                    listaPokemonApiCall = servicioWebPokemon.getListaPokemon(pokemonRespuestaApi.getUriSiguientes());
        //hacemos la llamada
        listaPokemonApiCall.enqueue(new Callback<ListaPokemon>() {
            //si recibe correctamente
            @Override
            public void onResponse(Call<ListaPokemon> call, Response<ListaPokemon> response) {
                if (response.isSuccessful()) {
                    //obtenemos el objeto creado a partir del JSON
                    pokemonRespuestaApi = response.body();
                    Log.d(TAG, "Pokemons desde offset: " + pokemonRespuestaApi.getUriSiguientes());
                    //añadimos al final los nuevos pokemon
                    listaPokemonApi.addAll(pokemonRespuestaApi.getListaPokemon());
                    //actualizamos el LiveData para que reaccione el observador
                    listaPokemonApiLiveData.setValue(listaPokemonApi);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<ListaPokemon> call, Throwable t) {
                Log.e(TAG, "onFailure:" + t.getMessage());
                // cargandoDatos.setValue(false);
            }
        });
    }

    public LiveData<List<Pokemon>> getListaPokemonApi() {
        //si no está abierto retrofit, es la primera vez, por lo que inicializamos variables
        //como el repositorio tambien mantiene la base de datos, evitamos abrir retrofit si no lo pide
        //el usuario. Si el acceso al servicio web es fundamental en la app se puede abrir retorofit en el constructor //en
        if (retrofit == null) {
            iniciaRetrofit();
        }
        return listaPokemonApiLiveData;
    }
}

package net.iessochoa.tomassolerlinares.practica6.ui.favoritos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.iessochoa.tomassolerlinares.practica6.model.Pokemon;
import net.iessochoa.tomassolerlinares.practica6.repository.PokemonRepository;

import java.util.List;

/**
 * Clase viewmodel de favoritos encargada de definir las funciones del fragment favoritos de cara al usuario
 */
public class FavoritosViewModel extends AndroidViewModel {
    private PokemonRepository mRepository;
    private LiveData<List<Pokemon>> mAllPokemons;

    //Constructor del viewmodel
    public FavoritosViewModel(@NonNull Application application) {
        super(application);
        mRepository = PokemonRepository.getInstance(application);
        //Recuperamos el LiveData de todos los pokemons
        mAllPokemons = mRepository.getFavoritPokemons();
    }

    //Método que devuelve todos los pokemons de favoritos
    public LiveData<List<Pokemon>> getAllPokemons() {
        return mAllPokemons;
    }

    //Método que devuelve todos los pokemons de favoritos en formato List
    public List<Pokemon> getAllPokemonsList(){
        return mAllPokemons.getValue();
    }

    //Método para insertar un nuevo pokemon
    public void insert(Pokemon pokemon) {
        mRepository.insert(pokemon);
    }

    //Método para eliminar un pokemon de favoritos
    public void delete(Pokemon pokemon) {
        mRepository.delete(pokemon);
    }
}
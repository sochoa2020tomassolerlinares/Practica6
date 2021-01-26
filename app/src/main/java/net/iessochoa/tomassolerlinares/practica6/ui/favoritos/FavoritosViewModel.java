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

public class FavoritosViewModel extends AndroidViewModel {
    private PokemonRepository mRepository;
    private LiveData<List<Pokemon>> mAllPokemons;

    public FavoritosViewModel(@NonNull Application application) {
        super(application);
        mRepository = PokemonRepository.getInstance(application);
        //Recuperamos el LiveData de todos los pokemons
        mAllPokemons = mRepository.getFavoritPokemons();
    }

    public LiveData<List<Pokemon>> getAllPokemons() {
        return mAllPokemons;
    }

    public void insert(Pokemon pokemon) {
        mRepository.insert(pokemon);
    }

    public void delete(Pokemon pokemon) {
        mRepository.delete(pokemon);
    }
}
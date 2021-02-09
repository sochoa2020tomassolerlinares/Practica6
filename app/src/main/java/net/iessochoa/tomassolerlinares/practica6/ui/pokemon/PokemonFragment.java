package net.iessochoa.tomassolerlinares.practica6.ui.pokemon;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.iessochoa.tomassolerlinares.practica6.R;
import net.iessochoa.tomassolerlinares.practica6.model.Pokemon;
import net.iessochoa.tomassolerlinares.practica6.ui.VerPokemonFragment;
import net.iessochoa.tomassolerlinares.practica6.ui.adapters.PokemonAdapter;
import net.iessochoa.tomassolerlinares.practica6.ui.favoritos.FavoritosViewModel;

import java.util.Date;
import java.util.List;

public class PokemonFragment extends Fragment {
    private RecyclerView rvPokemons;
    private PokemonAdapter adapter;
    private PokemonViewModel pokemonViewModel;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pokemons, container, false);
        rvPokemons = root.findViewById(R.id.rvPokemons);
        progressBar = root.findViewById(R.id.progressBar);
        defineDetectarFinRecycler();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        adapter = new PokemonAdapter();
        rvPokemons.setHasFixedSize(true);
        rvPokemons.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPokemons.setAdapter(adapter);

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokemonViewModel.getAllPokemons().observe(getViewLifecycleOwner(), new
                Observer<List<Pokemon>>() {
                    @Override
                    public void onChanged(List<Pokemon> pokemons) {
                        adapter.setListaPokemon(pokemonViewModel.getAllPokemonsList());
                        progressBar.setVisibility(View.INVISIBLE);
                        adapter.notifyDataSetChanged();
                    }
                });
        adapter.setOnItemPokemonClickListener(pokemon -> {
            //creamos bundle para pasar el pokemon al fragment ver_pokemon
            Bundle argumentosBundle = new Bundle();
            argumentosBundle.putParcelable(VerPokemonFragment.ARG_POKEMON, pokemon);
            //llamamos a la acción con el id del Navigation y el bundle

            NavHostFragment.findNavController(PokemonFragment.this).navigate(R.id.ver_pokemon, argumentosBundle);
        });

        definirEventoSwiper();


    }

    public void onChanged(@Nullable List<Pokemon> listaPokemon) {
        adapter.setListaPokemon(listaPokemon);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void definirEventoSwiper() {
        //Creamos el Evento de Swiper
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int
                            swipeDir) {
                        //realizamos un cast del viewHolder y obtenemos el pokemon a
                        // borrar
                        // PokemonListaPokemon
                        PokemonAdapter.PokemonViewHolder vhPokemon = (PokemonAdapter.PokemonViewHolder) viewHolder;
                        Pokemon pokemon = vhPokemon.getPokemon();
                        insertPokemon(pokemon, vhPokemon.getAdapterPosition());
                    }
                };
        //Creamos el objeto de ItemTouchHelper que se encargará del trabajo
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        //lo asociamos a nuestro reciclerView
        itemTouchHelper.attachToRecyclerView(rvPokemons);
    }

    private void insertPokemon(Pokemon pokemon, int posicion) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle(R.string.aviso);
        dialogo.setMessage(R.string.avisoAdd);
        dialogo.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyItemChanged(posicion);//recuperamos la posición
                    }
                });
        dialogo.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Borramos
                        pokemon.setFechaCompra(new Date());
                        pokemonViewModel.insert(pokemon);
                        adapter.notifyItemChanged(posicion); //para que lo vuelva a mostrar
                    }
                });
        dialogo.show();
    }

    private void defineDetectarFinRecycler() {
        rvPokemons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //de esta forma sabemos si llega al final
                if ((!recyclerView.canScrollVertically(1)) && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    Log.v("scroll", "fin recyclerview");
                    progressBar.setVisibility(View.VISIBLE);
                    //recuperamos los 20 siguientes pokemon eso hara que se active el observador
                    pokemonViewModel.getNextPokemon();
                }
            }
        });
    }
}
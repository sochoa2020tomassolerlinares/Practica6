package net.iessochoa.tomassolerlinares.practica6.ui.favoritos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import net.iessochoa.tomassolerlinares.practica6.ui.pokemon.PokemonFragment;

import java.util.List;

public class FavoritosFragment extends Fragment {
    private FavoritosViewModel favoritosViewModel;
    private RecyclerView rvPokemons;
    private PokemonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);
        rvPokemons = root.findViewById(R.id.rvPokemons);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritosViewModel = new ViewModelProvider(this).get(FavoritosViewModel.class);
        adapter = new PokemonAdapter();
        rvPokemons.setHasFixedSize(true);
        rvPokemons.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPokemons.setAdapter(adapter);

        favoritosViewModel = new ViewModelProvider(this).get(FavoritosViewModel.class);
        favoritosViewModel.getAllPokemons().observe(getViewLifecycleOwner(), new
                Observer<List<Pokemon>>() {
                    @Override
                    public void onChanged(List<Pokemon> pokemons) {
                        adapter.setListaPokemon(favoritosViewModel.getAllPokemonsList());
                        adapter.notifyDataSetChanged();
                    }
                });

        definirEventoSwiper();

        adapter.setOnItemPokemonClickListener(pokemon -> {
            //creamos bundle para pasar el pokemon al fragment ver_pokemon
            Bundle argumentosBundle = new Bundle();
            argumentosBundle.putParcelable(VerPokemonFragment.ARG_POKEMON, pokemon);
            //llamamos a la acción con el id del Navigation y el bundle

            NavHostFragment.findNavController(FavoritosFragment.this).navigate(R.id.ver_favorito, argumentosBundle);
        });
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
                        borrarPokemon(pokemon, vhPokemon.getAdapterPosition());
                    }
                };
        //Creamos el objeto de ItemTouchHelper que se encargará del trabajo
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        //lo asociamos a nuestro reciclerView
        itemTouchHelper.attachToRecyclerView(rvPokemons);
    }

    private void borrarPokemon(Pokemon pokemon, int posicion) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle(R.string.aviso);
        dialogo.setMessage(R.string.avisoBorrar);
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
                        favoritosViewModel.delete(pokemon);
                    }
                });
        dialogo.show();
    }
}

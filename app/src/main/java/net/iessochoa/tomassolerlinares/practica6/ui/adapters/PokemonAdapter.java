package net.iessochoa.tomassolerlinares.practica6.ui.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.iessochoa.tomassolerlinares.practica6.R;
import net.iessochoa.tomassolerlinares.practica6.model.Pokemon;
import net.iessochoa.tomassolerlinares.practica6.utils.Utils;

import java.util.List;

/**
 * Clase encargada de detectar las llamadas del usuario a sus respectivas funciones
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> listaPokemon;

    private OnItemPokemonClickListener listener;

    //Método que se inicia cuando se crea el viewHolder
    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                                       parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,
                parent, false);
        return new PokemonViewHolder(itemView);
    }

    //Método que se lanza cuando se detecta el viewHolder y establece los datos en sus contenedores respectivos
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        if (listaPokemon != null) {
            //mostramos los valores en el cardview
            final Pokemon pokemon = listaPokemon.get(position);
            holder.tvNombrePkm.setText(pokemon.getNombre().toUpperCase());
            //comprobamos si tenemos fecha por si no es de base de datos. Ya que utilizamos la misma clase
            holder.tvFechaPkm.setText(pokemon.getFechaPkmFormatoLocal());
            //utilizamos Glide para mostrar la imagen
            Utils.cargaImagen(holder.ivPokemon, pokemon.getUrlImagen());
            //guardamos el pokemon actual
            holder.pokemon = pokemon;
        }
    }

    //Devuelve el tamaño de la lista de pokemons
    @Override
    public int getItemCount() {
        if (listaPokemon != null)
            return listaPokemon.size();
        else return 0;
    }

    //Clase encargada de declarar el viewGolder del adapter
    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombrePkm;
        private ImageView ivPokemon;
        private TextView tvFechaPkm;
        private CardView cvPokemon;
        //private ImageView ivBorrar;
        private Pokemon pokemon;

        //Constructor del viewHolder
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePkm = itemView.findViewById(R.id.tvNombrePkm);
            tvFechaPkm = itemView.findViewById(R.id.tvFechaPkm);
            ivPokemon = itemView.findViewById(R.id.ivPokemon);
            cvPokemon = itemView.findViewById(R.id.cvItem);

            cvPokemon.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemPokemonClick(listaPokemon.get( PokemonViewHolder.this.getAdapterPosition()));
                }
            });
        }
        //Devuelve un pokemon seleccionado
        public Pokemon getPokemon() {
            return pokemon;
        }

    }

    //Almacena una lista de pokemon en el adapter
    public void setListaPokemon(List<Pokemon> pokemons) {
        listaPokemon = pokemons;
        notifyDataSetChanged();
    }

    //Interfaz que declara el evento click Listener
    public interface OnItemPokemonClickListener {
        void onItemPokemonClick(Pokemon pokemon);
    }

    //Método que llama al listener
    public void setOnItemPokemonClickListener(OnItemPokemonClickListener pokemonClickListener) {
        this.listener = pokemonClickListener;
    }

}
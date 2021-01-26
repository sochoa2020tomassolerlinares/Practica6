package net.iessochoa.tomassolerlinares.practica6.ui.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import net.iessochoa.tomassolerlinares.practica6.R;
import net.iessochoa.tomassolerlinares.practica6.model.Pokemon;
import net.iessochoa.tomassolerlinares.practica6.utils.Utils;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> listaPokemon;

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                                       parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,
                        parent, false);
        return new PokemonViewHolder(itemView);
    }

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
            Utils.cargaImagen(holder.ivPokemon,pokemon.getUrl());
//guardamos el pokemon actual
            holder.pokemon=pokemon;
        }
    }

    @Override
    public int getItemCount() {
        if (listaPokemon != null)
            return listaPokemon.size();
        else return 0;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombrePkm;
        private ImageView ivPokemon;
        private TextView tvFechaPkm;
        //private ImageView ivBorrar;
        private Pokemon pokemon;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePkm = itemView.findViewById(R.id.tvNombrePkm);
            tvFechaPkm = itemView.findViewById(R.id.tvFechaPkm);
            ivPokemon = itemView.findViewById(R.id.ivPokemon);
        }

        public Pokemon getPokemon() {
            return pokemon;
        }

    }

    public void setListaPokemon(List<Pokemon> pokemons){
        listaPokemon=pokemons;
        notifyDataSetChanged();
    }

}
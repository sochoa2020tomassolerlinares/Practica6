package net.iessochoa.tomassolerlinares.practica6.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iessochoa.tomassolerlinares.practica6.R;
import net.iessochoa.tomassolerlinares.practica6.model.Pokemon;
import net.iessochoa.tomassolerlinares.practica6.utils.Utils;


public class VerPokemonFragment extends Fragment {

    public static final String ARG_POKEMON = "VerPokemonFragment.pokemon";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvNombrePokemon;
        ImageView ivPokemon;
        Pokemon pokemon;

        //ten en cuenta los nombre que hayas elegido
        tvNombrePokemon = view.findViewById(R.id.tvNombrePokemon);
        ivPokemon = view.findViewById(R.id.ivPokemonVer);
        //recuperamos el pokemos y lo mostramos
        pokemon=getArguments().getParcelable(ARG_POKEMON);
        tvNombrePokemon.setText(pokemon.getNombre().toUpperCase());
        //mostramos la imagen con Glide
        Utils.cargaImagen(ivPokemon,pokemon.getUrlImagen());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_pokemon, container, false);
    }
}
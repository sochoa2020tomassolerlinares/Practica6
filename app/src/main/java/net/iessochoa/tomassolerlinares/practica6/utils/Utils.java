package net.iessochoa.tomassolerlinares.practica6.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utils {
    public static void cargaImagen(ImageView ivImagen, String uri) {
        Glide
                .with(ivImagen.getContext())
                //url de la imagen
                .load(uri)
                //centramos la imagen
                // .centerCrop()
                //mientras se carga la imagen que imagen queremos mostrar
                .placeholder(android.R.drawable.stat_notify_sync_noanim)
                //Imagen que mostramos si hay error
                .error(android.R.drawable.ic_lock_lock)
                //donde colocamos la imagen
                .into(ivImagen);
    }
}
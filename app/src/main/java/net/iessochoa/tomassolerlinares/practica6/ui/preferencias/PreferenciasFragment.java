package net.iessochoa.tomassolerlinares.practica6.ui.preferencias;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import net.iessochoa.tomassolerlinares.practica6.R;

public class PreferenciasFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencias, rootKey);
    }
}
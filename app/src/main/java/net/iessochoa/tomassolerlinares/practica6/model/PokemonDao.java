package net.iessochoa.tomassolerlinares.practica6.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Interfaz DAO encargada de definir las sentencias SQL de los objetos pokemon
 */
@Dao
public interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pokemon pokemon);

    @Delete
    void deleteByPokemon(Pokemon pokemon);

    @Query("DELETE FROM " + Pokemon.TABLE_NAME)
    void deleteAll();

    @Update
    void update(Pokemon pokemon);

    @Query("SELECT * FROM "+Pokemon.TABLE_NAME+" ORDER BY "+Pokemon.NOMBRE)
    LiveData<List<Pokemon>> allPokemon();

}

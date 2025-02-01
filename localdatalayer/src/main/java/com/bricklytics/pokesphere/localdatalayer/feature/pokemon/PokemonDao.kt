package com.bricklytics.pokesphere.localdatalayer.feature.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemonList: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Query("""
        UPDATE PokemonEntity 
        SET id = :id, urlDefault = :urlDefault, urlShinny = :urlShinny, favorite = :favorite 
        WHERE name == :name
    """)
    suspend fun updatePokemon(
        name: String,
        id: Int,
        urlDefault: String,
        urlShinny: String,
        favorite: Boolean
    )

    @Query("UPDATE PokemonEntity SET favorite = 0 WHERE favorite == 1")
    suspend fun clearOldFavoritePokemon()

    @Query("UPDATE PokemonEntity SET favorite = 1 WHERE name == :name")
    suspend fun setFavoritePokemon(name: String)

    @Query("SELECT * FROM PokemonEntity WHERE favorite == 1")
    suspend fun getFavoritePokemon(): PokemonEntity

    @Query("SELECT * FROM PokemonEntity WHERE page = :page ORDER BY id ASC")
    suspend fun getPokemonList(page: Int): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE name == :name and (urlDefault !='' or urlShinny !='')")
    suspend fun getPokemon(name: String): PokemonEntity?
}
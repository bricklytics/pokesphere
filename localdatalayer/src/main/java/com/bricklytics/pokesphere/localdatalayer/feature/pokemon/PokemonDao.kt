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

    @Query("UPDATE PokemonEntity SET favorite = 0 WHERE favorite == 1")
    fun clearOldFavoritePokemon()

    @Query("UPDATE PokemonEntity SET favorite = :favorite WHERE name == :name")
    suspend fun setFavoritePokemon(name: String, favorite: Boolean)

    @Query("SELECT * FROM PokemonEntity WHERE favorite == 1")
    suspend fun getFavoritePokemon(): PokemonEntity

    @Query("SELECT * FROM PokemonEntity WHERE page = :page and id < :page")
    suspend fun getPokemonList(page: Int): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE name == :name and (urlDefault !='' or urlShinny !='')")
    suspend fun getPokemon(name: String): PokemonEntity?
}
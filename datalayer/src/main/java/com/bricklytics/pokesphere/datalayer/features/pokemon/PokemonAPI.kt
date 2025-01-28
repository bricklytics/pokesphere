package com.bricklytics.pokesphere.datalayer.features.pokemon

import com.bricklytics.pokesphere.datalayer.features.pokemon.model.PokemonDTO
import com.bricklytics.pokesphere.datalayer.features.pokemon.model.PokemonsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {
    @GET("/api/v2/pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String
    ): Response<PokemonDTO>

    @GET("/api/v2/pokemon/")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ) : Response<PokemonsDTO>
}
package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonOfficialArtworkModel
import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("abilities")
    val abilities: List<PokemonAbilityDTO>?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("sprites")
    val sprite: PokemonSpriteDTO?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("types")
    val types: List<PokemonTypeDTO>?,
    @SerializedName("stats")
    val stats: List<PokemonStatDTO>?,
    @SerializedName("moves")
    val moves: List<PokemonMoveDTO>?
) {
    fun mapTo() = PokemonModel(
        id = id ?: 0,
        name = name.orEmpty(),
        abilities = abilities.orEmpty().map{ it.mapTo() },
        baseExperience = baseExperience ?: 0,
        officialArtworkModel = sprite?.otherSprites?.officialArtwork?.mapTo()
            ?: PokemonOfficialArtworkModel(),
        height = height?.div(10.0) ?: 0.0,
        weight = weight?.div(10.0) ?: 0.0,
        types = types.orEmpty().map { it.mapTo() },
        stats = stats.orEmpty().map { it.mapTo() },
        moves = moves.orEmpty().map { it.mapTo() }
    )
}
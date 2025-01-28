package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonOfficialArtworkModel
import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("order")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("abilities")
    val abilities: List<PokemonAbilityDTO>?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("sprites")
    val sprite: PokemonSpriteDTO?
) {
    fun mapTo() = PokemonModel(
        id = id ?: 0,
        name = name.orEmpty(),
        abilities = abilities.orEmpty().map{ it.mapTo() },
        baseExperience = baseExperience ?: 0,
        officialArtworkModel = sprite?.otherSprites?.officialArtwork?.mapTo()
            ?: PokemonOfficialArtworkModel()
    )
}
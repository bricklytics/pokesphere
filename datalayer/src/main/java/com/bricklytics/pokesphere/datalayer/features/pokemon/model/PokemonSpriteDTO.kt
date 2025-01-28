package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonOfficialArtworkModel
import com.google.gson.annotations.SerializedName

data class PokemonSpriteDTO(
    @SerializedName("other")
    val otherSprites: PokemonOtherSpritesDTO?
)

data class PokemonOtherSpritesDTO(
    @SerializedName("official-artwork")
    val officialArtwork: PokemonOfficialArtworkDTO?
)

data class PokemonOfficialArtworkDTO(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?
) {
    fun mapTo() : PokemonOfficialArtworkModel {
        return PokemonOfficialArtworkModel(
            frontDefault = frontDefault.orEmpty(),
            frontShiny = frontShiny.orEmpty()
        )
    }
}
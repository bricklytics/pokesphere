package com.bricklytics.pokesphere.localdatalayer.feature.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonOfficialArtworkModel


@Entity
data class PokemonEntity(
    var page: Int = 0,
    @PrimaryKey val name: String,
    val id: Int,
    val baseExperience: Int,
    val urlDefault: String,
    val urlShinny: String,
    val favorite: Boolean = false
)

fun PokemonModel.asEntity(): PokemonEntity {
    return PokemonEntity(
        id = this.id,
        page = this.page,
        name = this.name,
        baseExperience = this.baseExperience,
        urlDefault = this.officialArtworkModel.frontDefault,
        urlShinny = this.officialArtworkModel.frontShiny
    )
}

fun PokemonEntity?.asDomain(): PokemonModel? {
    if (this == null || this.name.isBlank()) return null

    return PokemonModel(
        id = this.id,
        name = this.name,
        officialArtworkModel = PokemonOfficialArtworkModel(
            frontDefault = this.urlDefault,
            frontShiny = this.urlShinny
        ),
        baseExperience = this.baseExperience,
        isFavorite = this.favorite,
    )
}
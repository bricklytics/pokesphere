package com.bricklytics.pokesphere.uilayer.features.pokemondetails.model

import com.bricklytics.pokesphere.uilayer.R

enum class PokemonTypes(
    val label: String,
    val color: Int
) {
    Normal("normal", R.color.pokemon_type_normal),
    Fighting("fighting", R.color.pokemon_type_fighting),
    Flying("flying", R.color.pokemon_type_flying),
    Poison("poison", R.color.pokemon_type_poison),
    Ground("ground", R.color.pokemon_type_ground),
    Rock("rock", R.color.pokemon_type_rock),
    Bug("bug", R.color.pokemon_type_bug),
    Ghost("ghost", R.color.pokemon_type_ghost),
    Steel("steel", R.color.pokemon_type_steel),
    Fire("fire", R.color.pokemon_type_fire),
    Water("water", R.color.pokemon_type_water),
    Grass("grass", R.color.pokemon_type_grass),
    Electric("electric", R.color.pokemon_type_electric),
    Psychic("psychic", R.color.pokemon_type_psychic),
    Ice("ice", R.color.pokemon_type_ice),
    Dragon("dragon", R.color.pokemon_type_dragon),
    Dark("dark", R.color.pokemon_type_dark),
    Fairy("fairy", R.color.pokemon_type_fairy),
    Unknown("", R.color.pokemon_type_unknown);

    companion object {
        fun fromLabel(label: String): PokemonTypes {
            return entries.firstOrNull { it.label == label } ?: Unknown
        }
    }
}

fun String.colorPokemonTypeResource(): Int {
    return PokemonTypes.fromLabel(this).color
}
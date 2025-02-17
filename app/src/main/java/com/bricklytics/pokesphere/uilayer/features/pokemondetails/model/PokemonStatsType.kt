package com.bricklytics.pokesphere.uilayer.features.pokemondetails.model

import com.bricklytics.pokesphere.uilayer.R

enum class PokemonStatsType(
    val label: String,
    val color: Int
) {
    Hp("hp", R.color.health_points),
    Xp("xp", R.color.experience_points),
    Attack("attack", R.color.attack_points),
    Defense("defense", R.color.defense_points),
    SpecialAttack("special-attack", R.color.special_attack_points),
    SpecialDefense("special-defense", R.color.special_defense_points),
    Speed("speed", R.color.speed_points);

    companion object {
        fun fromLabel(label: String): PokemonStatsType {
            return entries.first { it.label == label }
        }
    }
}

fun String.colorPokemonStatsResource(): Int {
    return PokemonStatsType.fromLabel(this).color
}
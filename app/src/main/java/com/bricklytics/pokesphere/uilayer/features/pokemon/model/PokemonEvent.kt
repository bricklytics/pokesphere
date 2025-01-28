package com.bricklytics.pokesphere.uilayer.features.pokemon.model

sealed class PokemonEvent {
    data object OnDismissBottomSheet : PokemonEvent()
    data object OnDrainedList : PokemonEvent()
}

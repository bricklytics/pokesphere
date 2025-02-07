package com.bricklytics.pokesphere.uilayer.features.pokemon.model

sealed class PokemonEvent {
    data object OnBackPressed : PokemonEvent()
    data object OnDismissBottomSheet : PokemonEvent()
    data object OnDrainedList : PokemonEvent()

    data class OnLongPressCard(
        val index: Int
    ) : PokemonEvent()
}

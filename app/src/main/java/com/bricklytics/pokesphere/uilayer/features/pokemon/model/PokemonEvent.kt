package com.bricklytics.pokesphere.uilayer.features.pokemon.model

sealed interface PokemonEvent {
    data object OnBackPressed : PokemonEvent
    data object OnDismissBottomSheet : PokemonEvent
    data object OnDrainedList : PokemonEvent

    data class OnTapPokeCard(
        val name: String,
        val scrollPosition: Int = 0,
        val scrollOffset: Int = 0
    ) : PokemonEvent

    data class OnLongPressCard(
        val index: Int,
        val isFavorite: Boolean
    ) : PokemonEvent

    data class OnDoubleTapPokeCard(
        val index: Int,
        val isShinny: Boolean
    ) : PokemonEvent
}

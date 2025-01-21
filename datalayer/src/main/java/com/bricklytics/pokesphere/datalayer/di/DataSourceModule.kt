package com.bricklytics.pokesphere.datalayer.di

import com.bricklytics.pokesphere.datalayer.base.BaseApiDataSource
import com.bricklytics.pokesphere.datalayer.features.ability.AbilityAPI
import com.bricklytics.pokesphere.datalayer.features.ability.AbilityApiDataSource
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonAPI
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class DataSourceModule {

    @Provides
    fun providePokemonDataSource(
        pokemonAPI: PokemonAPI
    ): BaseApiDataSource = PokemonApiDataSource(pokemonAPI)

    @Provides
    fun provideAbilityDataSource(
        abilityAPI: AbilityAPI
    ): BaseApiDataSource = AbilityApiDataSource(abilityAPI)
}
package com.bricklytics.pokesphere.datalayer.di

import com.bricklytics.pokesphere.datalayer.features.ability.AbilityApiDataSource
import com.bricklytics.pokesphere.datalayer.features.ability.AbilityRepositoryImpl
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonApiDataSource
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonRepositoryImpl
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.ability.AbilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Provides
    fun providePokemonRepository(
        pokemonDataSource: PokemonApiDataSource
    ) : PokemonRepository = PokemonRepositoryImpl(pokemonDataSource)

    @Provides
    fun provideAbilityRepository(
        abilityDataSource: AbilityApiDataSource
    ) : AbilityRepository = AbilityRepositoryImpl(abilityDataSource)
}
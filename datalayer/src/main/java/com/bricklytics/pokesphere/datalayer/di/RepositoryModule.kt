package com.bricklytics.pokesphere.datalayer.di

import com.bricklytics.pokesphere.datalayer.features.ability.AbilityApiDataSource
import com.bricklytics.pokesphere.datalayer.features.ability.AbilityRepositoryImpl
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonApiDataSource
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonRepositoryImpl
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.ability.AbilityRepository
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonDataSource: PokemonApiDataSource,
        pokemonDao: PokemonDao
    ) : PokemonRepository = PokemonRepositoryImpl(
        pokemonApiDataSource = pokemonDataSource,
        pokemonDao = pokemonDao
    )

    @Provides
    @Singleton
    fun provideAbilityRepository(
        abilityDataSource: AbilityApiDataSource
    ) : AbilityRepository = AbilityRepositoryImpl(abilityDataSource)
}
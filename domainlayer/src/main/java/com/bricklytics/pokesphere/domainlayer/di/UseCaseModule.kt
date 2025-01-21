package com.bricklytics.pokesphere.domainlayer.di

import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {

    @Provides
    fun provideGetPokemonDataUseCase(
        repository: PokemonRepository
    ): PokemonGetDataUseCase {
        return PokemonGetDataUseCase(repository)
    }
}
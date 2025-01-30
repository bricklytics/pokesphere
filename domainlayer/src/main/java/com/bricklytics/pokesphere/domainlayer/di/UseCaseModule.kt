package com.bricklytics.pokesphere.domainlayer.di

import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.GetFavoritePokemonUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetListUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.SetFavoritePokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPokemonDataUseCase(
        repository: PokemonRepository
    ): PokemonGetDataUseCase {
        return PokemonGetDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPokemonListUseCase(
        repository: PokemonRepository
    ): PokemonGetListUseCase {
        return PokemonGetListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetFavoritePokemonUseCase(
        repository: PokemonRepository
    ): SetFavoritePokemonUseCase {
        return SetFavoritePokemonUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoritePokemonUseCase(
        repository: PokemonRepository
    ): GetFavoritePokemonUseCase {
        return GetFavoritePokemonUseCase(repository)
    }

}
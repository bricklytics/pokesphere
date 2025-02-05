package com.bricklytics.pokesphere.datalayer.di

import com.bricklytics.pokesphere.datalayer.base.BaseApiDataSource
import com.bricklytics.pokesphere.datalayer.features.ability.AbilityApi
import com.bricklytics.pokesphere.datalayer.features.ability.AbilityApiDataSource
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonApi
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonApiDataSource
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataSourceModule {

    @Provides
    fun providesBaseApiDataSource(
        @AppDispatcher(AppDispatchers.IO)
        dispatcher: CoroutineDispatcher
    ): BaseApiDataSource = BaseApiDataSource(
        ioDispatcher = dispatcher
    )

    @Provides
    @Singleton
    fun providePokemonDataSource(
        pokemonApi: PokemonApi,
        @AppDispatcher(AppDispatchers.IO)
        dispatcher: CoroutineDispatcher
    ): BaseApiDataSource = PokemonApiDataSource(
        pokemonApi = pokemonApi,
        ioDispatcher = dispatcher
    )

    @Provides
    @Singleton
    fun provideAbilityDataSource(
        abilityAPI: AbilityApi,
        @AppDispatcher(AppDispatchers.IO)
        dispatcher: CoroutineDispatcher
    ): BaseApiDataSource = AbilityApiDataSource(
        abilityApi = abilityAPI,
        ioDispatcher = dispatcher
    )
}
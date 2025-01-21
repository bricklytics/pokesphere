package com.bricklytics.pokesphere.datalayer.di

import com.bricklytics.pokesphere.datalayer.features.ability.AbilityAPI
import com.bricklytics.pokesphere.datalayer.features.pokemon.PokemonAPI
import com.bricklytics.pokesphere.datalayer.network.provider.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {

    private val retrofit = RetrofitProvider.provideApiService()

    private inline fun <reified T> createApi(): T {
        return retrofit.create(T::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonAPI() = createApi<PokemonAPI>()

    @Provides
    @Singleton
    fun provideAbilityAPI() = createApi<AbilityAPI>()
}
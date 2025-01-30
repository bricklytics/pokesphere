package com.bricklytics.pokesphere.localdatalayer.di

import android.app.Application
import com.bricklytics.pokesphere.localdatalayer.database.PokesphereDatabase
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LocalDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): PokesphereDatabase {
        return PokesphereDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun providePokemonDao(
        appDatabase: PokesphereDatabase
    ): PokemonDao = appDatabase.pokemonDao()
}
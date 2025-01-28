package com.bricklytics.pokesphere.uilayer.di

import com.bricklytics.pokesphere.uilayer.features.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideHomeViewModel() : HomeViewModel = HomeViewModel()

//    @Provides
//    @ViewModelScoped
//    fun providePokemonViewModel(
//        name: String,
//        getPokemonDataUseCase: PokemonGetDataUseCase
//    ): PokemonViewModel = PokemonViewModel(
//        name = name,
//        getPokemonData = getPokemonDataUseCase
//    )
}
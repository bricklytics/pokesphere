package com.bricklytics.pokesphere.uilayer.di

import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.GetFavoritePokemonUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetListUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.SetFavoritePokemonUseCase
import com.bricklytics.pokesphere.uilayer.base.ResourcesProvider
import com.bricklytics.pokesphere.uilayer.features.home.HomeViewModel
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
internal class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideHomeViewModel(
        getFavoritePokemonUseCase: GetFavoritePokemonUseCase,
        @AppDispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher,
    ) : HomeViewModel = HomeViewModel(
        getFavoritePokemonUseCase = getFavoritePokemonUseCase,
        dispatcher = dispatcher
    )

    @Provides
    @ViewModelScoped
    fun providePokemonViewModel(
        getPokemonDataUseCase: PokemonGetDataUseCase,
        getPokemonListUseCase: PokemonGetListUseCase,
        setFavoritePokemonUseCase: SetFavoritePokemonUseCase,
        resources: ResourcesProvider,
        @AppDispatcher(AppDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): PokemonViewModel = PokemonViewModel(
        getPokemonDataUseCase = getPokemonDataUseCase,
        getPokemonListUseCase = getPokemonListUseCase,
        setFavoritePokemonUseCase = setFavoritePokemonUseCase,
        resources = resources,
        dispatcher = dispatcher
    )
}
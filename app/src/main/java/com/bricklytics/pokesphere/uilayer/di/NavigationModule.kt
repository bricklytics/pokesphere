package com.bricklytics.pokesphere.uilayer.di

import com.bricklytics.pokesphere.uilayer.base.navigation.AppNavGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class NavigationModule {

    @Provides
    fun provideAppNavGraph() = AppNavGraph()
}
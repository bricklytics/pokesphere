package com.bricklytics.pokesphere.datalayer.di

import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class DispatcherModule {

    @Provides
    @AppDispatcher(AppDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @AppDispatcher(AppDispatchers.DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @AppDispatcher(AppDispatchers.MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
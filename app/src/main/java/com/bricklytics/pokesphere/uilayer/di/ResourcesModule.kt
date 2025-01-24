package com.bricklytics.pokesphere.uilayer.di

import android.content.Context
import com.bricklytics.pokesphere.uilayer.base.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ResourcesModule {

    @Provides
    @Singleton
    fun providesResources(@ApplicationContext context: Context): ResourcesProvider {
        return ResourcesProvider(context)
    }
}
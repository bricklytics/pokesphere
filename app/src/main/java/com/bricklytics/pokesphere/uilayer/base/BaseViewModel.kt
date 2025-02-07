package com.bricklytics.pokesphere.uilayer.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.bricklytics.pokesphere.uilayer.base.navigation.AppNavigationManager
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {
    private lateinit var navigationManager: AppNavigationManager

    fun setNavManager(
        appNavigationManager: AppNavigationManager
    ) {
        this.navigationManager = appNavigationManager
    }

    fun navigateBack() {
        navigationManager.navigateBack()
    }

    fun goToHome() {
        navigationManager.goToHome()
    }

    fun navigateTo(appRoutes: AppRoutes) {
        navigationManager.navigateTo(appRoutes)
    }

    fun navigateTo(appRoutes: AppRoutes, args: Bundle) {
        navigationManager.navigateTo(appRoutes, args)
    }
}
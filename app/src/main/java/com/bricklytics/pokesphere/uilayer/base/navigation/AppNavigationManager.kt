package com.bricklytics.pokesphere.uilayer.base.navigation

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.navOptions
import kotlin.properties.Delegates

class AppNavigationManager {

    private lateinit var navController: NavController
    private lateinit var backStackEntry: NavBackStackEntry
    private var _launchSingleTop by Delegates.notNull<Boolean>()
    private var _restoreState by Delegates.notNull<Boolean>()

    fun navigateBack() {
        val route = navController.previousBackStackEntry?.destination?.route
        route?.run {
            navController.navigate(
                route = route,
                navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(route) {
                        inclusive = true
                        saveState = false
                    }
                }
            )
        }
    }

    fun goToHome() {
        AppRoutes.Home.run {
            navController.navigate(
                route = route,
                navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(route) {
                        inclusive = true
                        saveState = false
                    }
                }
            )
        }
    }

    fun navigateTo(
        appRoutes: AppRoutes
    ) {
        navController.navigate(
            route = appRoutes.route,
            navOptions = navOptions {
                launchSingleTop = _launchSingleTop
                restoreState = _restoreState
            }
        )
    }

    fun navigateTo(
        appRoutes: AppRoutes,
        args: String
    ) {
        val encodedJson = Uri.encode(args)
        navController.navigate(
            route = "${appRoutes.route}/$encodedJson",
            navOptions = navOptions {
                launchSingleTop = _launchSingleTop
                restoreState = _restoreState
                popUpTo(appRoutes.route) {
                    saveState = true
                }
            }
        )
    }

    fun getArgs(key: String): String {
        val encodedJson = backStackEntry.arguments?.getString(key) ?: ""
        return Uri.decode(encodedJson)
    }

    class Builder {
        private var nc: NavController? = null
        private var be: NavBackStackEntry? = null
        private var launchSingleTop = true
        private var restoreState = true

        fun setNavController(navController: NavController): Builder {
            nc = navController
            return this
        }

        fun setNavBackStackEntry(navBackStackEntry: NavBackStackEntry): Builder {
            be = navBackStackEntry
            return this
        }

        fun setLaunchSingleTop(launchSingleTop: Boolean): Builder {
            this.launchSingleTop = launchSingleTop
            return this
        }

        fun setRestoreState(restoreState: Boolean): Builder {
            this.restoreState = restoreState
            return this
        }

        fun build(): AppNavigationManager {
            requireNotNull(nc, { "NavController must be set" })
            requireNotNull(be, { "NavBackStackEntry must be set" })

            return AppNavigationManager().apply {
                navController = nc!!
                backStackEntry = be!!
                _launchSingleTop = launchSingleTop
                _restoreState = restoreState
            }
        }
    }
}

class AppNavigationManagerBuilder {
    lateinit var navControler: NavController
    lateinit var backStackEntry: NavBackStackEntry
    var launchSingleTop = true
    var restoreState = true

    fun build(): AppNavigationManager {
        return AppNavigationManager.Builder()
            .setNavController(navControler)
            .setNavBackStackEntry(backStackEntry)
            .setLaunchSingleTop(launchSingleTop)
            .setRestoreState(restoreState)
            .build()
    }
}

fun appNavigationManager(
    appNavBuilder: AppNavigationManagerBuilder.() -> Unit
): AppNavigationManager {
    return AppNavigationManagerBuilder()
        .apply(appNavBuilder)
        .build()
}
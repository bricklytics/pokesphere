package com.bricklytics.pokesphere.uilayer.base

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

class ResourcesProvider(
    @ApplicationContext
    private val context: Context
) {

    fun getString(resId: Int): String = context.getString(resId)

    fun getString(resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)
}
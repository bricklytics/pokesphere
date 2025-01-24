package com.bricklytics.pokesphere.uilayer.features.pokemon.model

import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.model.BottomSheetModel

data class BottomSheetUIState(
    val code: Int = 0,
    val message: String = "",
    val cause: String = "",
    val errorType: BottomSheetType = BottomSheetType.Undefined,
    val enabled: Boolean = false,
    val model: BottomSheetModel = BottomSheetModel(),
)

enum class BottomSheetType {
    Undefined,
    Error;
}

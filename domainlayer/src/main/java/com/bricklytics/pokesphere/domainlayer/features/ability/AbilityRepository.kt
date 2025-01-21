package com.bricklytics.pokesphere.domainlayer.features.ability

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel

interface AbilityRepository {
    suspend fun getAbility(name: String): ResultWrapper<AbilityModel, ErrorDetailModel>
//    suspend fun getAbilityList(): ResultWrapper<AbilitiesModel, ErrorDetailModel>
}
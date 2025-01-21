package com.bricklytics.pokesphere.datalayer.features.ability

import androidx.annotation.WorkerThread
import com.bricklytics.pokesphere.datalayer.base.BaseApiDataSource
import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.features.ability.model.AbilityDTO
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper

@WorkerThread
class AbilityApiDataSource(
    private val abilityApi: AbilityAPI,
) : BaseApiDataSource() {

    suspend fun getAbility(
        name: String
    ): ResultWrapper<AbilityDTO, ErrorDetailDTO> {
        return safeApiCall{
            abilityApi.getAbility(name)
        }
    }
}
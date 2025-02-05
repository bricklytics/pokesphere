package com.bricklytics.pokesphere.datalayer.features.ability

import androidx.annotation.WorkerThread
import com.bricklytics.pokesphere.datalayer.base.BaseApiDataSource
import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.features.ability.model.AbilityDTO
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@WorkerThread
class AbilityApiDataSource @Inject constructor (
    private val abilityApi: AbilityApi,
    @AppDispatcher(AppDispatchers.IO)
    ioDispatcher: CoroutineDispatcher,
) : BaseApiDataSource(ioDispatcher) {

    suspend fun getAbility(
        name: String
    ): ResultWrapper<AbilityDTO, ErrorDetailDTO> {
        return safeApiCall{
            abilityApi.getAbility(name)
        }
    }
}
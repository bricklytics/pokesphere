package com.bricklytics.pokesphere.datalayer.features.ability

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.features.ability.AbilityRepository
import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel

class AbilityRepositoryImpl(
    private val abilityDataSource: AbilityApiDataSource
) : AbilityRepository {

    override suspend fun getAbility(
        name: String
    ): ResultWrapper<AbilityModel, ErrorDetailModel> {
        return abilityDataSource.getAbility(name)
            .transformSuccess { it.mapTo() }
            .transformError { it.mapTo() }
    }
}
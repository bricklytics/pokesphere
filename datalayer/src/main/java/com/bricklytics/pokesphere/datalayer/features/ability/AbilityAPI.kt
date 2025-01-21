package com.bricklytics.pokesphere.datalayer.features.ability

import com.bricklytics.pokesphere.datalayer.features.ability.model.AbilityDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityAPI {

    @GET("/api/v2/ability/{name}")
    suspend fun getAbility(
        @Path("name") name: String
    ): Response<AbilityDTO>
}
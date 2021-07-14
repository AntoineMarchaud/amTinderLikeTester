package com.amarchaud.amtinderliketester.data.network

import com.amarchaud.amtinderliketester.domain.api.ApiProfiles
import retrofit2.Response
import retrofit2.http.GET

interface TestApi {

    @GET("/public/take_home_sample_profiles")
    suspend fun getProfiles(): Response<ApiProfiles>
}
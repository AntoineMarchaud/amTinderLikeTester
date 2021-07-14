package com.amarchaud.amtinderliketester.domain.repository

import arrow.core.Either
import com.amarchaud.amtinderliketester.data.network.TestApi
import com.amarchaud.amtinderliketester.domain.api.ApiProfiles
import okhttp3.ResponseBody
import javax.inject.Inject

class TestRepo @Inject constructor(
    private val testApi: TestApi
) : ITestRepo {
    override suspend fun getProfiles(): Either<ResponseBody?, ApiProfiles?> {
        val res = testApi.getProfiles()
        if (res.isSuccessful) {
            return Either.Right(res.body())
        } else {
            return Either.Left(res.errorBody())
        }
    }
}
package com.amarchaud.amtinderliketester.domain.repository

import arrow.core.Either
import com.amarchaud.amtinderliketester.domain.api.ApiProfiles
import okhttp3.ResponseBody

interface ITestRepo {
    suspend fun getProfiles(): Either<ResponseBody?, ApiProfiles?>
}
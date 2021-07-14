package com.amarchaud.amtinderliketester.di

import com.amarchaud.amtinderliketester.domain.repository.ITestRepo
import com.amarchaud.amtinderliketester.domain.repository.TestRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindTestRepos(repo: TestRepo): ITestRepo
}
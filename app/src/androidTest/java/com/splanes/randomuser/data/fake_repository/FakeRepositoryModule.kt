package com.splanes.randomuser.data.fake_repository

import com.splanes.data.di.RepositoryModule
import com.splanes.randomuser.data.fake_repository.FakeUserRepository
import com.splanes.domain.feature.user.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(
        fakeUserRepository: FakeUserRepository
    ): UserRepository

}
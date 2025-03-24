package com.onehungary.one.domain.di

import android.content.Context
import com.onehungary.one.api.services.details.DetailsDataSource
import com.onehungary.one.api.services.offers.OffersDataSource
import com.onehungary.one.domain.AuthenticationStorage
import com.onehungary.one.domain.DetailsAndOffersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideDetailsAndOffersRepository(
        offersDataSource: OffersDataSource,
        detailsDataSource: DetailsDataSource,
    ): DetailsAndOffersRepository {
        return DetailsAndOffersRepository(
            offersDataSource = offersDataSource,
            detailsDataSource = detailsDataSource
        )
    }

    @Provides
    @Singleton
    fun provideAuthenticationStorage(): AuthenticationStorage {
        return AuthenticationStorage()
    }

}
package com.onehungary.one.domain

import com.onehungary.one.api.services.details.DetailsDataSource
import com.onehungary.one.api.services.offers.OffersDataSource
import com.onehungary.one.domain.common.DomainResult
import com.onehungary.one.domain.model.DetailsEntity
import com.onehungary.one.domain.model.OffersEntity
import com.onehungary.one.domain.model.asDomainEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsAndOffersRepository @Inject constructor(
    private val offersDataSource: OffersDataSource,
    private val detailsDataSource: DetailsDataSource
) {

    suspend fun offersListing(): DomainResult<List<OffersEntity>> = try {
        val offersList = offersDataSource.request().first()
            .map { it.asDomainEntity() }
        DomainResult.domainSuccess(offersList)
    } catch (e: Exception) {
        when (e) {
            is NoSuchElementException -> DomainResult.domainFailure()
            else -> DomainResult.domainException(e)
        }
    }

    suspend fun detailsListing(): DomainResult<List<DetailsEntity>> = try {
        val detailsList = detailsDataSource.request().first()
            .map { it.asDomainEntity() }
        DomainResult.domainSuccess(detailsList)
    } catch (e: Exception) {
        when (e) {
            is NoSuchElementException -> DomainResult.domainFailure()
            else -> DomainResult.domainException(e)
        }
    }

}

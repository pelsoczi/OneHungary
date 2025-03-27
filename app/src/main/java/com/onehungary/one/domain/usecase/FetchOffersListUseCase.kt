package com.onehungary.one.domain.usecase

import androidx.annotation.VisibleForTesting
import com.onehungary.one.domain.AuthenticationStorage
import com.onehungary.one.domain.DetailsAndOffersRepository
import com.onehungary.one.domain.common.DomainResult
import com.onehungary.one.domain.model.OffersEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchOffersListUseCase @Inject constructor(
    private val authenticationStorage: AuthenticationStorage,
    private val repository: DetailsAndOffersRepository
) {

    operator fun invoke(): Flow<List<OffersEntity>> = flow {
        val items = repository.offersListing()
        when (items) {
            is DomainResult.Success<List<OffersEntity>> -> emit(handleDomainEntity(items))
            is DomainResult.Failure -> emit(emptyList())
            is DomainResult.Exception -> throw items.exception
        }
    }

    private fun handleDomainEntity(
        items: DomainResult.Success<List<OffersEntity>>
    ): List<OffersEntity> {
        authenticationStorage.isSessionAuthenticated?.let { hasSession ->
            return sortedGroupOffersList(items.data, hasSession)
        }
        return emptyList()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sortedGroupOffersList(items: List<OffersEntity>, hasSession: Boolean): List<OffersEntity> {
        return buildList {
            val data = items
                .sortedByDescending { it.isSpecial }
                .partition { it.isSpecial }

            val specials = data.first.sortedBy { it.rank }
            val specialsRanked = specials.partition { it.rank != null }
            if (hasSession) {
                addAll(specialsRanked.first)
                addAll(specialsRanked.second.sortedBy { it.id.toInt() })
            }

            val offers = data.second.sortedBy { it.rank }
            val offersRanked = offers.partition { it.rank != null }
            addAll(offersRanked.first)
            addAll(offersRanked.second.sortedBy { it.id.toInt() })
        }
    }

}
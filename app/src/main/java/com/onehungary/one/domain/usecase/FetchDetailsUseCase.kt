package com.onehungary.one.domain.usecase

import com.onehungary.one.domain.DetailsAndOffersRepository
import com.onehungary.one.domain.common.DomainResult
import com.onehungary.one.domain.model.DetailsEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchDetailsUseCase @Inject constructor(
    private val repository: DetailsAndOffersRepository,
) {

    operator fun invoke(id: Int) = flow {
        val items = repository.detailsListing()
        when (items) {
            is DomainResult.Success<List<DetailsEntity>> -> emit(handleDomainEntity(items, id))
            is DomainResult.Failure -> throw RuntimeException(items.error)
            is DomainResult.Exception -> throw items.exception
        }
    }

    private fun handleDomainEntity(
        items: DomainResult.Success<List<DetailsEntity>>,
        id: Int
    ): DetailsEntity {
        val detail = items.data.first { it.id.toInt() == id }
        return detail
    }

}
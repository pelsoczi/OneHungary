package com.onehungary.one.domain

import com.google.common.truth.Truth
import com.onehungary.one.api.services.details.DetailsDataSource
import com.onehungary.one.api.services.details.model.DetailsDTO
import com.onehungary.one.api.services.offers.OffersDataSource
import com.onehungary.one.api.services.offers.model.OffersDTO
import com.onehungary.one.domain.common.DomainResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DetailsAndOffersRepositoryTest {

    private val offersDataSource = mockk<OffersDataSource>()
    private val detailsDataSource = mockk<DetailsDataSource>()

    private lateinit var repository: DetailsAndOffersRepository

    @Before
    fun setup() {
        repository = DetailsAndOffersRepository(
            offersDataSource = offersDataSource,
            detailsDataSource = detailsDataSource
        )
    }

    // region: offers

    @Test
    fun `offers api mapped to success domain model`() = runTest {
        // given
        val dto = mockk<OffersDTO>()
        coEvery { offersDataSource.request() } returns flowOf(listOf(dto))
        // when
        val data = repository.offersListing()
        // then
        Truth.assertThat(data).isInstanceOf(DomainResult.Success::class.java)
        coVerify(exactly = 1) { offersDataSource.request() }
    }

    @Test
    fun `offers api mapped to failure domain model`() = runTest {
        // given
        val dto = mockk<OffersDTO>()
        coEvery { offersDataSource.request() } returns emptyFlow()
        // when
        val data = repository.offersListing()
        // then
        Truth.assertThat(data).isInstanceOf(DomainResult.Failure::class.java)
        coVerify(exactly = 1) { offersDataSource.request() }
    }

    @Test
    fun `offers api mapped to exception domain model`() = runTest {
        // given
        val dto = mockk<OffersDTO>()
        coEvery { offersDataSource.request() } throws RuntimeException()
        // when
        val data = repository.offersListing()
        // then
        Truth.assertThat(data).isInstanceOf(DomainResult.Exception::class.java)
        coVerify(exactly = 1) { offersDataSource.request() }
    }

    // endregion

    // region: details

    @Test
    fun `details api mapped to success domain model`() = runTest {
        // given
        val dto = mockk<DetailsDTO>()
        coEvery { detailsDataSource.request() } returns flowOf(listOf(dto))
        // when
        val data = repository.detailsListing()
        // then
        Truth.assertThat(data).isInstanceOf(DomainResult.Success::class.java)
        coVerify(exactly = 1) { detailsDataSource.request() }
    }

    @Test
    fun `details api mapped to failure domain model`() = runTest {
        // given
        val dto = mockk<DetailsDTO>()
        coEvery { detailsDataSource.request() } returns emptyFlow()
        // when
        val data = repository.detailsListing()
        // then
        Truth.assertThat(data).isInstanceOf(DomainResult.Failure::class.java)
        coVerify(exactly = 1) { detailsDataSource.request() }
    }

    @Test
    fun `details api mapped to exception domain model`() = runTest {
        // given
        val dto = mockk<DetailsDTO>()
        coEvery { detailsDataSource.request() } throws RuntimeException()
        // when
        val data = repository.detailsListing()
        // then
        Truth.assertThat(data).isInstanceOf(DomainResult.Exception::class.java)
        coVerify(exactly = 1) { detailsDataSource.request() }
    }

    // endregion

}
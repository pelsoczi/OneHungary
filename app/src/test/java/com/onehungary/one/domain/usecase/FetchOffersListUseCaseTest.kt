package com.onehungary.one.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.onehungary.one.domain.AuthenticationStorage
import com.onehungary.one.domain.DetailsAndOffersRepository
import com.onehungary.one.domain.common.DomainResult
import com.onehungary.one.domain.model.OffersEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class FetchOffersListUseCaseTest {

    private val authenticationStorage = mockk<AuthenticationStorage>()
    private val offersRepository = mockk<DetailsAndOffersRepository>()

    private lateinit var useCase: FetchOffersListUseCase

    @Before
    fun setUp() {
        useCase = FetchOffersListUseCase(
            authenticationStorage = authenticationStorage,
            repository = offersRepository
        )
    }

    @Test
    fun `domain success emits list of offers`() = runTest {
        // given
        val offer = mockk<OffersEntity>()
        val offersList = DomainResult.domainSuccess(listOf(offer))
        coEvery { offersRepository.offersListing() } returns offersList
        // when
        useCase.invoke().test {
            // then
            val list = awaitItem().let {
                Truth.assertThat(it).isNotEmpty()
            }
            awaitComplete()
        }
        coVerify(exactly = 1) { authenticationStorage.saveSession(any()) }
        coVerify(exactly = 1) { offersRepository.offersListing() }
    }

    @Test
    fun `domain failure emits empty list`() = runTest {
        // given
        val offersList = DomainResult.domainFailure()
        coEvery { offersRepository.offersListing() } returns offersList
        // when
        useCase.invoke().test {
            // then
            val list = awaitItem().let {
                Truth.assertThat(it).isEmpty()
            }
            awaitComplete()
        }
        coVerify(exactly = 0) { authenticationStorage.saveSession(any()) }
        coVerify(exactly = 1) { offersRepository.offersListing() }
    }

    @Test
    fun `domain exceptions do not emit and throws domain exception`() = runTest {
        // given
        val offersList = DomainResult.domainException(RuntimeException())
        coEvery { offersRepository.offersListing() } returns offersList
        // when
        useCase.invoke().test {
            // then
            awaitError()
        }
        coVerify(exactly = 0) { authenticationStorage.saveSession(any()) }
        coVerify(exactly = 1) { offersRepository.offersListing() }
    }

    @Test
    fun `usecase sorts special desc and then partitions, and combines id sorted lists`() = runTest {
        // given
        val one = OffersEntity("1", 2, true, "One time 1 GB", "Let's choose between our data packages. This is a special offer just for you!")
        val two = OffersEntity("2", 1, true, "One time 300 MB", "Let's choose between our data packages.")
        val three = OffersEntity("3", 4, false, "One time 500 MB", "Let's choose between our data packages.")
        val four = OffersEntity("4", 3, true, "One time 100 MB", "")
        val five = OffersEntity("5", null, true, "One time 200 MB", "Let's choose between our data packages.")
        // randomize listof entries order to simulate larger lists and unknown sequences to test
        val offersList = listOf(one, five, three, two, four)
        coEvery { offersRepository.offersListing() } returns DomainResult.domainSuccess(offersList)

        // when
        val list = useCase.sortedGroupOffersList(offersList, true)

        //then
        // the order will always return to "one two four five three"
        list.let {
            Truth.assertThat(it[0] == one)
            Truth.assertThat(it[1] == two)
            Truth.assertThat(it[2] == four)
            Truth.assertThat(it[3] == five)
            Truth.assertThat(it[4] == three)
        }
    }

}
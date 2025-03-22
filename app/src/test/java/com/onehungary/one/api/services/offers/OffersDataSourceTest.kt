package com.onehungary.one.api.services.offers

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.onehungary.one.api.common.readJsonFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class OffersDataSourceTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockWebServer = MockWebServer()
    private val httpClient = OkHttpClient.Builder().build()

    private val apiService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(httpClient)
        .build()
        .create(OffersService::class.java)

    private lateinit var gson: Gson
    private lateinit var offersDataSource: OffersDataSource

    @Before
    fun setUp() {
        gson = Gson()
        offersDataSource = OffersDataSource(
            offersService = apiService,
            gson = gson,
            ioDispatcher = testDispatcher
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun `request offers, 200 success`() = runTest {
        // given
        enqueueMockResponse(200, "offers-200-success.json")
        // when
        offersDataSource.request().test {
            // then
            awaitItem().let {
                Truth.assertThat(it).isNotEmpty()
            }
            awaitComplete()
        }
    }

    @Test
    fun `request invalid url`() = runTest {
        // given
        enqueueMockResponse(400, "offers-400-invalid-url.json")
        // when
        offersDataSource.request().test {
            // then
            awaitError()
        }
    }

    @Test
    fun `request when server unreachable, and all http client errors`() = runTest {
        // given
        val response = MockResponse()
        response.setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        mockWebServer.enqueue(response)
        // when
        offersDataSource.request().test {
            // then
            awaitError().let {
                assert(it is Exception)
            }
        }
        // and when
        mockWebServer.shutdown()
        offersDataSource.request().test {
            // and then
            awaitError().let {
                assert(it is Exception)
            }
        }
    }

    private fun enqueueMockResponse(responseCode: Int, fileName: String) {
        val response = MockResponse()
            .setResponseCode(responseCode)
            .setBody(readJsonFile("com/onehungary/one/api/offers/$fileName"))
        mockWebServer.enqueue(response)
    }

}

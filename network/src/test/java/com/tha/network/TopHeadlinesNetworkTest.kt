package com.tha.network

import com.tha.core.Constants.BASE_URL
import com.tha.core.models.BaseApiResponse
import com.tha.core.models.NetworkResult
import com.tha.network.topHeadlines.TopHeadlinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class TopHeadlinesNetworkTest: BaseApiResponse() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val topHeadlinesApi = retrofit.create(TopHeadlinesApi::class.java)

    @Test
    fun  `test Retrofit Instance`() {
        assert(retrofit.baseUrl().toString() == BASE_URL)
    }

    @Test
    fun `top headlines api response should be success when api key is valid`() {

        val networkResult =  runBlocking {
            safeApiCall { topHeadlinesApi.getTopHeadlinesBySource("a4a687d78daf4759a6aef3803e0cec6a", "bbc-news") }
        }

        assert(networkResult is NetworkResult.Success)
    }

    @Test
    fun `top headlines api response should be error when api key is invalid`() {

        val networkResult =  runBlocking {
            safeApiCall { topHeadlinesApi.getTopHeadlinesBySource("", "bbc-news") }
        }

        assert(networkResult is NetworkResult.Error)
    }

    @Test
    fun `should return bbc news top headlines when source is bbc news`() {

        val networkResult =  runBlocking {
            safeApiCall { topHeadlinesApi.getTopHeadlinesBySource("a4a687d78daf4759a6aef3803e0cec6a", "bbc-news") }
        }

        assert(networkResult is NetworkResult.Success)
        assert(networkResult.data != null
                && networkResult.data?.articles!!.isNotEmpty()
                && networkResult.data?.articles!![0].source.id == "bbc-news")
    }

    @Test
    fun `should return abc news top headlines when source is abc news`() {

        val networkResult =  runBlocking {
            safeApiCall { topHeadlinesApi.getTopHeadlinesBySource("a4a687d78daf4759a6aef3803e0cec6a", "abc-news") }
        }

        assert(networkResult is NetworkResult.Success)
        assert(networkResult.data != null
                && networkResult.data?.articles!!.isNotEmpty()
                && networkResult.data?.articles!![0].source.id == "abc-news")
    }

}

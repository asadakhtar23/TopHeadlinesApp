package com.tha.network.topHeadlines

import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import retrofit2.Response
import javax.inject.Inject

class TopHeadlinesNetwork @Inject constructor(private val topHeadlinesApi: TopHeadlinesApi) {
    suspend fun getTopHeadlinesBySource(apikey: String, source: String): Response<TopHeadlinesResponse>  {
        return topHeadlinesApi.getTopHeadlinesBySource(apikey, source)
    }
}
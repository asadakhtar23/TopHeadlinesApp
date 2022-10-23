package com.tha.network.topHeadlines

import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopHeadlinesApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesBySource(@Query("apiKey") apikey: String, @Query("sources") sources: String): Response<TopHeadlinesResponse>
}
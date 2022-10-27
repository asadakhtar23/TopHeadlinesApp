package com.tha.network.topHeadlines

import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import retrofit2.Response
import javax.inject.Inject

class TopHeadlinesNetwork @Inject constructor(private val topHeadlinesApi: TopHeadlinesApi) {

    /**
     * Get top headlines according to source from the api and return
     * top headlines response accordingly
     *
     * @param apiKey Api Key of the NewsAPI
     * @param source targeted news source (i.e bbc-news, abc-news..)
     *
     * @return Response<TopHeadlinesResponse> - Top headline response
     *
     */
    suspend fun getTopHeadlinesBySource(apikey: String, source: String): Response<TopHeadlinesResponse>  {
        return topHeadlinesApi.getTopHeadlinesBySource(apikey, source)
    }
}
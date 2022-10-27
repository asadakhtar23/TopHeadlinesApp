package com.tha.data

import com.tha.core.models.NetworkResult
import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import com.tha.core.models.BaseApiResponse
import com.tha.core.models.topHeadlines.Article
import com.tha.network.topHeadlines.TopHeadlinesNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class TopHeadlinesRepository @Inject constructor(private val topHeadlinesNetwork: TopHeadlinesNetwork): BaseApiResponse() {

    /**
     * Get top headlines according to source and return its response
     * in the flow of data stream.
     *
     * @param apiKey Api Key of the NewsAPI
     * @param source targeted news source (i.e bbc-news, abc-news..)
     *
     * @return Flow<NetworkResult<TopHeadlinesResponse>> - data stream flow of top headline response
     *
     */
    fun getTopHeadlinesBySource(apiKey:String, source: String): Flow<NetworkResult<TopHeadlinesResponse>> {
        return flow {
            emit(safeApiCall { topHeadlinesNetwork.getTopHeadlinesBySource(apiKey, source) })
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Sort list of article according to published date
     *
     * @param articles list of article
     *
     * @return List<Article> - sorted list of article
     *
     */
    fun sortArticlesByDate(articles: List<Article>): List<Article> {
        Collections.sort(articles, Comparator { firstArticle, secondArticle ->

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

            return@Comparator dateFormat.parse(secondArticle.publishedAt)
                ?.compareTo(dateFormat.parse(firstArticle.publishedAt)) ?: -1
        })
        return articles
    }
}
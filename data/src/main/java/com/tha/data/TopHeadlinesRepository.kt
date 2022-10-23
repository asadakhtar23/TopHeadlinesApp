package com.tha.data

import com.tha.core.models.NetworkResult
import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import com.tha.network.BaseApiResponse
import com.tha.network.topHeadlines.TopHeadlinesNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TopHeadlinesRepository @Inject constructor(private val topHeadlinesNetwork: TopHeadlinesNetwork): BaseApiResponse() {
    fun getTopHeadlinesBySource(apiKey:String, source: String): Flow<NetworkResult<TopHeadlinesResponse>> {
        return flow {
            emit(safeApiCall { topHeadlinesNetwork.getTopHeadlinesBySource(apiKey, source) })
        }.flowOn(Dispatchers.IO)
    }
}
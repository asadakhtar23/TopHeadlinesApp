package com.tha.data

import com.tha.core.models.NetworkResult
import com.tha.core.models.topHeadlines.Article
import com.tha.core.models.topHeadlines.Source
import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import com.tha.network.topHeadlines.TopHeadlinesNetwork
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TopHeadlinesRepositoryTest {

    private val topHeadlinesNetwork: TopHeadlinesNetwork = mockk()

    private val topHeadlinesRepository = spyk(TopHeadlinesRepository(topHeadlinesNetwork))
    private val topHeadlinesResponse: TopHeadlinesResponse = mockk()

    private val apiKey = "dsdasdasda"

    private val unsortedArticleData = mutableListOf(
        Article("BBC News"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            , "2022-10-25T12:37:21.7532483Z"
            , Source("bbc-news", "BBC News")
            ,"Russian court rejects Brittney Griner appeal over jail term"
            ,"http://www.bbc.co.uk/news/world-europe-63387888"
            ,"https://ichef.bbci.co.uk/news/1024/branded_news/83B3/production/_115651733_breaking-large-promo-nc.png"),
        Article("BBC News"
            ,"Iranian man who didn't wash for half a century dies at 94"
            ,"Amou Haji avoided soap for fear of getting ill and lived part of his life in a hole in the ground."
            , "2022-10-26T04:22:32.1634494Z"
            , Source("bbc-news", "BBC News")
            ,"Iranian man who didn't wash for half a century dies at 94"
            ,"http://www.bbc.co.uk/news/world-middle-east-63389045"
            ,"https://ichef.bbci.co.uk/news/1024/branded_news/10903/production/_127334876_mediaitem127333860.jpg")
        )

    @Test
    fun `should return success flow of Top Headlines data stream when Api response is successful` () {

        every { topHeadlinesRepository.getTopHeadlinesBySource(any(), any()) } returns flow { emit(NetworkResult.Success(topHeadlinesResponse)) }

        runBlocking {
            val flowData = topHeadlinesRepository.getTopHeadlinesBySource(apiKey, "bbc-news")
            flowData.collectLatest {
                assert(it is NetworkResult.Success && it.data == topHeadlinesResponse)
            }
        }
    }

    @Test
    fun `should return error flow of Top Headlines data stream when Api response is not successful` () {

        every { topHeadlinesRepository.getTopHeadlinesBySource(any(), any()) } returns flow { emit(NetworkResult.Error("error")) }

        runBlocking {
            val flowData = topHeadlinesRepository.getTopHeadlinesBySource(apiKey, "bbc-news")
            flowData.collectLatest {
                assert(it is NetworkResult.Error && it.message == "error")
            }
        }
    }

    @Test
    fun `articles data should be sorted by date` () {
        val sortedArticleList = topHeadlinesRepository.sortArticlesByDate(unsortedArticleData)
        assert(sortedArticleList[0].title == "Iranian man who didn't wash for half a century dies at 94"
                && sortedArticleList[0].publishedAt == "2022-10-26T04:22:32.1634494Z"
                && sortedArticleList[1].title == "Russian court rejects Brittney Griner appeal over jail term"
                && sortedArticleList[1].publishedAt == "2022-10-25T12:37:21.7532483Z")
    }

}
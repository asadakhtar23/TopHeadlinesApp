package com.android.topheadlinesapp

import android.os.Handler
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.topheadlinesapp.utils.MainCoroutineRule
import com.android.topheadlinesapp.viewmodels.TopHeadlinesViewModel
import com.tha.core.models.NetworkResult
import com.tha.core.models.topHeadlines.Article
import com.tha.core.models.topHeadlines.Source
import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import com.tha.data.TopHeadlinesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TopHeadlinesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private var handler: Handler = mockk()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val topHeadlinesRepository: TopHeadlinesRepository = mockk()

    private val topHeadlinesViewModel = spyk(TopHeadlinesViewModel(topHeadlinesRepository))

    private val mockedTopHeadlinesResponse = TopHeadlinesResponse(mutableListOf(
        Article("BBC News"
            ,"Iranian man who didn't wash for half a century dies at 94"
            ,"Amou Haji avoided soap for fear of getting ill and lived part of his life in a hole in the ground."
            , "2022-10-26T04:22:32.1634494Z"
            , Source("bbc-news", "BBC News")
            ,"Iranian man who didn't wash for half a century dies at 94"
            ,"http://www.bbc.co.uk/news/world-middle-east-63389045"
            ,"https://ichef.bbci.co.uk/news/1024/branded_news/10903/production/_127334876_mediaitem127333860.jpg"),
        Article("BBC News"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            , "2022-10-25T12:37:21.7532483Z"
            , Source("bbc-news", "BBC News")
            ,"Russian court rejects Brittney Griner appeal over jail term"
            ,"http://www.bbc.co.uk/news/world-europe-63387888"
            ,"https://ichef.bbci.co.uk/news/1024/branded_news/83B3/production/_115651733_breaking-large-promo-nc.png")
    ))

    @Test
    fun `should be handle success state when getTopHeadlines response is success` () {

        every { topHeadlinesRepository.getTopHeadlinesBySource(any(), any()) } returns flow { emit(NetworkResult.Success(mockedTopHeadlinesResponse)) }

        every { topHeadlinesRepository.sortArticlesByDate(any()) } returns mockedTopHeadlinesResponse.articles

        runBlocking {
            topHeadlinesViewModel.getTopHeadlines("dasd", "bbc-news")
        }

        Assert.assertEquals(topHeadlinesViewModel.responseTopHeadlines.value?.get(0)?.title, "Iranian man who didn't wash for half a century dies at 94")
    }

    @Test
    fun `should be handle error state when getTopHeadlines response is error` () {
        every { topHeadlinesRepository.getTopHeadlinesBySource(any(), any()) } returns flow { emit(NetworkResult.Error("error")) }

        runBlocking {
            topHeadlinesViewModel.getTopHeadlines("dasd", "bbc-news")
        }

        Assert.assertEquals(topHeadlinesViewModel.showErrorMessage.value, "error")
    }

}
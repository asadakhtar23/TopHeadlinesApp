package com.android.topheadlinesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tha.core.models.NetworkResult
import com.tha.core.models.topHeadlines.Article
import com.tha.core.models.topHeadlines.TopHeadlinesResponse
import com.tha.data.TopHeadlinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(private val topHeadlinesRepository: TopHeadlinesRepository): ViewModel() {

    private val _responseTopHeadlines: MutableLiveData<List<Article>> = MutableLiveData()

    val responseTopHeadlines: LiveData<List<Article>> = _responseTopHeadlines

    var showProgressDialog = MutableLiveData<Boolean>()

    var showErrorMessage = MutableLiveData<String>()

    var isDataInitialized = false

    /**
     * Get top headlines of the targeted source and handle states accordingly
     *
     * @param apiKey Api Key of the NewsAPI
     * @param source targeted news source (i.e bbc-news, abc-news..)
     *
     *
     */
    fun getTopHeadlines(apiKey: String, source: String) {
        showProgressDialog.value = true
        viewModelScope.launch {
            topHeadlinesRepository.getTopHeadlinesBySource(apiKey, source).collectLatest { topHeadlines ->
                handleTopHeadlinesStates(topHeadlines)
            }
        }

    }

    /**
     * Handle state according to top headlines response and sort list of articles
     * according to published date and set that list on live data for there observers
     * if the response is success and set error message on live data if the response is error.
     *
     *
     * @param topHeadlines Network response of top headlines
     *
     */
    private fun handleTopHeadlinesStates(topHeadlines: NetworkResult<TopHeadlinesResponse>) {
        when (topHeadlines) {
            is NetworkResult.Success -> {
                showProgressDialog.postValue(false)
                val sortedArticles = topHeadlinesRepository.sortArticlesByDate(topHeadlines.data?.articles!!)
                _responseTopHeadlines.postValue(sortedArticles)
                isDataInitialized = true
            }
            is NetworkResult.Error -> {
                showProgressDialog.postValue(false)
                showErrorMessage.postValue(topHeadlines.message ?: "Data Load Error")
            }
        }
    }

}
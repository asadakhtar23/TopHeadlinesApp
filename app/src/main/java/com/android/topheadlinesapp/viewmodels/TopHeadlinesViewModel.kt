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
class TopHeadlinesViewModel @Inject constructor(private val topStoriesRepository: TopHeadlinesRepository): ViewModel() {

    private val _responseTopHeadlines: MutableLiveData<List<Article>> = MutableLiveData()

    val responseTopHeadlines: LiveData<List<Article>> = _responseTopHeadlines

    var showProgressDialog = MutableLiveData<Boolean>()

    var showErrorMessage = MutableLiveData<String>()

    var isDataInitialized = false

    fun getTopHeadlines(source: String) {
        showProgressDialog.value = true
        viewModelScope.launch {
            topStoriesRepository.getTopHeadlinesBySource("a4a687d78daf4759a6aef3803e0cec6a", source).collectLatest { topHeadlines ->
                handleTopHeadlinesStates(topHeadlines)
            }
        }

    }

    fun handleTopHeadlinesStates(topHeadlines: NetworkResult<TopHeadlinesResponse>) {
        when (topHeadlines) {
            is NetworkResult.Success -> {
                showProgressDialog.postValue(false)
                _responseTopHeadlines.postValue(topHeadlines.data?.articles)
                isDataInitialized = true
            }
            is NetworkResult.Error -> {
                showProgressDialog.postValue(false)
                showErrorMessage.postValue(topHeadlines.message ?: "Data Load Error")
            }
        }
    }

}
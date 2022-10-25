package com.android.topheadlinesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.tha.core.models.topHeadlines.Article

class DetailsViewModel: ViewModel() {
    fun getImageUrl (article: Article): String {
        return article.urlToImage.ifEmpty { "" }
    }
}
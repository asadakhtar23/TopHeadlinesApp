package com.android.topheadlinesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.tha.core.models.topHeadlines.Article

class DetailsViewModel: ViewModel() {

    /**
     * Get image url from an article data. If url is not available then return NA.
     *
     * @param article Article data of headline
     *
     * @return String - image url of the headline
     *
     */
    fun getImageUrl (article: Article): String {
        return article.urlToImage.ifEmpty { "NA" }
    }
}
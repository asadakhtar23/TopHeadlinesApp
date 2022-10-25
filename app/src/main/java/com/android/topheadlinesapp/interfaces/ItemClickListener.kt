package com.android.topheadlinesapp.interfaces

import com.tha.core.models.topHeadlines.Article

interface ItemClickListener {
    fun onItemClickListener(position: Int, data: Article)
}
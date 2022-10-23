package com.tha.core.models.topHeadlines

data class TopHeadlinesResponse(
    var articles: List<Article> = listOf(),
    var status: String = "",
    var totalResults: Int = 0,
)
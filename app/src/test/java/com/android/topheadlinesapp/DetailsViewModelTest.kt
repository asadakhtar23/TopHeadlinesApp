package com.android.topheadlinesapp

import com.android.topheadlinesapp.viewmodels.DetailsViewModel
import com.tha.core.models.topHeadlines.Article
import com.tha.core.models.topHeadlines.Source
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailsViewModelTest {
    private val detailsViewModel = DetailsViewModel()

    private val articleDataWithImageUrl = Article("BBC News"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            , "2022-10-25T12:37:21.7532483Z"
            , Source("bbc-news", "BBC News")
            ,"Russian court rejects Brittney Griner appeal over jail term"
            ,"http://www.bbc.co.uk/news/world-europe-63387888"
            ,"https://ichef.bbci.co.uk/news/1024/branded_news/83B3/production/_115651733_breaking-large-promo-nc.png")

    private val articleDataWithoutImageUrl = Article("BBC News"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            ,"Russian court rejects appeal by US basketball star Brittney Griner against her traumatic nine-year jail term for drugs charges"
            , "2022-10-25T12:37:21.7532483Z"
            , Source("bbc-news", "BBC News")
            ,"Russian court rejects Brittney Griner appeal over jail term"
            ,"http://www.bbc.co.uk/news/world-europe-63387888"
            ,"")


    @Test
    fun `should return image url when urlToImage is not empty in article data` () {
        Assert.assertTrue(detailsViewModel.getImageUrl(articleDataWithImageUrl) == "https://ichef.bbci.co.uk/news/1024/branded_news/83B3/production/_115651733_breaking-large-promo-nc.png")
    }

    @Test
    fun `should return NA when urlToImage is empty in article data` () {
        Assert.assertTrue(detailsViewModel.getImageUrl(articleDataWithoutImageUrl) == "NA")
    }

}
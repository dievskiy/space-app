package com.ruslan.assignment.data.source

import com.ruslan.assignment.data.Article

class FakeDataSource(private val articles: List<Article>) : ArticleDataSource {

    override suspend fun getArticles(limit: Int): List<Article> {
        return articles
    }

}

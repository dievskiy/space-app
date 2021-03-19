package com.ruslan.assignment.repo

import com.ruslan.assignment.data.Article
import com.ruslan.assignment.data.source.ArticleDataSource

interface ArticlesRepository {
    suspend fun getArticles(limit: Int = 20): List<Article>
}

class ArticlesRepositoryImpl constructor(
    private val articleRemoteDataSource: ArticleDataSource
) : ArticlesRepository {

    override suspend fun getArticles(limit: Int): List<Article> {
        return articleRemoteDataSource.getArticles(limit)
    }

}
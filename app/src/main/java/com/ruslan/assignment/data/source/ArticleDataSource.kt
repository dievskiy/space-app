package com.ruslan.assignment.data.source

import com.ruslan.assignment.data.Article

/**
 * Defines behaviour expected from article data sources
 */
interface ArticleDataSource {
    suspend fun getArticles(limit: Int): List<Article>
}
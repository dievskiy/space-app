package com.ruslan.assignment.data.source.remote

import com.ruslan.assignment.data.Article
import com.ruslan.assignment.data.source.ArticleDataSource
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRemoteService : ArticleDataSource {

    @GET("articles")
    override suspend fun getArticles(@Query("_limit") limit: Int): List<Article>

}

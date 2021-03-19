package com.ruslan.assignment.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ruslan.assignment.data.source.remote.ArticleRemoteService
import com.ruslan.assignment.network.JsonInterceptor
import com.ruslan.assignment.repo.ArticlesRepository
import com.ruslan.assignment.repo.ArticlesRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Injector that provides needed objects in DI manner
 */
object Injector {

    private const val SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    private const val API_BASE_URL = "https://test.spaceflightnewsapi.net/api/v2/"

    val articlesRepo: ArticlesRepository by lazy { provideRemoteArticleRepo() }

    private fun provideRemoteArticleRepo(): ArticlesRepository {
        val client = provideOkHttpClient(JsonInterceptor())
        val retrofit = provideRetrofit(client, provideGsonConverterFactory(provideGson()))
        val remoteService: ArticleRemoteService = provideArticleService(retrofit)
        return ArticlesRepositoryImpl(remoteService)
    }

    private fun provideOkHttpClient(jsonInterceptor: JsonInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(jsonInterceptor)
            .build()

    private fun provideRetrofit(client: OkHttpClient, factory: GsonConverterFactory) =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()

    /**
     * Provide Gson and specify date format to correctly
     * convert date object from server's to [java.util.Date]
     */
    private fun provideGson(): Gson =
        GsonBuilder()
            .setDateFormat(SERVER_DATE_PATTERN)
            .create()

    private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }


    private fun provideArticleService(retrofit: Retrofit): ArticleRemoteService {
        return retrofit.create(ArticleRemoteService::class.java)
    }

}
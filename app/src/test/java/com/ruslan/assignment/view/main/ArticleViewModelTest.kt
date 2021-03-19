package com.ruslan.assignment.view.main

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.ruslan.assignment.MainCoroutineRule
import com.ruslan.assignment.data.Article
import com.ruslan.assignment.repo.FakeArticlesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class ArticleViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel: ArticleViewModel

    private lateinit var repository: FakeArticlesRepository

    private lateinit var uiStateObserver: Observer<ArticlesUiState>
    private lateinit var articlesObserver: Observer<List<Article>>


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)

        repository = FakeArticlesRepository()
        viewModel = ArticleViewModel(context, repository)

        setupObservers()
    }

    @Test
    fun `retrieving articles with ViewModel results in error`() = runBlocking {
        repository.setReturnError(true)

        with(viewModel) {
            loadArticles()
            uiState.asLiveData().observeForever(uiStateObserver)
            articles.observeForever(articlesObserver)
        }

        Assert.assertTrue(viewModel.uiState.value is ArticlesUiState.NetworkError)
        Assert.assertTrue(viewModel.articles.value == null)
    }

    @Test
    fun `retrieving articles with ViewModel returns correct data`() = runBlocking {
        repository.setReturnError(false)

        with(viewModel) {
            loadArticles()
            uiState.asLiveData().observeForever(uiStateObserver)
            articles.observeForever(articlesObserver)
        }

        Assert.assertTrue(viewModel.uiState.value == ArticlesUiState.Success)
        Assert.assertTrue(viewModel.articles.value?.isNotEmpty() == true)

    }

    private fun setupObservers() {
        uiStateObserver = mock(Observer::class.java) as Observer<ArticlesUiState>
        articlesObserver = mock(Observer::class.java) as Observer<List<Article>>
    }
}
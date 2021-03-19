package com.ruslan.assignment.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ruslan.assignment.data.Article
import com.ruslan.assignment.repo.ArticlesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ArticlesUiState {
    object NoConnection : ArticlesUiState()
    object Loading : ArticlesUiState()
    object Success : ArticlesUiState()
    object Empty : ArticlesUiState()
    class NetworkError(val code: Int? = null) : ArticlesUiState()
    class DatabaseError(val message: String? = null) : ArticlesUiState()
}

class ArticleViewModel constructor(
    application: Application,
    private val articlesRepository: ArticlesRepository
) : AndroidViewModel(application) {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _uiState = MutableStateFlow<ArticlesUiState>(ArticlesUiState.Empty)
    val uiState: StateFlow<ArticlesUiState> get() = _uiState

    private var job: Job? = null

    fun loadArticles() {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                _uiState.value = ArticlesUiState.Loading
                _articles.value = articlesRepository.getArticles()
                _uiState.value = ArticlesUiState.Success
            } catch (e: Throwable) {
                _uiState.value = ArticlesUiState.NetworkError()
            }
        }
    }

    fun cancelJob() {
        job?.cancel()
    }

}
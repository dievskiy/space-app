package com.ruslan.assignment.repo

import com.ruslan.assignment.MainCoroutineRule
import com.ruslan.assignment.data.Article
import com.ruslan.assignment.data.source.FakeDataSource

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class ArticlesRepositoryImplTest {
    private val article1 = Article(
        "6051e86631c42cd69c01e29a",
        "https://mk0spaceflightnoa02a.kinstacdn.com/wp-content/uploads/2021/03/50875730681_b4e2d8c6cc_k.jpg",
        Date(),
        "Russia’s decision to partner with China on a planned lunar research station, and not join the U.S.-led Artemis moon program, was disappointing after more than two decades of cooperation on the International Space Station, says NASA’s top human spaceflight official.",
        "Russia looks to China as new space exploration partner",
        "https://spaceflightnow.com/2021/03/15/russia-looks-to-china-as-new-space-exploration-partner/"
    )
    private val article2 = article1.copy(title = "Title 2")

    private val remoteArticles = listOf(article1, article2)
    private lateinit var tasksRemoteDataSource: FakeDataSource

    private lateinit var articleRepository: ArticlesRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteArticles)
        articleRepository = ArticlesRepositoryImpl(tasksRemoteDataSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve articles from remote source`() = mainCoroutineRule.runBlockingTest {
        val tasks = articleRepository.getArticles()

        assertThat(tasks, IsEqual(remoteArticles))
    }

}
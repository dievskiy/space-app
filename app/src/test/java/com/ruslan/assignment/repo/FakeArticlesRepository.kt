package com.ruslan.assignment.repo

import com.ruslan.assignment.data.Article
import java.util.*

class FakeArticlesRepository : ArticlesRepository {

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getArticles(limit: Int): List<Article> {
        if (shouldReturnError) {
            throw Exception("Exception in Test")
        }
        return getFakeArticles()
    }

    private fun getFakeArticles(): List<Article> {
        val article1 = Article(
            "6051e86631c42cd69c01e29a",
            "https://mk0spaceflightnoa02a.kinstacdn.com/wp-content/uploads/2021/03/50875730681_b4e2d8c6cc_k.jpg",
            Date(),
            "Russia’s decision to partner with China on a planned lunar research station, and not join the U.S.-led Artemis moon program, was disappointing after more than two decades of cooperation on the International Space Station, says NASA’s top human spaceflight official.",
            "Russia looks to China as new space exploration partner",
            "https://spaceflightnow.com/2021/03/15/russia-looks-to-china-as-new-space-exploration-partner/"
        )
        val article2 = article1.copy()
        return listOf(article1, article2, article2, article2)
    }

}
package com.example.criticaltechworkschallenge.util

import com.example.criticaltechworkschallenge.entity.Article
import com.example.criticaltechworkschallenge.entity.ArticleEntity
import com.example.criticaltechworkschallenge.entity.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleTest {

    @Test
    fun `mapToArticleEntity() should return ArticleEntity with correct values`() {
        val article = Article(
            author = "John Doe",
            content = "This is the content of the article.",
            description = "This is the description of the article.",
            publishedAt = "2023-04-26T13:30:00.000Z",
            source = Source(name = "CNN", id = "cnn"),
            title = "This is the title of the article",
            url = "https://www.example.com/article",
            urlToImage = "https://www.example.com/image.jpg"
        )

        val expected = ArticleEntity(
            author = "John Doe",
            content = "This is the content of the article.",
            description = "This is the description of the article.",
            publishedAt = "2023-04-26T13:30:00.000Z",
            source = Source(name = "CNN", id = "cnn"),
            title = "This is the title of the article",
            url = "https://www.example.com/article",
            urlToImage = "https://www.example.com/image.jpg"
        )

        val actual = article.mapToArticleEntity()

        assertEquals(expected, actual)
    }

}

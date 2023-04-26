package com.example.criticaltechworkschallenge.util

import com.example.criticaltechworkschallenge.entity.Article
import com.example.criticaltechworkschallenge.entity.ArticleEntity
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


fun String.formatDate(): String {
    val dateTime =
        LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC))
    return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
}

fun Article.mapToArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage,
    )
}

fun ArticleEntity.mapToArticleDomainModel(): ArticlePresentationModel {
    return ArticlePresentationModel(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt?.formatDate(), // formatting the image
        source = this.source,
        title = this.title,
        urlToImage = this.urlToImage,
    )
}


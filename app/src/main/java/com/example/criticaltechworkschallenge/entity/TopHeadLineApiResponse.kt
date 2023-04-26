package com.example.criticaltechworkschallenge.entity

data class TopHeadLineApiResponse(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
)
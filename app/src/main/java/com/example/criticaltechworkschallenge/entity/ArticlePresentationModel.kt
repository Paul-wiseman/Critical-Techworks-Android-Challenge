package com.example.criticaltechworkschallenge.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlePresentationModel(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?
): Parcelable
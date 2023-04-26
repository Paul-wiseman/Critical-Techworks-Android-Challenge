package com.example.criticaltechworkschallenge.data.repository

import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.util.Resource
import kotlinx.coroutines.flow.Flow

interface TopHeadlineRepository {
    fun getTopHeadlinePosts(): Flow<Resource<List<ArticlePresentationModel>>>
}
package com.example.criticaltechworkschallenge.domain.usecase

import com.example.criticaltechworkschallenge.data.repository.TopHeadlineRepository
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTopHeadlineUseCase @Inject constructor(private val repository: TopHeadlineRepository) {
    operator fun invoke(): Flow<Resource<List<ArticlePresentationModel>>> =
        repository.getTopHeadlinePosts()
}
package com.example.criticaltechworkschallenge.domain.usecase

import com.example.criticaltechworkschallenge.MockResponse.getListArticlePresentationModel
import com.example.criticaltechworkschallenge.data.repository.TopHeadlineRepository
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class FetchTopHeadlineUseCaseTest {
    private lateinit var mockRepository: TopHeadlineRepository
    private lateinit var fetchTopHeadlineUseCase: FetchTopHeadlineUseCase

    @Before
    fun setup() {
        mockRepository = mockk()
        fetchTopHeadlineUseCase = FetchTopHeadlineUseCase(mockRepository)
    }

    @Test
    fun `invoke() should return Resource_Success`() = runBlocking {
        // given
        val articlePresentationModels = getListArticlePresentationModel()
        val resource = Resource.Success(articlePresentationModels)
        coEvery { mockRepository.getTopHeadlinePosts() } returns flowOf(resource)

        // when
        val result = fetchTopHeadlineUseCase().toList()

        // then
        coVerify { mockRepository.getTopHeadlinePosts() }
        assertEquals(listOf(resource), result)
    }

    @Test
    fun `invoke() should return Resource_Error`() = runBlocking {
        // given
        val throwable = Throwable("Network Error")
        val resource = Resource.Error<List<ArticlePresentationModel>>(throwable)
        coEvery { mockRepository.getTopHeadlinePosts() } returns flowOf(resource)

        // when
        val result = fetchTopHeadlineUseCase().toList()

        // then
        coVerify { mockRepository.getTopHeadlinePosts() }
        assertEquals(listOf(resource), result)
    }
}
package com.example.criticaltechworkschallenge.data.repository

import com.example.criticaltechworkschallenge.MockResponse.getListArticlePresentationModel
import com.example.criticaltechworkschallenge.MockResponse.getListOfArticleEntity
import com.example.criticaltechworkschallenge.MockResponse.getMockWebServer
import com.example.criticaltechworkschallenge.MockResponse.getTopHeadLineApiResponse
import com.example.criticaltechworkschallenge.data.database.NewsDataBase
import com.example.criticaltechworkschallenge.data.database.TopHeadlinePostDao
import com.example.criticaltechworkschallenge.data.remote.NewsApiService
import com.example.criticaltechworkschallenge.util.Resource
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TopHeadlineRepositoryImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var newsApiService: NewsApiService
    private lateinit var newsDataBase: NewsDataBase
    private lateinit var topHeadlinePostDao: TopHeadlinePostDao
    private lateinit var topHeadlineRepository: TopHeadlineRepositoryImpl

    @Before
    fun setup() {
        mockWebServer = getMockWebServer()
        newsApiService = spyk()
        newsDataBase = mockk()
        topHeadlinePostDao = mockk()
        every { newsDataBase.topHeadlinePostDao() } returns topHeadlinePostDao
        topHeadlineRepository = TopHeadlineRepositoryImpl(newsApiService, newsDataBase)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    // Test case : Verify that getTopHeadlinePosts() returns Success Resource
    @Test
    fun `getTopHeadlinePosts() should return Resource_Success`() = runBlocking {
        // given
        coEvery { topHeadlinePostDao.getAllHeadlinePosts() } returns flowOf(getListOfArticleEntity())
        coEvery { newsApiService.getTopHeadlines().body() } returns  getTopHeadLineApiResponse()

        // when
        val result = topHeadlineRepository.getTopHeadlinePosts().last()

        // then
        coVerify { topHeadlinePostDao.getAllHeadlinePosts() }
        assertEquals(Resource.Success(getListArticlePresentationModel()).data, result.data)
    }

}

package com.example.criticaltechworkschallenge.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.criticaltechworkschallenge.MockResponse.getListArticlePresentationModel
import com.example.criticaltechworkschallenge.domain.usecase.FetchTopHeadlineUseCase
import com.example.criticaltechworkschallenge.util.Resource
import com.example.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopHeadlineNewsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TopHeadlineNewsViewModel
    private lateinit var fetchTopHeadlineUseCase: FetchTopHeadlineUseCase

    @Before
    fun setup() {
        fetchTopHeadlineUseCase = mockk()
        viewModel = TopHeadlineNewsViewModel(fetchTopHeadlineUseCase)
    }

    @After
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `fetchTopHeadlinePosts should post Resource_Loading then Resource_Success when fetchTopHeadlineUseCase succeeds`() =
        runBlocking {
            // given
            val expectedResource = Resource.Success(getListArticlePresentationModel())
            coEvery { fetchTopHeadlineUseCase.invoke() } returns flowOf(expectedResource)

            // when
            viewModel.fetchTopHeadlinePosts()


            // then
            val value = viewModel.headLinePostResponse.getOrAwaitValue()
            Assert.assertEquals(value, expectedResource)
        }

    @Test
    fun `fetchTopHeadlinePosts should post Resource_Loading then Resource_Error when fetchTopHeadlineUseCase fails`() =
        runBlocking {
            // given
            val expectedResource =
                Resource.Error(Exception("something went wrong"), getListArticlePresentationModel())
            coEvery { fetchTopHeadlineUseCase.invoke() } returns flowOf(expectedResource)

            // when
            viewModel.fetchTopHeadlinePosts()

            // then
            val value = viewModel.headLinePostResponse.getOrAwaitValue()
            Assert.assertEquals(value.javaClass, expectedResource.javaClass)
        }
}
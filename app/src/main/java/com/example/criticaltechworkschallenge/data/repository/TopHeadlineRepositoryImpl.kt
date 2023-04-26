package com.example.criticaltechworkschallenge.data.repository

import androidx.room.withTransaction
import com.example.criticaltechworkschallenge.data.database.NewsDataBase
import com.example.criticaltechworkschallenge.data.remote.NewsApiService
import com.example.criticaltechworkschallenge.entity.Article
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.util.Resource
import com.example.criticaltechworkschallenge.util.mapToArticleDomainModel
import com.example.criticaltechworkschallenge.util.mapToArticleEntity
import com.example.criticaltechworkschallenge.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopHeadlineRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDataBase: NewsDataBase
) : TopHeadlineRepository {
    private val topHeadlinePostDao = newsDataBase.topHeadlinePostDao()

    override fun getTopHeadlinePosts(): Flow<Resource<List<ArticlePresentationModel>>> =
        networkBoundResource(
            query = {
                topHeadlinePostDao.getAllHeadlinePosts().map { allPost ->
                    allPost.map { articleEntity ->
                        articleEntity.mapToArticleDomainModel() // converting into domain model

                    }
                }
            },
            fetch = {
                newsApiService.getTopHeadlines().body()?.articles?.map { article: Article ->
                    article.mapToArticleEntity()
                }
            },
            saveFetchResult = { articleEntities ->
                newsDataBase.withTransaction {
                    topHeadlinePostDao.deleteAllPosts()
                    articleEntities?.let {
                        topHeadlinePostDao.insertAllPost(articleEntities)
                    }
                }
            }
        )

}
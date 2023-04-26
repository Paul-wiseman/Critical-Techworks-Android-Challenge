package com.example.criticaltechworkschallenge

import com.example.criticaltechworkschallenge.data.remote.NewsApiService
import com.example.criticaltechworkschallenge.entity.ArticleEntity
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.entity.TopHeadLineApiResponse
import com.example.criticaltechworkschallenge.util.Endpoints
import com.example.criticaltechworkschallenge.util.mapToArticleDomainModel
import com.example.criticaltechworkschallenge.util.mapToArticleEntity
import com.google.gson.Gson
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MockResponse {
    val gson = Gson()
    fun getTopHeadLineApiResponse(): TopHeadLineApiResponse {
        val responseJson = """{
    "status": "ok",
    "totalResults": 10,
    "articles": [
        {
            "source": {
                "id": "abc-news",
                "name": "ABC News"
            },
            "author": "Jon Haworth",
            "title": "Man arrested over murder of homeless man living in drainage canal",
            "description": "The suspect was already in custody on unrelated charges at a detention center.",
            "url": "https://abcnews.go.com/US/man-arrested-murder-homeless-man-living-drainage-canal/story?id=98821077",
            "urlToImage": "https://s.abcnews.com/images/US/JemiahGarner_1682407463062_hpMain_16x9_992.jpg",
            "publishedAt": "2023-04-25T06:41:01Z",
            "content": "An arrest has been made over the murder of a homeless man living in a Las Vegas drainage canal that happened two months ago.\r\nThe incident occurred on Feb. 20 at approximately 2:40 a.m. when the Las … [+1212 chars]"
        },
        {
            "source": {
                "id": "abc-news",
                "name": "ABC News"
            },
            "author": "Jon Haworth",
            "title": "Man who attacked church with Molotov cocktails over drag show events federally charged",
            "description": "The man was part of a White Lives Matter group with racist and pro-Nazi views.",
            "url": "https://abcnews.go.com/US/man-attacked-church-molotov-cocktails-drag-show-events/story?id=98820531",
            "urlToImage": "https://s.abcnews.com/images/US/fbi-building-gty-lv-230105_1672939260500_hpMain_16x9_992.jpg",
            "publishedAt": "2023-04-25T06:41:01Z",
            "content": "A man who allegedly attempted to burn down a church with Molotov cocktails after it planned to hold a series of drag show events has been charged with federal crimes.\r\nThe incident occurred on March … [+3444 chars]"
        }
    ]
}"""
        return gson.fromJson(responseJson, TopHeadLineApiResponse::class.java)
    }

    fun getMockWebServer(): MockWebServer {
        return MockWebServer()
    }

    fun getListOfArticleEntity(): List<ArticleEntity> {
        return getTopHeadLineApiResponse().articles.map {
            it.mapToArticleEntity()
        }
    }

    fun getListArticlePresentationModel(): List<ArticlePresentationModel> {
        return getTopHeadLineApiResponse().articles.map {
            it.mapToArticleEntity().mapToArticleDomainModel()
        }
    }
}
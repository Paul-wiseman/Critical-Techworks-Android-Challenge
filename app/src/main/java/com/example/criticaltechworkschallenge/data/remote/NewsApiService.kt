package com.example.criticaltechworkschallenge.data.remote

import com.example.criticaltechworkschallenge.BuildConfig
import com.example.criticaltechworkschallenge.entity.TopHeadLineApiResponse
import com.example.criticaltechworkschallenge.util.Constants.API_KEY
import com.example.criticaltechworkschallenge.util.Endpoints.TOP_HEADLINES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET(TOP_HEADLINES)
    suspend fun getTopHeadlines(
        @Query("sources") sources: String = BuildConfig.sources,
        @Query("X-Api-Key") X_Api_Key: String = API_KEY
    ): Response<TopHeadLineApiResponse>
}
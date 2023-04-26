package com.example.criticaltechworkschallenge.di

import com.example.criticaltechworkschallenge.data.database.NewsDataBase
import com.example.criticaltechworkschallenge.data.remote.NewsApiService
import com.example.criticaltechworkschallenge.data.repository.TopHeadlineRepository
import com.example.criticaltechworkschallenge.data.repository.TopHeadlineRepositoryImpl
import com.example.criticaltechworkschallenge.di.util.provideGenericApiService
import com.example.criticaltechworkschallenge.util.Constants.API_KEY
import com.example.criticaltechworkschallenge.util.Constants.API_KEY_HEADER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(API_KEY_HEADER, API_KEY)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideTopHeadlineService(okHttpClient: OkHttpClient): NewsApiService =
        provideGenericApiService(okHttpClient)

    @Singleton
    @Provides
    fun provideRepository(
        newsApiService: NewsApiService,
        newsDataBase: NewsDataBase
    ): TopHeadlineRepository = TopHeadlineRepositoryImpl(newsApiService, newsDataBase)

}
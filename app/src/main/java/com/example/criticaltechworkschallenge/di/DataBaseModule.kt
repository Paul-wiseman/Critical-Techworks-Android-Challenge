package com.example.criticaltechworkschallenge.di

import android.content.Context
import androidx.room.Room
import com.example.criticaltechworkschallenge.data.database.NewsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideNewDataBase(@ApplicationContext context: Context): NewsDataBase {
        val databaseBuilder =
            Room.databaseBuilder(context, NewsDataBase::class.java, "NewsDataBase.db")
        return databaseBuilder.build()
    }
}
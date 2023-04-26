package com.example.criticaltechworkschallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criticaltechworkschallenge.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NewsDatabaseTypeConverter::class)
abstract class NewsDataBase: RoomDatabase()  {
    abstract fun topHeadlinePostDao():TopHeadlinePostDao
}
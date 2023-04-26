package com.example.criticaltechworkschallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.criticaltechworkschallenge.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopHeadlinePostDao {

    @Query("SELECT * FROM headlinePosts ORDER BY id ASC")
    fun getAllHeadlinePosts(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(headLinePost: List<ArticleEntity>)

    @Query("DELETE FROM headlinePosts")
    suspend fun deleteAllPosts()

}
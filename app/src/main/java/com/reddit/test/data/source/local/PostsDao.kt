package com.reddit.test.data.source.local

import androidx.room.*
import com.reddit.test.data.source.local.entities.PostEntity

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insert(listPosts: List<PostEntity>)

    @Query("SELECT * FROM post_entity")
    fun getAllPosts(): List<PostEntity>
}
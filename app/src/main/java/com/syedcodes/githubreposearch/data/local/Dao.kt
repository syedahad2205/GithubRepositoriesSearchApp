package com.syedcodes.githubreposearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepoListing(repoListing: List<LocalEntity>)

    @Query("DELETE FROM LocalEntity")
    suspend fun clearRepoListing()

    @Query("SELECT * FROM LocalEntity")
    suspend fun getRepoListing(): List<LocalEntity>

}
package com.syedcodes.githubreposearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalEntity::class],
    version = 1
)
abstract class LocalDatabase: RoomDatabase() {
    abstract val dao: Dao
}
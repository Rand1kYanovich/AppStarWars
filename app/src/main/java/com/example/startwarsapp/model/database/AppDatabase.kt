package com.example.startwarsapp.model.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.example.startwarsapp.model.entity.FullInfoCard


@Database(entities = [FullInfoCard::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
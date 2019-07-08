package com.example.startwarsapp.model.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.example.startwarsapp.model.entity.FullInfoCard
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration




@Database(entities = [FullInfoCard::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao


}
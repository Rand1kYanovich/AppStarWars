package com.example.startwarsapp

import android.app.Application
import android.arch.persistence.room.Room
import com.example.startwarsapp.model.database.AppDatabase


class App : Application() {
    lateinit var database: AppDatabase

    companion object {
        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }


    fun getDB(): AppDatabase {
        return getInstance().database
    }
}
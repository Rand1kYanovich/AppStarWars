package com.example.startwarsapp.model.database

import android.arch.persistence.room.*
import com.example.startwarsapp.model.entity.FullInfoCard
import io.reactivex.Flowable
import io.reactivex.Maybe


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoriteList")
    fun getAll(): Flowable<List<FullInfoCard>>


    @Query("SELECT * FROM favoriteList WHERE name = :name")
    fun getById(name: String): Maybe<FullInfoCard>

    @Insert
    fun insert(favorite: FullInfoCard)

    @Update
    fun update(favorite: FullInfoCard)

    @Delete
    fun delete(favorite: FullInfoCard)

}
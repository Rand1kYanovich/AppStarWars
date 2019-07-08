package com.example.startwarsapp.model.database

import android.arch.persistence.room.*
import com.example.startwarsapp.model.entity.FullInfoCard

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM fullinfocard")
    fun getAll():List<FullInfoCard>

    @Query("SELECT * FROM fullinfocard WHERE name = :name")
    fun getById(name:String):FullInfoCard

    @Insert
    fun insert(favorite:FullInfoCard);

    @Update
    fun update(favorite:FullInfoCard);

    @Delete
    fun  delete(favorite:FullInfoCard)

}
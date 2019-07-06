package com.example.startwarsapp

import android.arch.paging.DataSource
import com.example.startwarsapp.model.entity.FullInfoCard

class MyPositionalDataSourceFactory: DataSource.Factory<String, FullInfoCard>() {
    val dataSource = MyPositionalDataSource()
    override fun create(): DataSource<String, FullInfoCard>  = dataSource
}
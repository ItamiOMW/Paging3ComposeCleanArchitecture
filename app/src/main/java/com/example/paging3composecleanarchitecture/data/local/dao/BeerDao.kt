package com.example.paging3composecleanarchitecture.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.paging3composecleanarchitecture.data.local.entity.BeerEntity
import com.example.paging3composecleanarchitecture.domain.model.Beer

@Dao
interface BeerDao {

    @Upsert
    suspend fun upsertAll(beers: List<BeerEntity>)


    @Query("SELECT * FROM BeerEntity")
    fun pagingSource(): PagingSource<Int, BeerEntity>


    @Query("DELETE FROM BeerEntity")
    suspend fun clearAll()

}
package com.example.paging3composecleanarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3composecleanarchitecture.data.local.dao.BeerDao
import com.example.paging3composecleanarchitecture.data.local.entity.BeerEntity

@Database(
    entities = [BeerEntity::class],
    version = 1
)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun beerDao(): BeerDao

}
package com.example.paging3composecleanarchitecture.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paging3composecleanarchitecture.data.local.BeerDatabase
import com.example.paging3composecleanarchitecture.data.local.entity.BeerEntity
import com.example.paging3composecleanarchitecture.data.remote.BeerApi
import com.example.paging3composecleanarchitecture.data.remote.BeerRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(
        @ApplicationContext context: Context,
    ): BeerDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = BeerDatabase::class.java,
            name = "Beers.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideBeerApi(): BeerApi {
        return Retrofit.Builder()
            .baseUrl(BeerApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(BeerApi::class.java)
    }


    @Provides
    @Singleton
    fun provideBeerPager(
        beerDatabase: BeerDatabase,
        beerApi: BeerApi,
    ): Pager<Int, BeerEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            remoteMediator = BeerRemoteMediator(beerDatabase, beerApi),
            pagingSourceFactory = {
                beerDatabase.beerDao().pagingSource()
            }
        )
    }

}
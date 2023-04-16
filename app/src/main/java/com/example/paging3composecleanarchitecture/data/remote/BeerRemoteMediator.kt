package com.example.paging3composecleanarchitecture.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3composecleanarchitecture.data.local.BeerDatabase
import com.example.paging3composecleanarchitecture.data.local.entity.BeerEntity
import com.example.paging3composecleanarchitecture.data.mapper.toBeerEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDatabase: BeerDatabase,
    private val beerApi: BeerApi,
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val beerDtos = beerApi.getBeers(page = loadKey, pageCount = state.config.pageSize)

            beerDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    beerDatabase.beerDao().clearAll()
                }
                val beerEntities = beerDtos.map { it.toBeerEntity() }
                beerDatabase.beerDao().upsertAll(beerEntities)
            }
            MediatorResult.Success(beerDtos.isEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}
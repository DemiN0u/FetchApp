package com.test.fetchapp.data.datasources

import com.test.fetchapp.data.Item
import com.test.fetchapp.data.api.ItemsApiService
import com.test.fetchapp.domain.datasources.RemoteItemsDataSource
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteItemsDataSourceImpl @Inject constructor(private val service: ItemsApiService): RemoteItemsDataSource {

    override suspend fun fetchItems(): Result<List<Item>> {
        try {
            val response = service.fetchItems()
            return Result.success(response)
        } catch (ex: HttpException) {
            return Result.failure(ex)
        } catch (ex: UnknownHostException) {
            return Result.failure(ex)
        }
    }
}
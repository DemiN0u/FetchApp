package com.test.fetchapp.domain.repository

import com.test.fetchapp.domain.datasources.RemoteItemsDataSource

class ItemsRepository(private val dataSource: RemoteItemsDataSource) {

    suspend fun fetchItems() = dataSource.fetchItems()

}
package com.test.fetchapp.domain.datasources

import com.test.fetchapp.data.Item

interface RemoteItemsDataSource {

    suspend fun fetchItems(): Result<List<Item>>

}
package com.test.fetchapp.domain.usecases

import com.test.fetchapp.data.GroupedItem
import com.test.fetchapp.domain.mappers.filterEmptyNames
import com.test.fetchapp.domain.mappers.toSortedGroupedItems
import com.test.fetchapp.domain.repository.ItemsRepository

class FetchGroupedItemsUseCase(private val repository: ItemsRepository) {

    suspend fun fetchGroupedItems(): Result<List<GroupedItem>> {
        try {
            val items = repository.fetchItems().getOrThrow()
            return Result.success(items.filterEmptyNames().toSortedGroupedItems())
        } catch (ex: Exception) {
            return Result.failure(ex)
        }
    }

}
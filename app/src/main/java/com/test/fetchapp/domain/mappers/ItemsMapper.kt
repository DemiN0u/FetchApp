package com.test.fetchapp.domain.mappers

import com.test.fetchapp.data.GroupedItem
import com.test.fetchapp.data.Item

fun List<Item>.toSortedGroupedItems() =
    this.groupBy { it.listId }
        .map { entry ->
            GroupedItem(
                entry.key,
                entry.value.sortedWith(compareBy({ it.name?.length }, { it.name })) //comparator by length used for sorting in right lexicographical order
            )
        }
        .sortedBy { it.id }

fun List<Item>.filterEmptyNames() = this.filterNot { it.name.isNullOrEmpty() }
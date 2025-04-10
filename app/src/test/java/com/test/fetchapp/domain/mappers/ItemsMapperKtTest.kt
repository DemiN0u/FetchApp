package com.test.fetchapp.domain.mappers

import com.test.fetchapp.data.GroupedItem
import com.test.fetchapp.data.Item
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemsMapperKtTest {

    private val item1 = Item (id = 755L, listId = 2, name = "")
    private val item2 = Item (id = 203L, listId = 2, name = "Item 203")
    private val item3 = Item (id = 684L, listId = 1, name = "Item 684")
    private val item4 = Item (id = 276L, listId = 1, name = "Item 276")

    @Test
    fun toSortedGroupedItems() {
        val listItems = listOf(item2, item3, item4)
        val expected = listOf(GroupedItem(id = 1, subItems = listOf(item4, item3)), GroupedItem(id = 2, subItems = listOf(item2)))
        val grouped = listItems.toSortedGroupedItems()
        assertThat<List<GroupedItem>>("", grouped, equalTo(expected))
    }

    @Test
    fun filterEmptyNames() {
        var listItems = listOf(item1, item2, item3, item4)
        listItems = listItems.filterEmptyNames()
        assertThat(listItems, not(hasItem(item1)))
    }

}
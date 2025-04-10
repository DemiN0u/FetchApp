package com.test.fetchapp.data.api

import com.test.fetchapp.data.Item
import retrofit2.http.GET

interface ItemsApiService {

    @GET("hiring.json")
    suspend fun fetchItems(): List<Item>

}
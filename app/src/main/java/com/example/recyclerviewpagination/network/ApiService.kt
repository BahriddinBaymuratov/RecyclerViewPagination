package com.example.recyclerviewpagination.network

import com.example.recyclerviewpagination.model.Item
import com.example.recyclerviewpagination.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.END_POINT)
    fun getUsers(
        @Query("page") page: Int
    ): Call<Item>
}
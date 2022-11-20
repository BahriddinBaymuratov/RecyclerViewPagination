package com.example.recyclerviewpagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpagination.adapter.PagingAdapter
import com.example.recyclerviewpagination.adapter.RvScrollingListener
import com.example.recyclerviewpagination.databinding.ActivityMainBinding
import com.example.recyclerviewpagination.model.Item
import com.example.recyclerviewpagination.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var paginationAdapter: PagingAdapter

    lateinit var linearLayoutManager: LinearLayoutManager

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var TOTAL_PAGES = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        linearLayoutManager = LinearLayoutManager(this)

        paginationAdapter = PagingAdapter()
        binding.recyclerView.addOnScrollListener(object : RvScrollingListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = paginationAdapter

        loadFirstPage()
    }

    private fun loadFirstPage() {
        RetroInstance.retroInstance().getUsers(currentPage).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    binding.progressBar.isVisible = false
                    paginationAdapter.addUserList(
                        response.body()?.data?.toMutableList() ?: mutableListOf()
                    )

                    if (currentPage <= TOTAL_PAGES) {
                        paginationAdapter.onLoading()
                    } else {
                        isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }
        })
    }

    private fun loadNextPage() {
        RetroInstance.retroInstance().getUsers(currentPage).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    paginationAdapter.offLoading()
                    isLoading = false

                    paginationAdapter.addUserList(
                        response.body()?.data?.toMutableList() ?: mutableListOf()
                    )

                    if (currentPage <= TOTAL_PAGES) {
                        paginationAdapter.onLoading()
                    } else {
                        isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.d("@@@", t.message.toString())
            }
        })
    }
}
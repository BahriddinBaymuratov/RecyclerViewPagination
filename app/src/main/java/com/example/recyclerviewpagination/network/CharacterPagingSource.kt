package com.example.recyclerviewpagination.network

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recyclerviewpagination.model.CharacterData
import com.example.recyclerviewpagination.util.Constants.FIRST_PAGE_INDEX
import java.lang.Exception

class CharacterPagingSource(private val apiService: ApiService) :
    PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getAllCharacters(nextPage)
            var nextPageNumber: Int? = null
            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            LoadResult.Page(data = response.results, prevKey = null, nextKey = nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
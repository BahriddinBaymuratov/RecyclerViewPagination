package com.example.recyclerviewpagination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recyclerviewpagination.model.CharacterData
import com.example.recyclerviewpagination.network.CharacterPagingSource
import com.example.recyclerviewpagination.network.RetroInstance
import kotlinx.coroutines.flow.Flow

class PagingViewModel : ViewModel() {
    private val apiService = RetroInstance.retroInstance()
    fun getCharacterList(): Flow<PagingData<CharacterData>> {
        return Pager(config = PagingConfig(pageSize = 34, maxSize = 200),
            pagingSourceFactory = { CharacterPagingSource(apiService) }).flow.cachedIn(
            viewModelScope
        )
    }
}
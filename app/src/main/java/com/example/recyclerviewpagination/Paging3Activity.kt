package com.example.recyclerviewpagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpagination.adapter.Paging3Adapter
import com.example.recyclerviewpagination.databinding.ActivityPagin3Binding
import com.example.recyclerviewpagination.viewmodel.PagingViewModel
import kotlinx.coroutines.flow.collectLatest

class Paging3Activity : AppCompatActivity() {
    private val binding by lazy { ActivityPagin3Binding.inflate(layoutInflater) }
    private lateinit var paging3Adapter: Paging3Adapter
    private lateinit var viewModel: PagingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PagingViewModel::class.java]
        paging3Adapter = Paging3Adapter()
        initViews()
    }

    private fun initViews() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@Paging3Activity)
            adapter = paging3Adapter
            addItemDecoration(
                DividerItemDecoration(
                    this@Paging3Activity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
        setupVm()
    }

    private fun setupVm() {
        lifecycleScope.launchWhenStarted {
            viewModel.getCharacterList().collectLatest {
                binding.prb.isVisible = false
                paging3Adapter.submitData(it)
                Log.d("@@@", it.toString())
            }
        }
    }
}
package com.muchbeer.ktorplug.presentation.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.muchbeer.ktorplug.R
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.databinding.FragmentPostsBinding
import com.muchbeer.ktorplug.presentation.adapter.PostAdapter
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.utility.onQueryTextChanged
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewFragment : Fragment(R.layout.fragment_posts) {

    private lateinit var binding : FragmentPostsBinding
    private val viewModel : PostViewModel by viewModels()
    private lateinit var postAdapter : PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPostsBinding.bind(view)
        postAdapter = PostAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = postAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            searchPaps.editText?.doOnTextChanged { onTextChange, _, _, _ ->
                viewModel.setSearchQuery(onTextChange.toString())
                logs(TAG, "tHE value search is : ${onTextChange.toString()}")
            }
        }

        easyViewPostData()

        setHasOptionsMenu(true)
    }

    private fun easyViewPostData() {
            collectStateFlow(viewModel.searchListStateData) { dataState ->

                when (dataState) {
                    is DataState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        logs(TAG, dataState.error)
                    }
                    is DataState.ErrorException -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        logs(TAG, dataState.exception.message.toString())
                    }
                    DataState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is DataState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        postAdapter.submitList(dataState.data)

                    }
                }.exhaustive
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_posts, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.setSearchQuery(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
                     R.id.action_search -> {

                         true
                     } else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        private val TAG = ViewFragment::class.simpleName.toString()
    }
}
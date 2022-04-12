package com.muchbeer.ktorplug.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.muchbeer.ktorplug.R
import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.databinding.FragmentPostsBinding
import com.muchbeer.ktorplug.presentation.adapter.PostAdapter
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        }
        easyViewPostData()

    }
    private fun easyViewPostData() {
            collectStateFlow(viewModel.retrievePost) { dataState ->

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
                        postAdapter.submitList(dataState.data.toMutableList())
                    }
                }.exhaustive
            }
    }


    companion object {
        private val TAG = ViewFragment::class.simpleName.toString()
    }
}
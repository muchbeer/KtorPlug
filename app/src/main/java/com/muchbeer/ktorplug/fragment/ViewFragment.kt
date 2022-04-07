package com.muchbeer.ktorplug.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.muchbeer.ktorplug.*
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.databinding.FragmentViewBinding
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.serializer

@AndroidEntryPoint
class ViewFragment : Fragment() {

    private lateinit var binding : FragmentViewBinding
    private val viewModel : PostViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentViewBinding.inflate(inflater, container , false)

        binding.btnView.setOnClickListener {
          //  viewPostedData()
            easyViewPostData()
        }
        return binding.root
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun easyViewPostData() {

            collectActivityFlow(viewModel.retrievePost) { dataState ->
                Log.d("ViewFragment", "Enter the collect")
                when (dataState) {
                    is DataState.Error -> logs(TAG,  dataState.error)
                    is DataState.ErrorException -> logs(TAG,  dataState.exception.message.toString())
                    DataState.Loading -> requireContext().toastMsg("Loading...")
                    is DataState.Success -> {
                        logPrettyJson(dataModel = dataState.data)
                    }
                }.exhaustive
            }
    }

    companion object {
        private val TAG = ViewFragment::class.simpleName.toString()
    }
}
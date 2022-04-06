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
import com.muchbeer.ktorplug.R
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.databinding.FragmentViewBinding
import com.muchbeer.ktorplug.exhaustive
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
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
            viewPostedData()
        }
        return binding.root
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun viewPostedData() {

        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.retrievePost.collect{ dataState ->
                    when(dataState){
                        is DataState.Error -> showError(dataState.error)
                        is DataState.ErrorException -> showError(dataState.exception.message.toString())
                        DataState.Loading -> showError("Loading...")
                        is DataState.Success -> {

                            val postsSerializer = ListSerializer(PostResponse.serializer())
                            val json = Json.encodeToString(postsSerializer, dataState.data)
                            Log.d("ViewFragment", "the fetch data is $json}")
                   /*         for (datum in dataState.data) {
                                Log.d("ViewFragment", "the fetch data is :" +
                                        " ${datum.title}")
                               }*/
                            }

                    }.exhaustive
                }
            }
        }
    }

    private fun showError(msgError : String) {
        Toast.makeText(requireContext(), msgError, Toast.LENGTH_LONG).show()
    }
}
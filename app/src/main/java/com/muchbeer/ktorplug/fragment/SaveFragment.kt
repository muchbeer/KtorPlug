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
import com.muchbeer.ktorplug.*
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.databinding.FragmentSaveBinding
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@AndroidEntryPoint
class SaveFragment : Fragment() {

    private lateinit var binding : FragmentSaveBinding
    private val viewModel : PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSaveBinding.inflate(inflater, container, false)

        val randamInt = (1000..2000).random()

        binding.btnSend.setOnClickListener {
            sendPostData(
                PostRequest(
                    body = binding.edtBody.text.toString(),
                    title = binding.edtTitle.text.toString(),
                    userId = randamInt
                )
            )
        }


        return binding.root
    }
    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun sendPostData(request: PostRequest) {

        collectActivityFlow( viewModel.sendStatus(request)) {  dataState ->

            when(dataState){
                is DataState.Error -> logs(TAG,  dataState.error)
                is DataState.ErrorException -> logs(TAG,  dataState.exception.message.toString())
                DataState.Loading -> requireContext().toastMsg("Loading...")
                is DataState.Success -> { logPrettyJson(dataModel = dataState.data) }
            }.exhaustive
        }
    }

    companion object {
        private val TAG = SaveFragment::class.simpleName.toString()
    }
}
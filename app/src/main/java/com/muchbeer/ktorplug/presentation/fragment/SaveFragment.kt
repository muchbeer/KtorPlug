package com.muchbeer.ktorplug.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.databinding.FragmentSaveBinding
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logPrettyJson
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.utility.toastMsg
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFragment : Fragment() {

    private lateinit var binding : FragmentSaveBinding
    private val viewModel : PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSaveBinding.inflate(inflater, container, false)

        val randomInt = (1000..2000).random()

        binding.btnSend.setOnClickListener {
            sendPostData(
                PostRequestDto(
                    body = binding.edtBody.text.toString(),
                    title = binding.edtTitle.text.toString(),
                    userId = randomInt
                )
            )
        }

        binding.btnSaveDataStore.setOnClickListener {
            viewModel.saveFullname(binding.edtDataStore.text.toString())
        }
        return binding.root
    }


    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun sendPostData(request: PostRequestDto) {

        collectStateFlow( viewModel.sendStatus(request)) { dataState ->
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
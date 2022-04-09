package com.muchbeer.ktorplug.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.databinding.FragmentViewBinding
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logPrettyJson
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.utility.toastMsg
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewFragment : Fragment() {

    private lateinit var binding : FragmentViewBinding
    private val viewModel : PostViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewBinding.inflate(inflater, container , false)

        binding.btnView.setOnClickListener {
          //  viewPostedData()
            easyViewPostData()
        }
        return binding.root
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun easyViewPostData() {

            collectStateFlow(viewModel.retrievePost) { dataState ->
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
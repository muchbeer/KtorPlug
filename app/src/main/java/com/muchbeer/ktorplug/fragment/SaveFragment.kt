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
import com.muchbeer.ktorplug.R
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.databinding.FragmentSaveBinding
import com.muchbeer.ktorplug.exhaustive
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sendStatus(request).collect{ dataState ->
                    when(dataState){
                        is DataState.Error -> showError(dataState.error)
                        is DataState.ErrorException -> showError(dataState.exception.message.toString())
                        DataState.Loading -> showError("Loading......")
                        is DataState.Success -> {
                            Log.d("SaveFragment", "the request data response is :" +
                                    " ${dataState.data!!.body}")
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
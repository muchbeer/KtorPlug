package com.muchbeer.ktorplug.presentation.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.muchbeer.ktorplug.*
import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.databinding.FragmentImageBinding
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.collectflow.collectFlowActivity
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logPrettyJson
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.utility.toastMsg
import com.muchbeer.ktorplug.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class ImageFragment : Fragment() {

    private lateinit var binding : FragmentImageBinding
    private val viewModel : PostViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var selectedImageFile : File? = null

    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess->
        if (isSuccess) {
            selectedImageUri?.let { uri ->
                binding.imageView.setImageURI(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentImageBinding.inflate(inflater, container, false)

        binding.apply {
            btnTakePhoto.setOnClickListener {  takeImage()  }

            btnUpload.setOnClickListener {
               collectFlowActivity(viewModel.retrieveFullName)  { fullname->
                   logs(TAG, "tHE saved value is $fullname")
               }
               // uploadImageToServer()
            }
        }
        return binding.root
    }

    private fun takeImage() {
        getFileUri("muchbeer").let { uri ->
            selectedImageUri = uri
            takePhoto.launch(uri)
        }

    }

    private fun getFileUri(fileName: String): Uri {
        val tmpFile = File.createTempFile(fileName, ".png",  requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        logs(TAG, "tHE name of the file is :${tmpFile.absolutePath}" )
        selectedImageFile = tmpFile
        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    private fun uploadImageToServer() {

        if (selectedImageUri == null) {
           Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_LONG).show()
            return
        }

        collectStateFlow(viewModel.uploadImage(selectedImageFile!!)) { imageDataState->
                when(imageDataState) {
                    is DataState.Error -> logs(TAG,  imageDataState.error)
                    is DataState.ErrorException -> logs(TAG,  imageDataState.exception.message.toString())
                    DataState.Loading -> requireContext().toastMsg("Loading...")
                    is DataState.Success -> {
                        logPrettyJson(dataModel = imageDataState.data)
                    }
                }.exhaustive
        }

    }

    companion object {
        private val TAG = ImageFragment::class.simpleName.toString()
    }
}
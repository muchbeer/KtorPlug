package com.muchbeer.ktorplug.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.databinding.FragmentSaveBinding
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.logPrettyJson
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.viewmodel.GrievanceVMAlt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveAltFragment : Fragment() {

    private lateinit var binding : FragmentSaveBinding
    private val viewModel : GrievanceVMAlt by viewModels()
    private lateinit var valuationNo : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSaveBinding.inflate(inflater, container, false)

        binding.valutationNumber.setOnClickListener {
            valuationNo = (100..200).random().toString()
            Toast.makeText(requireContext(),
                "The valuation number is : $valuationNo", Toast.LENGTH_LONG).show()     }

        insertGrievance()
        viewGrievance()
        return binding.root
    }


    private fun insertGrievance() {

        val aGrievanceGeneral = AgrievanceAltEntity(
            primary_key = "A02",
            user_name = "muchbeer",
            valuation_number ="V02"
        )
        binding.btnAgrievance.setOnClickListener {
          val successAgri =  viewModel.insertAgrievEntryAlt(agriev = aGrievanceGeneral)
            logs(TAG, "tHE cGriev value is : $successAgri")
        }

        val cGriev = CgrievTotalAltEntity(
            agreetosign = "no",
            full_name = "Gadiel",
            a_username = "muchbeer",
            a_primary_key = "A02"
        )
        binding.btnCGrievance.setOnClickListener {
            logs(TAG, "tHE value attached is : ${logPrettyJson(cGriev)}")
            val successNumber =  viewModel.insertCgrievEntryAlt(cGriev)
            logs(TAG, "tHE cGriev value is : $successNumber")
        }

        binding.btnOpenCamera.setOnClickListener {
            val dAttchPhoto = DpapAttachAltEntity(
                file_name = "gadielFile",
                c_fullname = "Gadiel",
                image_status = IMAGESTATUS.AVAILABLE,
            )
            viewModel.insertDattachEntryAlt(dAttchPhoto)
            logs(TAG, "tHE Dattach value is : $dAttchPhoto")
        }
    }

    private fun viewGrievance() {
        binding.btnDisplayAOnly.setOnClickListener {
            collectStateFlow(viewModel.allAgrienceEntryAlt) {
                logPrettyJson(it)
            }
        }

        binding.btnViewCGrievance.setOnClickListener {
            collectStateFlow(viewModel.allCgrievGeneral) {
                logPrettyJson(it)
            }
        }
        binding.btnViewDAttach.setOnClickListener {
            collectStateFlow(viewModel.retrieveAllDattachWithStatus(IMAGESTATUS.AVAILABLE)) { dAttach ->
                dAttach.forEach {
                    viewModel.updateDgrievanceAlt(it.copy(image_status = IMAGESTATUS.SUCCESSFUL))
                }
            }
        }
    }

    companion object {
        private val TAG = SaveAltFragment::class.simpleName.toString()
    }
}
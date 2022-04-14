package com.muchbeer.ktorplug.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.databinding.FragmentSaveBinding
import com.muchbeer.ktorplug.utility.collectflow.collectStateFlow
import com.muchbeer.ktorplug.utility.logPrettyJson
import com.muchbeer.ktorplug.utility.logs
import com.muchbeer.ktorplug.viewmodel.GrievaneViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFragment : Fragment() {

    private val bpapsTopList = mutableListOf<BpapDetailEntity>()
    private val cgrievList = mutableListOf<CgrievTotalEntity>()
    private val dAttachmentList =  mutableListOf<DpapAttachEntity>()

    private lateinit var valuationNo : String

    private lateinit var binding : FragmentSaveBinding
    private val viewModel : GrievaneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSaveBinding.inflate(inflater, container, false)

       /* binding.btnSaveDataStore.setOnClickListener {
            viewModel.saveFullname(binding.edtDataStore.text.toString())
        }*/

        binding.valutationNumber.setOnClickListener {
            valuationNo = (100..200).random().toString()
            Toast.makeText(requireContext(),
                "The valuation number is : $valuationNo", Toast.LENGTH_LONG).show()     }

        collectStateFlow(viewModel.getAllCGrievWithSameUsername("muchbeer")) {
            cgrievList.clear()
            cgrievList.addAll(it)
        }

        collectStateFlow(viewModel.getAllDAttachByStatus(imagestatus = IMAGESTATUS.SUCCESSFUL)) {
            dAttachmentList.clear()
            dAttachmentList.addAll(it)
        }

        collectStateFlow(viewModel.getAllBGrievWithSameUsername("muchbeer")) {
            bpapsTopList.clear()
            bpapsTopList.addAll(it)
        }

        insertGrievance()
        clickButton()
        return binding.root
    }



    private fun insertGrievance() {
        val dAttach = DpapAttachEntity(
            file_name = "gadielFilename",
            url_name = "gadielUrl",
            c_fullname = "Gadiel"
        )

        binding.btnDAttach.setOnClickListener {
            viewModel.insertDattach(dattach = dAttach)
            logs(TAG, "tHE cGriev value is : $dAttach")
        }

        val cGriev = CgrievTotalEntity(
            agreetosign = "yes",
            full_name = "Gadiel",
            a_username = "muchbeer",
            attachments = dAttachmentList
        )
        binding.btnCGrievance.setOnClickListener {
            logs(TAG, "tHE value attached is : ${logPrettyJson(dAttachmentList)}")
            viewModel.insertCgriev(cGriev)
            logs(TAG, "tHE cGriev value is : $cGriev")
        }

        val bAttachTop = BpapDetailEntity(
            "B02",
            a_username = "muchbeer",
            grievance = cgrievList
        )

        binding.btnBpapDetail.setOnClickListener {
            viewModel.updatCgrievance(cGriev.copy(attachments = dAttachmentList))
            viewModel.insertBpapsEntry(bAttachTop)

            logs(TAG, "tHE cGriev value is : $bAttachTop")
        }

        val aGrievanceGeneral = AgrievanceEntity(
            primary_key = "A01",
            user_name = "muchbeer",
            papdetails = listOf(bAttachTop)
        )
        binding.btnAgrievance.setOnClickListener {
            viewModel.insertAgrievEntry(agriev = aGrievanceGeneral)
            logs(TAG, "tHE cGriev value is : $aGrievanceGeneral")
        }
    }

    private fun clickButton() {
        binding.btnDisplayAOnly.setOnClickListener {

            collectStateFlow(viewModel.allAgrienceEntry) {
               logPrettyJson(it)
              //  viewModel.displayApiModel(it)
            }
        }

        binding.btnViewBOnly.setOnClickListener {
            collectStateFlow(viewModel.allBpapsEntry) {
                logPrettyJson(it)
            }
        }

        binding.btnViewCGrievance.setOnClickListener {
            collectStateFlow(viewModel.allCpapsDetailEntry) {
                logPrettyJson(it)     }
        }

        binding.btnViewDAttach.setOnClickListener {
            collectStateFlow(viewModel.allDAttachmentEntry) {
                logPrettyJson(it)  }
        }
    }
    companion object {
        private val TAG = SaveFragment::class.simpleName.toString()
    }
}
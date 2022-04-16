package com.muchbeer.ktorplug.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.utility.logs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrievaneViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel(){
    val allAgrienceEntry = repository.retrieveAllAgrievanceEntry().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    val allBpapsEntry = repository.retrieveAllBpapsEntry().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    val allCpapsDetailEntry = repository.retrieveAllCGrievanceEntry().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    val allDAttachmentEntry = repository.retrieveAllDpapsEntry().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    fun getAllBGrievWithSameUsername(username : String) : StateFlow<List<BpapDetailEntity>> {
        return repository.retrieveAllBGrievWithUsername(username = username).stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )     }

    fun getAllCGrievWithSameUsername(username : String) : StateFlow<List<CgrievTotalEntity>> {
        return repository.retrieveAllCGrievanceWithUsername(username).stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )    }

    fun getAllDAttachWithfullName(fullName: String) : StateFlow<List<DpapAttachEntity>> {
        return repository.retrieveDAddAttachWithfullName(fullName).stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )
    }

    fun insertAgrievEntry(agriev : AgrievanceEntity) = viewModelScope.launch {
        val aGrieValue = repository.insertAgrievEntry(agriev)
        if (aGrieValue >= 0) {
            logs(TAG, "Inserted AgrienceEntity successfully record no: $aGrieValue")
        }
    }

    fun insertBpapsEntry(bpaps: BpapDetailEntity) = viewModelScope.launch {
        val bPapEntryValue =  repository.insertBpapDetail(bpapsDetail = bpaps)
        if (bPapEntryValue >= 0) {
            logs(TAG, "Inserted BpapDetailModel successfully record no: $bPapEntryValue")
        }
    }

    fun insertCgriev(cgriev : CgrievTotalEntity) = viewModelScope.launch {
        val cGrievDetailValue =  repository.insertCgrievDetail(cgriev = cgriev)
        if (cGrievDetailValue >= 0) {
            logs(TAG, "Inserted CGrievance successfully record no: $cGrievDetailValue")    }
    }

    fun insertDattach(dattach : DpapAttachEntity) = viewModelScope.launch {
        val dAttachValue =  repository.insertDattach(dattach = dattach)
        if (dAttachValue >= 0) {
            Log.d(TAG, "Inserted DpapAttachEntity successfully record no: $dAttachValue")    }
    }


    fun updatCgrievance(cgriev: CgrievTotalEntity) = viewModelScope.launch {
        repository.updateCgrievance(cgriev)  }

    fun uploadFileToserver () = viewModelScope.launch{
        repository.workManagerUploads()    }

    companion object {
        private val TAG = GrievaneViewModel::class.simpleName.toString()
    }
}
package com.muchbeer.ktorplug.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.utility.logs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrievanceVMAlt @Inject constructor(
    private val repository: PostRepository
) : ViewModel(){

    val allAgrienceEntryAlt = repository.retrieveAgrieveWithCgrievAndDattach().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    val allCgrievGeneral = repository.retrieveCgrievanceAlt().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )
    fun insertAgrievEntryAlt(agriev : AgrievanceAltEntity) = viewModelScope.launch {
        val aGrieValue = repository.insertAgrievEntryAlt(agriev)
        if (aGrieValue >= 0) {
            logs(TAG, "Inserted AgrienceEntity successfully record no: $aGrieValue")
        }
    }

    fun insertCgrievEntryAlt(cgriev : CgrievTotalAltEntity) = viewModelScope.launch {
        val cGrievDetailValue =  repository.insertCgrievDetailAlt(cgriev = cgriev)
        if (cGrievDetailValue >= 0) {
            logs(TAG, "Inserted CGrievance successfully record no: $cGrievDetailValue")    }
    }

    fun insertDattachEntryAlt(dattach : DpapAttachAltEntity) = viewModelScope.launch {
        val dAttachValue =  repository.insertDattachAlt(dattach = dattach)
        if (dAttachValue >= 0) {
            Log.d(TAG, "Inserted DpapAttachEntity successfully record no: $dAttachValue")    }
    }

    fun updatCgrievanceAlt(cgriev: CgrievTotalAltEntity) = viewModelScope.launch {
        repository.updateCgrievanceAlt(cgriev)  }

    fun retrieveAllDattachWithStatus(status: IMAGESTATUS) : StateFlow<List<DpapAttachAltEntity>> {
      return  repository.retrieveDattachByStatusAlt(status).stateIn(
          initialValue = emptyList(),
          scope = viewModelScope,
          started = SharingStarted.WhileSubscribed(5000)
      )
    }

    fun uploadFileToserver () = viewModelScope.launch{
        repository.workManagerUploads()    }

    fun updateDgrievanceAlt(dattach : DpapAttachAltEntity) = viewModelScope.launch {
        repository.updateDattachmentAlt(dattach)
    }
     companion object {
        private val TAG = GrievanceVMAlt::class.simpleName.toString()
    }
}
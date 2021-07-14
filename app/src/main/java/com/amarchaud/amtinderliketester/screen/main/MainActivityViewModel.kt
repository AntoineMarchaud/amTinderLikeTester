package com.amarchaud.amtinderliketester.screen.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.amarchaud.amtinderliketester.domain.api.ApiProfile
import com.amarchaud.amtinderliketester.domain.api.ApiProfiles
import com.amarchaud.amtinderliketester.domain.repository.ITestRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

sealed class Status {
    data class StatusOk(val profiles : ApiProfiles?) : Status()
    data class StatusError(val message : ResponseBody?) : Status()
}


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val app: Application,
    private val testRepo: ITestRepo
) : AndroidViewModel(app) {

    private var alreadyAccepted : HashSet<ApiProfile> = HashSet()

    private var _numberToDisplay = MutableLiveData(0)
    val numberToDisplay: LiveData<Int>
        get() = _numberToDisplay

    private var _apiProfiles = MutableLiveData<Status>()
    val apiProfiles: LiveData<Status>
        get() = _apiProfiles

    fun fetchProfiles() {
        viewModelScope.launch {
            val res = testRepo.getProfiles()
            if(res is Either.Right) {
                _apiProfiles.postValue(Status.StatusOk(res.value))
            } else if(res is Either.Left) {
                _apiProfiles.postValue(Status.StatusError(res.value))
            }
        }
    }

    private fun accept() {
        _numberToDisplay.value?.let {
            var currentValue: Int = it
            _numberToDisplay.postValue(++currentValue)
        }
    }

    fun manageItemAccepted(apiProfile: ApiProfile) {
        if (apiProfile.is_match) {
            if(!alreadyAccepted.contains(apiProfile)) {
                alreadyAccepted.add(apiProfile)
                accept()
            }
        }
    }
}
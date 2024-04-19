package com.sobu.baseapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import com.sobu.baseapplication.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val _statusButtonCreate = MutableLiveData<Boolean>()
    val statusButtonCreate: MutableLiveData<Boolean> get() = _statusButtonCreate


    private val _statusTurnServiceRecord = MutableLiveData<Boolean>()
    val statusTurnServiceRecord: MutableLiveData<Boolean> get() = _statusTurnServiceRecord





}
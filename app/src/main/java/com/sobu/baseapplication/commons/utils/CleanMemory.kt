package com.sobu.baseapplication.commons.utils

import com.sobu.baseapplication.commons.firebase.RemoteConstants

object CleanMemory {
    var isActivityRecreated = false
    fun clean() {
        RemoteConstants.reset()
    }

}
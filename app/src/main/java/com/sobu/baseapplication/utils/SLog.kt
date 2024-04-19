package com.sobu.baseapplication.utils

import android.util.Log
import com.sobu.baseapplication.BuildConfig

object SLog {

    fun d(tag: String, content: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, content)
        }
    }

    fun d(content: String) {
        if (BuildConfig.DEBUG) {
            Log.d("sobutag", content)
        }
    }

    fun dRelease(content: String) {
        Log.d("sobutag", content)
    }

    fun dRelease(tag: String, content: String) {
        Log.d("sobutag", content)
    }

}
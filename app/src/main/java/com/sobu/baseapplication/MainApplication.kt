package com.sobu.baseapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.sobu.baseapplication.commons.koin.modulesList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

//    var appOpen: AdmobAppOpen? = null

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initKoin()
//        appOpen = AdmobAppOpen(this)

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(modulesList)
        }
    }

    fun initLoadAd() {
        // to get test ads on this device."
        /*MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("05800821DC9AB108DA9E238C6B384359")).build()
        )

//        appOpen!!.fetchAd()



        MobileAds.initialize(this) { initializationStatus ->
            val statusMap =
                initializationStatus.adapterStatusMap
            for (adapterClass in statusMap.keys) {
                val status = statusMap[adapterClass]

            }

            // Start loading ads here...
        }*/
    }


}
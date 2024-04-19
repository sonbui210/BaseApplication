package com.sobu.baseapplication.commons.koin

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.sobu.baseapplication.commons.managers.InternetManager
import com.sobu.baseapplication.commons.preferences.SharedPreferenceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val managerModules = module {
    single { InternetManager(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }
}

private val utilsModules = module {
    single { SharedPreferenceUtils(androidContext().getSharedPreferences("MonitorBaby", Application.MODE_PRIVATE)) }
}

private val firebaseModule = module {
//    single { RemoteConfiguration(get(),androidContext().getSharedPreferences("monitorBaby_firebase_preferences", Application.MODE_PRIVATE)) }
}

private val applicationScope = CoroutineScope(SupervisorJob())

private val dbModule = module {


}

private val admobModule = module {
//    single { AdmobBanner() }
//    single { QueueLoadNative() }
//    single { AdmobNative() }
//    single { AdmobNativePreload() }
//    single { FrameAds() }
}


val modulesList = listOf(utilsModules, managerModules, firebaseModule, dbModule, admobModule)
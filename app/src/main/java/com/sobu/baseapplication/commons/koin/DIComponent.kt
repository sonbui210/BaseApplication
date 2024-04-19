package com.sobu.baseapplication.commons.koin

import com.sobu.baseapplication.commons.managers.InternetManager
import com.sobu.baseapplication.commons.preferences.SharedPreferenceUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

class DIComponent : KoinComponent {

    // Utils
    val sharedPreferenceUtils by inject<SharedPreferenceUtils>()

    // Managers
    val internetManager by inject<InternetManager>()

    // Remote Configuration
//    val remoteConfiguration by inject<RemoteConfiguration>()

    val utilsViewmodel = module {

    }

    // ViewModels

    //ads
//    val queueLoadNative by inject<QueueLoadNative>()
//    val admobPreLoadNative by inject<AdmobNativePreload>()
//    val admobNative by inject<AdmobNative>()
//    val admobBanner by inject<AdmobBanner>()
//    val frameAds by inject<FrameAds>()

}
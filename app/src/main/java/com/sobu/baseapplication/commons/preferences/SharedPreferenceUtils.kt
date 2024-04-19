package com.sobu.baseapplication.commons.preferences

import android.content.SharedPreferences
import com.sobu.baseapplication.utils.SharePreference.backgroundUriKey
import com.sobu.baseapplication.utils.SharePreference.billingRequireKey
import com.sobu.baseapplication.utils.SharePreference.firstTimeAskingPermission
import com.sobu.baseapplication.utils.SharePreference.isNotificationRemindKey
import com.sobu.baseapplication.utils.SharePreference.isShowFirstScreenKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingBABBLINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingBABY_INFANT_CRYKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingBABY_LAUGHTERKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingBELLY_LAUGHKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingBURPINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCHEWINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCHILDREN_PLAYINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCHILDREN_SHOUTKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCHILD_SINGINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCHILD_SPEECHKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCHUCKLE_CHORTLEKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCOUGHKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingCRYING_SOBBINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingFARTKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingGASPKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingGIGGLEKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingHICCUPKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingLAUGHTERKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingPANTKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSCREAMINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSHOUTKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSNEEZEKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSNICKERKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSNIFFKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSNORINGKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingSNORTKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingWAIL_MOANKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingWHEEZEKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingWHIMPERKey
import com.sobu.baseapplication.utils.SharePreference.isTrackingYELLKey
import com.sobu.baseapplication.utils.SharePreference.isTurnOnRecordSoundKey
import com.sobu.baseapplication.utils.SharePreference.selectedLanguageCodeKey


class SharedPreferenceUtils(private val sharedPreferences: SharedPreferences) {

    /* ---------- Billing ---------- */

    var isFirstTimeAskingPermission: Boolean
        get() = sharedPreferences.getBoolean(firstTimeAskingPermission, true)
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(firstTimeAskingPermission, value)
                apply()
            }
        }

    /* ---------- Permission ---------- */

    var isAppPurchased: Boolean
        get() = sharedPreferences.getBoolean(billingRequireKey, false)
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(billingRequireKey, value)
                apply()
            }
        }

    /* ---------- UI ---------- */

    var showFirstScreen: Boolean
        get() = sharedPreferences.getBoolean(isShowFirstScreenKey, true)
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isShowFirstScreenKey, value)
                apply()
            }
        }

    var selectedLanguageCode: String
        get() = sharedPreferences.getString(selectedLanguageCodeKey, "en") ?: "en"
        set(value) {
            sharedPreferences.edit().apply {
                putString(selectedLanguageCodeKey, value)
                apply()
            }
        }


    var backgroundUri: String
        get() = sharedPreferences.getString(backgroundUriKey, "") ?: ""
        set(value) {
            sharedPreferences.edit().apply {
                putString(backgroundUriKey, value)
                apply()
            }
        }

    var isTurnOnRecordSound: Boolean
        get() = sharedPreferences.getBoolean(isTurnOnRecordSoundKey, false) ?: false
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTurnOnRecordSoundKey, value)
                apply()
            }
        }


    var isTrackingCHILD_SPEECH: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCHILD_SPEECHKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCHILD_SPEECHKey, value)
                apply()
            }
        }

    var isTrackingBABBLING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingBABBLINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingBABBLINGKey, value)
                apply()
            }
        }


    var isTrackingSHOUT: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSHOUTKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSHOUTKey, value)
                apply()
            }
        }


    var isTrackingYELL: Boolean
        get() = sharedPreferences.getBoolean(isTrackingYELLKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingYELLKey, value)
                apply()
            }
        }

    var isTrackingCHILDREN_SHOUT: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCHILDREN_SHOUTKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCHILDREN_SHOUTKey, value)
                apply()
            }
        }

    var isTrackingSCREAMING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSCREAMINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSCREAMINGKey, value)
                apply()
            }
        }


    var isTrackingLAUGHTER: Boolean
        get() = sharedPreferences.getBoolean(isTrackingLAUGHTERKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingLAUGHTERKey, value)
                apply()
            }
        }


    var isTrackingBABY_LAUGHTER: Boolean
        get() = sharedPreferences.getBoolean(isTrackingBABY_LAUGHTERKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingBABY_LAUGHTERKey, value)
                apply()
            }
        }


    var isTrackingGIGGLE: Boolean
        get() = sharedPreferences.getBoolean(isTrackingGIGGLEKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingGIGGLEKey, value)
                apply()
            }
        }


    var isTrackingSNICKER: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSNICKERKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSNICKERKey, value)
                apply()
            }
        }


    var isTrackingBELLY_LAUGH: Boolean
        get() = sharedPreferences.getBoolean(isTrackingBELLY_LAUGHKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingBELLY_LAUGHKey, value)
                apply()
            }
        }


    var isTrackingCHUCKLE_CHORTLE: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCHUCKLE_CHORTLEKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCHUCKLE_CHORTLEKey, value)
                apply()
            }
        }


    var isTrackingCRYING_SOBBING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCRYING_SOBBINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCRYING_SOBBINGKey, value)
                apply()
            }
        }


    var isTrackingBABY_INFANT_CRY: Boolean
        get() = sharedPreferences.getBoolean(isTrackingBABY_INFANT_CRYKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingBABY_INFANT_CRYKey, value)
                apply()
            }
        }


    var isTrackingWHIMPER: Boolean
        get() = sharedPreferences.getBoolean(isTrackingWHIMPERKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingWHIMPERKey, value)
                apply()
            }
        }


    var isTrackingWAIL_MOAN: Boolean
        get() = sharedPreferences.getBoolean(isTrackingWAIL_MOANKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingWAIL_MOANKey, value)
                apply()
            }
        }


    var isTrackingCHILD_SINGING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCHILD_SINGINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCHILD_SINGINGKey, value)
                apply()
            }
        }


    var isTrackingWHEEZE: Boolean
        get() = sharedPreferences.getBoolean(isTrackingWHEEZEKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingWHEEZEKey, value)
                apply()
            }
        }


    var isTrackingSNORING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSNORINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSNORINGKey, value)
                apply()
            }
        }


    var isTrackingGASP: Boolean
        get() = sharedPreferences.getBoolean(isTrackingGASPKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingGASPKey, value)
                apply()
            }
        }


    var isTrackingPANT: Boolean
        get() = sharedPreferences.getBoolean(isTrackingPANTKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingPANTKey, value)
                apply()
            }
        }


    var isTrackingSNORT: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSNORTKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSNORTKey, value)
                apply()
            }
        }


    var isTrackingCOUGH: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCOUGHKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCOUGHKey, value)
                apply()
            }
        }


    var isTrackingSNEEZE: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSNEEZEKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSNEEZEKey, value)
                apply()
            }
        }


    var isTrackingSNIFF: Boolean
        get() = sharedPreferences.getBoolean(isTrackingSNIFFKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingSNIFFKey, value)
                apply()
            }
        }


    var isTrackingCHEWING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCHEWINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCHEWINGKey, value)
                apply()
            }
        }


    var isTrackingBURPING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingBURPINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingBURPINGKey, value)
                apply()
            }
        }


    var isTrackingHICCUP: Boolean
        get() = sharedPreferences.getBoolean(isTrackingHICCUPKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingHICCUPKey, value)
                apply()
            }
        }


    var isTrackingFART: Boolean
        get() = sharedPreferences.getBoolean(isTrackingFARTKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingFARTKey, value)
                apply()
            }
        }


    var isTrackingCHILDREN_PLAYING: Boolean
        get() = sharedPreferences.getBoolean(isTrackingCHILDREN_PLAYINGKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isTrackingCHILDREN_PLAYINGKey, value)
                apply()
            }
        }

    fun setIsTrackingIdentify(key: String, value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getIsTrackingIdentify(key: String) : Boolean = sharedPreferences.getBoolean(key, true) ?: true



    var isNotificationRemind: Boolean
        get() = sharedPreferences.getBoolean(isNotificationRemindKey, true) ?: true
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(isNotificationRemindKey, value)
                apply()
            }
        }
}
package com.sobu.baseapplication.commons.firebase

import com.sobu.baseapplication.BuildConfig

object RemoteConstants {

    /**
     * Remote Config keys
     */
    fun inter_splash_key(): String {
        return if (BuildConfig.DEBUG) {
            "inter_splash_key_debug"
        } else {
            "inter_splash_key"
        }
    }

    //ad bottom
    fun ad_bottom_select_language(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_select_language_debug"
        } else {
            "ad_bottom_select_language"
        }
    }

    fun ad_bottom_introduce(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_introduce_debug"
        } else {
            "ad_bottom_introduce"
        }
    }

    fun ad_center_home(): String {
        return if (BuildConfig.DEBUG) {
            "ad_center_home_debug"
        } else {
            "ad_center_home"
        }
    }

    fun ad_bottom_history(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_history_debug"
        } else {
            "ad_bottom_history"
        }
    }

    fun ad_bottom_setting_main(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_setting_main_debug"
        } else {
            "ad_bottom_setting_main"
        }
    }

    fun ad_bottom_setting_in_setting(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_setting_in_setting_debug"
        } else {
            "ad_bottom_setting_in_setting"
        }
    }

    fun ad_top_select_background(): String {
        return if (BuildConfig.DEBUG) {
            "ad_top_select_background_debug"
        } else {
            "ad_top_select_background"
        }
    }

    fun ad_top_select_identify(): String {
        return if (BuildConfig.DEBUG) {
            "ad_top_select_identify_debug"
        } else {
            "ad_top_select_identify"
        }
    }

    fun ad_bottom_version(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_version_debug"
        } else {
            "ad_bottom_version"
        }
    }

    fun ad_bottom_instruct(): String {
        return if (BuildConfig.DEBUG) {
            "ad_bottom_instruct_debug"
        } else {
            "ad_bottom_instruct"
        }
    }

    fun ad_inter_home_to_history(): String {
        return if (BuildConfig.DEBUG) {
            "ad_inter_home_to_history_debug"
        } else {
            "ad_inter_home_to_history"
        }
    }

    fun ad_reward_turn_on(): String {
        return if (BuildConfig.DEBUG) {
            "ad_reward_turn_on_debug"
        } else {
            "ad_reward_turn_on"
        }
    }

    var rcvInterSplash: Int = 0
    var rcvNativeSplash: Int = 0
    var ad_bottom_select_language: String = "native"
    var ad_bottom_introduce: String = "native"
    var ad_center_home: String = "native"
    var ad_bottom_history: String = "native"
    var ad_bottom_setting_main: String = "native"
    var ad_bottom_setting_in_setting: String = "native"
    var ad_top_select_background: String = "native"
    var ad_top_select_identify: String = "native"
    var ad_bottom_version: String = "native"
    var ad_bottom_instruct: String = "native"


    var ad_inter_home_to_history: Boolean = true


    var ad_reward_turn_on: Boolean = true



    fun reset() {
        rcvInterSplash = 0
        rcvNativeSplash = 0
    }
}
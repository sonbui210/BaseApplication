package com.sobu.baseapplication.commons.firebase

object FirebaseUtils {

    private const val TAG_FIREBASE = "firebase_tag"

    /**
     *  Syntax:
     *      try {}
     *      catch(ex: Exception){
     *          ex.recordException("MainActivity > OnCreate > getList")
     *      }
     */

    fun Throwable.recordException(logTag: String) {

    }

    /**
     *  Syntax:
     *      EventsProvider.HOME_SCREEN.postFirebaseEvent()
     *      EventsProvider.START_BUTTON.postFirebaseEvent()
     */

    fun String.postFirebaseEvent() {

    }

    fun getDeviceToken() {
        // Add this 'id' in firebase AB testing console as a testing device

    }
}
package com.dailymotionplayerintegration

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.util.Log


class DmPlayerModule(reactContext: ReactApplicationContext): ReactContextBaseJavaModule(reactContext){

    override fun getName() = "DmPlayerModule";

    @ReactMethod fun createPlayer(name: String, location: String) {
        Log.d("CalendarModule", "Create event called with name: $name and location: $location")
    }
}
package com.dailymotionplayerintegration

import android.util.Log
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.webview.error.PlayerError
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp


class DailyMotionPlayerView : SimpleViewManager<PlayerView>() {

    override fun getName() = "DailyMotionPlayerView"

    private var dmPlayerView: PlayerView? = null
    private var playerId = "xioux"
    private var videoId = "x8npck3"

    @ReactProp(name = "videoId")
    fun setVideoId(view: PlayerView , newId: String?) {
        Log.d("DailymotionPlayer", "Insert videoId=${newId}")
    }

    override fun createViewInstance(reactContext: ThemedReactContext): PlayerView {

        Dailymotion.createPlayer(
                reactContext,
                playerId = playerId,
                videoId =videoId,
                playerSetupListener = object : Dailymotion.PlayerSetupListener {
                    override fun onPlayerSetupFailed(error: PlayerError) {
                        TODO("Not yet implemented")
                    }

                    override fun onPlayerSetupSuccess(player: PlayerView) {
                        dmPlayerView = player

                    }
                }
        )
        return dmPlayerView!!
    }


//
//    override fun createViewInstance(reactContext: ThemedReactContext): PlayerView {
//        Log.d("DailymotionPlayer", "Load videoId=${videoId}")
//
//        var player = PlayerView(reactContext);
//
////        player.loadContent(videoId=videoId,)
////        player.initialize({
////
////        }, videoId=videoId, playerSetupListener = object : Dailymotion.PlayerSetupListener {
////            override fun onPlayerSetupSuccess(player: PlayerView) {
////                Log.d(logTag, "Successfully created dailymotion player")
////
////                dmPlayerView = player
////            }
////
////            override fun onPlayerSetupFailed(error: PlayerError) {
////                Log.e(logTag, "Error while creating dailymotion player: ${error.message}")
////            }
////        })
//
////        Dailymotion.createPlayer(
////                context = reactContext,
////                playerId = playerId,
////                videoId = videoId,
////                playerSetupListener = object : Dailymotion.PlayerSetupListener {
////                    override fun onPlayerSetupSuccess(players: PlayerView) {
////                        Log.d(logTag, "Successfully created dailymotion player")
////
////                        player =players
////                    }
////
////                    override fun onPlayerSetupFailed(error: PlayerError) {
////                        Log.e(logTag, "Error while creating dailymotion player: ${error.message}")
////                    }
////                }
////        )
//
//        return player;
//    }
//
//

}
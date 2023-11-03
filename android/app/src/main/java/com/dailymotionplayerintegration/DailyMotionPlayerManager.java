package com.dailymotionplayerintegration;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

class DailyMotionPlayerManager extends SimpleViewManager<FrameLayout> {


    @NonNull
    @Override
    public String getName() {
        return "DailyMotionPlayerView";
    }

    @Override
    protected FrameLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
//        LayoutInflater inflater = (LayoutInflater) reactContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.activity_main,null);

        return new DailyMotionPlayerView(reactContext);
    }

    @ReactProp(name = "videoId")
    public void setPropVideoId(DailyMotionPlayerView view, @Nullable String param) {
        view.setVideoId(param);
    }

    @ReactProp(name = "playerId")
    public void setPlayerId(DailyMotionPlayerView view, @Nullable String param) {
        view.setPlayerId(param);
    }
}

package com.dailymotionplayerintegration.DailymotionPlayer;

import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

class DailymotionPlayerController extends SimpleViewManager<FrameLayout> {


    @NonNull
    @Override
    public String getName() {
        return "DailymotionPlayerNative";
    }

    @Override
    protected FrameLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new DailymotionPlayerNativeView(reactContext);
    }

    @ReactProp(name = "videoId")
    public void setPropVideoId(DailymotionPlayerNativeView view, @Nullable String param) {
        view.setVideoId(param);
    }

    @ReactProp(name = "playerId")
    public void setPlayerId(DailymotionPlayerNativeView view, @Nullable String param) {
        view.setPlayerId(param);
    }
}

package com.dailymotionplayerintegration;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

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
}

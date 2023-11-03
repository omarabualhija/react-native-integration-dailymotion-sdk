# Dailymotion Android SDK Integration with React Native

<!-- Untuk melakukan integrasi Dailymotion Android Player SDK. Ada beberapa hal yang harus dilakukan, seperti:

- Membuat custom class menggunakan file Java/Kotlin.
- Membuat `DailyMotionPlayerManager.java`
- more... -->

## Registering the Package in MainApplication.java

1.  **Locate MainApplication.java:** In your React Native project, navigate to the `android/app/src/main/java/com/yourAppName` directory. Inside this directory, you'll find the `MainApplication.java` file.
2.  **Add Import Statements:**
    At the top of the MainApplication.java file, import the necessary classes.

    ```java
    import com.yourpackage.DailyPlayerPackage;
    // Replace 'yourpackage' with your actual package name
    ```

3.  Register the package
    Inside the `getPackages()` method in `MainApplication.java`, add an instance of your package to the list of packages.

    ```java
    @Override
    protected List<ReactPackage> getPackages() {
      @SuppressWarnings("UnnecessaryLocalVariable")
      List<ReactPackage> packages = new PackageList(this).getPackages();
      // Packages that cannot be autolinked yet can be added manually here, for example:
      packages.add(new DailyPlayerPackage());
      return packages;
    }

    ```

    Ensure that you're using your actual package name in place of `yourpackage`. This registration allows React Native to access the functionality provided by your `DailyPlayerPackage`.

## Creating the Package

navigate to the `android/app/src/main/java/com/yourAppName` directory. Inside this directory, create a class called `DailyPlayerPackage.kt`

```kt
package com.dailymotionplayerintegration


import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class DailyPlayerPackage: ReactPackage {
    override fun createViewManagers(reactContext: ReactApplicationContext): MutableList<ViewManager<out View, out ReactShadowNode<*>>> {
        return mutableListOf(
                DailyMotionPlayerManager()
        )
    }

    override fun createNativeModules(
            reactContext: ReactApplicationContext
    ): MutableList<NativeModule> = ArrayList()
}
```

after that, create class called `DailyMotionPlayerManager.java`:

```java
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

```

The last file that we need to create is `DailyMotionPlayerView.kt`:

```kt
package com.dailymotionplayerintegration

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.ThemedReactContext

class DailyMotionPlayerView(context: ThemedReactContext?) : FrameLayout(context!!) {

    private var playerId: String = ""
    private var videoId: String = ""
    private var dmPlayer: PlayerView? = null

    private fun getReactContext(): ReactContext? {
        return context as ReactContext
    }

    init {
        inflate(getReactContext(), R.layout.activity_main, this)
    }

    override fun requestLayout() {
        super.requestLayout()

        // This view relies on a measure + layout pass happening after it calls requestLayout().
        // https://github.com/facebook/react-native/issues/4990#issuecomment-180415510
        // https://stackoverflow.com/questions/39836356/react-native-resize-custom-ui-component
        post(measureAndLayout)
    }

    private val measureAndLayout = Runnable {
        measure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
        this.layout(left, top, right, bottom)
    }

    private fun loadThePlayer(): Any {
        val currentActivity = getReactContext()?.currentActivity

        if (currentActivity != null) {

            // Create and configure the Dailymotion PlayerView
            val playerView = PlayerView(getReactContext()!!)

            val playerContainerView = findViewById<View>(R.id.playerContainerView) as FrameLayout

            if (playerContainerView.layoutParams != null) {
                playerView.layoutParams = playerContainerView.layoutParams
            } else {
                Log.e("--DailymotionPlayer--", "No playerContainerView found")
            }

            return createDailymotionPlayer(
                    context,
                    playerId = playerId!!,
                    playerContainerView = playerContainerView
            )
        }
        Log.e("--DailymotionPlayer--", "Container null")

        return View(context) as PlayerView
    }

    fun createDailymotionPlayer(
            context: Context,
            playerId: String,
            playerContainerView: FrameLayout
    ) {

        Log.d("--DailymotionPlayer--", "createDailymotionPlayer")

        Dailymotion.createPlayer(
                context,
                playerId = playerId,
                playerSetupListener =
                object : Dailymotion.PlayerSetupListener {
                    override fun onPlayerSetupFailed(error: PlayerError) {
                        Log.e(
                                "--DailymotionPlayer--",
                                "Error while creating Dailymotion player: ${error.message}"
                        )
                    }

                    override fun onPlayerSetupSuccess(player: PlayerView) {
                        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                        dmPlayer = player
                        playerContainerView.addView(dmPlayer, lp)


                        Log.d(
                                "--DailymotionPlayer--",
                                "Added Dailymotion player ${dmPlayer} to view hierarchy"
                        )
                        runTheVideo()
                    }
                },
                playerListener =
                object : PlayerListener {
                    override fun onFullscreenRequested(playerDialogFragment: DialogFragment) {
                        super.onFullscreenRequested(playerDialogFragment)
                        Log.d("--DailymotionPlayer--", "Enter fullscreen")

                        playerContainerView.layoutParams.height = LayoutParams.MATCH_PARENT
                        playerContainerView.layoutParams.width = LayoutParams.MATCH_PARENT


                        // You might need to handle
                        // this@YourClass.dmPlayer?.notifyFullscreenChanged() here.
                    }

                    override fun onFullscreenExit(playerView: PlayerView) {
                        super.onFullscreenExit(playerView)
                        Log.d("--DailymotionPlayer--", "Exit fullscreen")

                        playerContainerView.layoutParams.height = LayoutParams.MATCH_PARENT
                        playerContainerView.layoutParams.width = LayoutParams.MATCH_PARENT
                        // You might need to handle
                        // this@YourClass.dmPlayer?.notifyFullscreenChanged() here.
                    }
                }
        )
    }

    fun setVideoId(videoId: String) {
        this.videoId = videoId
        Log.d("--DailymotionPlayer--", "Set video id ${this.videoId}")
    }


    fun setPlayerId(playerId: String) {
        this.playerId = playerId
        Log.d("--DailymotionPlayer--", "Set player id ${this.playerId}")
        loadThePlayer()
    }

    fun runTheVideo() {
        dmPlayer!!.loadContent(videoId=videoId)
    }
}

```

## Using DailyMotionPlayer Package in React Native

In your React Native code, import the Native Module using `requireNativeComponent` and use it in your component.

```ts
import {HostComponent, ViewStyle, requireNativeComponent} from 'react-native';

const DailyMotionPlayer: HostComponent<{
  videoId: string;
  playerId: string;
  style?: ViewStyle;
}> = requireNativeComponent('DailyMotionPlayerView');

export default DailyMotionPlayer;
```

in `App.tsx`, you can call the component

```tsx
<DailyMotionPlayer
  playerId="x9uwg"
  videoId="x8pbfnm"
  style={{
    height: 300,
    backgroundColor: 'black',
  }}
/>
```

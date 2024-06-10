# Dailymotion Android SDK Integration with React Native

## Adding Dependencies and Repository Configuration

1. Open settings.gradle

   Open the `settings.gradle` file in the root of your React Native project.

2. Add Dependency Resolution Management

   Add the following code at the top of your `settings.gradle` file:

   ```gradle
   dependencyResolutionManagement {
       repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
       repositories {
           google()
           mavenCentral()
           maven {
               name = "DailymotionMavenRelease"
               url = "https://mvn.dailymotion.com/repository/releases/"
           }
       }
   }

   // ... (other existing code)
   ```

3. Open build.gradle (app)

   Open the `build.gradle` file in the app directory of your React Native project.

4. Add DailyMotion SDK Dependencies

   Add the following lines to the dependencies section of your `build.gradle` file:

   ```gradle
   android {
    // ... (other existing code)

    dependencies {
        // ... (other existing dependencies)

        implementation 'com.dailymotion.player.android:sdk:1.2.0'
        implementation 'com.dailymotion.player.android:ads:1.2.0'
    }
   }

   ```

5. Explanation

   In the added code, we configured the dependency resolution management in `settings.gradle` to include the DailyMotion Maven repository. This allows your project to resolve dependencies from the specified Maven repository.

   In the `build.gradle` file, we added the DailyMotion SDK dependencies to the `implementation` section. This includes the main SDK (`com.dailymotion.player.android:sdk:1.0.6`) and the ads module (`com.dailymotion.player.android:ads:1.0.6`).

   These steps are necessary for integrating the DailyMotion SDK into your React Native Android project.

## Registering the Package in MainApplication.java

1.  **Locate MainApplication.java:** In your React Native project, navigate to the `android/app/src/main/java/com/yourAppName` directory. Inside this directory, you'll find the `MainApplication.java` file.
2.  **Add Import Statements:**
    At the top of the MainApplication.java file, import the necessary classes.

    ```java
    import com.yourpackage.DailymotionPlayer.DailymotionPlayerViewFactory;
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
      packages.add(new DailymotionPlayerViewFactory());
      return packages;
    }

    ```

    Ensure that you're using your actual package name in place of `yourpackage`. This registration allows React Native to access the functionality provided by your `DailymotionPlayerViewFactory`.

## Adding FragmentManager in MainActivity

1.  Open MainActivity.java

    Open the `MainActivity.java` file in your React Native Android project.

2.  Add FragmentManager Declaration

    Add the following line at the top of your `MainActivity` class to declare `mSupportFragmentManager`:

    ```java
    public static FragmentManager mSupportFragmentManager = null;
    ```

    Your `MainActivity` class should look like this:

    ```java
    package com.yourapp;

    import android.os.Bundle;
    import com.facebook.react.ReactActivity;
    import androidx.fragment.app.FragmentManager; // Make sure to import FragmentManager

    public class MainActivity extends ReactActivity {

    public static FragmentManager mSupportFragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSupportFragmentManager = getSupportFragmentManager();

        // setContentView(R.layout.activity_main); // Commented out for reference
    }

    // ... (other existing code)

    }
    ```

3.  Save Changes

    Save the changes to `MainActivity.java`.

4.  Explanation

    In the added code, we declare a `public static FragmentManager mSupportFragmentManager` variable in the `MainActivity` class. This variable is initialized in the `onCreate` method, allowing you to access the `FragmentManager` from other parts of your application.

    This modification is necessary for enabling fullscreen capability in the DailyMotion SDK, as it relies on the `FragmentManager` provided by the hosting activity.

## Creating the Package

navigate to the `android/app/src/main/java/com/yourAppName` directory. Inside this directory, create a class called `DailyPlayerPackage.kt`

```kt
package com.dailymotionplayerintegration.DailymotionPlayer


import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class DailymotionPlayerViewFactory: ReactPackage {
    override fun createViewManagers(reactContext: ReactApplicationContext): MutableList<ViewManager<out View, out ReactShadowNode<*>>> {
        return mutableListOf(
            DailymotionPlayerController()
        )
    }

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): MutableList<NativeModule> = ArrayList()
}
```

after that, create class called `DailymotionPlayerController.java`:

```java
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


```

The last file that we need to create is `DailymotionPlayerNativeView.kt`:

```kt
package com.dailymotionplayerintegration.DailymotionPlayer

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.dailymotion.player.android.sdk.Dailymotion
import com.dailymotion.player.android.sdk.PlayerView
import com.dailymotion.player.android.sdk.listeners.PlayerListener
import com.dailymotion.player.android.sdk.webview.error.PlayerError
import com.dailymotionplayerintegration.MainActivity
import com.dailymotionplayerintegration.R
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.ThemedReactContext

class DailymotionPlayerNativeView(context: ThemedReactContext?) : FrameLayout(context!!) {

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
                        playerDialogFragment.show(MainActivity.mSupportFragmentManager, "dmPlayerFullscreenFragment")
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
}> = requireNativeComponent('DailymotionPlayerNative');

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

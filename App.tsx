/* eslint-disable react-native/no-inline-styles */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {SafeAreaView, StatusBar, useColorScheme, View} from 'react-native';

// const DmPlayer = Platform.select({
//   ios: View,
//   android: requireNativeComponent('DailyMotionPlayerView'),
// });

import {Colors} from 'react-native/Libraries/NewAppScreen';
import DmComponent from './DmComponent';

function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  return (
    <SafeAreaView
      style={{
        display: 'flex',
        height: '100%',
        // backgroundColor: 'yellow',
      }}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <DmComponent />
    </SafeAreaView>
  );
}

export default App;

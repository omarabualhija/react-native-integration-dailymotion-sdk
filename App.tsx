/* eslint-disable react-native/no-inline-styles */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {SafeAreaView, StatusBar, useColorScheme} from 'react-native';

import {Colors} from 'react-native/Libraries/NewAppScreen';
import DailyMotionPlayer from './DailyMotionPlayer';

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
      }}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <DailyMotionPlayer
        playerId="x9uwg"
        videoId="x8pbfnm"
        style={{
          height: 300,
          backgroundColor: 'black',
        }}
      />
    </SafeAreaView>
  );
}

export default App;

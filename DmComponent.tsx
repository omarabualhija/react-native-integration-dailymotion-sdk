import React from 'react';
import {Platform, View, requireNativeComponent} from 'react-native';

const DMComponent_ = Platform.select({
  ios: View,
  android: requireNativeComponent('DailyMotionPlayerView'),
});

const DmComponent = () => {
  return (
    <DMComponent_
      style={{
        height: 300,
        backgroundColor: 'black',
      }}></DMComponent_>
  );
};

export default DmComponent;

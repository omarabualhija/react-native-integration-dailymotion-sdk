import {HostComponent, ViewStyle, requireNativeComponent} from 'react-native';

const DailyMotionPlayer: HostComponent<{
  videoId: string;
  playerId: string;
  style?: ViewStyle;
}> = requireNativeComponent('DailymotionPlayerNative');

export default DailyMotionPlayer;

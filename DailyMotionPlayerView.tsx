import React, {useEffect, useRef} from 'react';
import {PixelRatio, UIManager, findNodeHandle} from 'react-native';
import {DailyMotionPlayerManager} from './DailyMotionPlayerManager';

const createFragment = viewId =>
  UIManager.dispatchViewManagerCommand(
    viewId,
    // we are calling the 'create' command
    UIManager.DailyMotionPlayerManager.Commands.create.toString(),
    [viewId],
  );

const DailyMotionPlayerView = () => {
  const ref = useRef(null);

  useEffect(() => {
    const viewId = findNodeHandle(ref.current);
    createFragment(viewId);
  }, []);

  return (
    <DailyMotionPlayerManager
      style={{
        height: PixelRatio.getPixelSizeForLayoutSize(100),
        // converts dpi to px, provide desired width
        width: PixelRatio.getPixelSizeForLayoutSize(200),
        padding: PixelRatio.getPixelSizeForLayoutSize(30),
      }}
      ref={ref}
    />
  );
};

export default DailyMotionPlayerView;

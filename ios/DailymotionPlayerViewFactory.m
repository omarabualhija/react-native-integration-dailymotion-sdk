//
//  DailymotionPlayerViewFactory.m
//  ReactNativeDailymotionSDK
//
//  Created by Arryangga Aliev Pratamaputra on 19/06/24.
//
#import <Foundation/Foundation.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(DailymotionPlayerNative, RCTViewManager)
RCT_EXPORT_VIEW_PROPERTY(status, BOOL)
RCT_EXPORT_VIEW_PROPERTY(videoId, NSString)
RCT_EXPORT_VIEW_PROPERTY(playerId, NSString)
//RCT_EXPORT_VIEW_PROPERTY(onClick, RCTBubblingEventBlock)
@end


//
//  DailymotionPlayerViewFactory.swift
//  ReactNativeDailymotionSDK
//
//  Created by Arryangga Aliev Pratamaputra on 13/06/24.
//

import Foundation
import SwiftUI

@objc(DailymotionPlayerNative)
class DailymotionPlayerViewFactory: RCTViewManager {
  
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  override func view() -> UIView! {
    return DailymotionPlayerNativeView()
  }

}

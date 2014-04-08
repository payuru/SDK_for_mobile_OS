//
//  AppDelegate.h
//  PAYU_TEST
//
//  Created by Demjanko Denis on 02.04.14.
//  Copyright (c) 2014 Demjanko Denis. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate,UIWebViewDelegate>{
    NSString *date;
    NSString *payrefno;
}

@property (strong, nonatomic) UIWindow *window;

@end

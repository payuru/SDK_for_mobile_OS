//
//  orderLUViewController.h
//  PAYU_Example
//
//  Created by Max on 13.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"

#define SHOW_PORGRESS(x) [MBProgressHUD showHUDAddedTo:x animated:YES]
#define HIDE_PORGRESS(x) [MBProgressHUD hideHUDForView:x animated:YES]

@interface orderLUViewController : UIViewController <UIWebViewDelegate>{

}

@property (nonatomic,strong) IBOutlet UIWebView *webView;
@property (nonatomic,weak) NSString *payrefno;

@end

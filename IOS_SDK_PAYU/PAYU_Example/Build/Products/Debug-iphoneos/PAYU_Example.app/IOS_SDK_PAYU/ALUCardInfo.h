//
//  ALUCardInfo.h
//  PAYU_Example
//
//  Created by Max on 25.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ALUCardInfo : NSObject{
    NSString *CC_NUMBER;
    NSString *EXP_MONTH;
    NSString *EXP_YEAR;
    NSString *CC_CVV;
    NSString *CC_OWNER;
    NSString *CC_TOKEN;
}
@property (nonatomic,readonly) NSString *CC_NUMBER;
@property (nonatomic,readonly) NSString *EXP_MONTH;
@property (nonatomic,readonly) NSString *EXP_YEAR;
@property (nonatomic,readonly) NSString *CC_CVV;
@property (nonatomic,readonly) NSString *CC_OWNER;
@property (nonatomic,readonly) NSString *CC_TOKEN;

-(id)initWithCC_NUMBER:(NSString*)ccNumber EXP_MONTH:(NSString*)expMonth EXP_YEAR:(NSString*)expYear CC_CVV:(NSString*)ccCvv CC_OWNER:(NSString*)ccOwner;
-(id)initWithCC_TOKEN:(NSString*)ccToken CC_CVV:(NSString*)ccCvv;
@end

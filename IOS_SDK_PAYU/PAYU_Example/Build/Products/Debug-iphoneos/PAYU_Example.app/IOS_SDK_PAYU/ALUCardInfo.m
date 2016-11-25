//
//  ALUCardInfo.m
//  PAYU_Example
//
//  Created by Max on 25.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import "ALUCardInfo.h"

@implementation ALUCardInfo
@synthesize CC_NUMBER;
@synthesize EXP_MONTH;
@synthesize EXP_YEAR;
@synthesize CC_CVV;
@synthesize CC_OWNER;
@synthesize CC_TOKEN;

-(id)initWithCC_NUMBER:(NSString*)ccNumber EXP_MONTH:(NSString*)expMonth EXP_YEAR:(NSString*)expYear CC_CVV:(NSString*)ccCvv CC_OWNER:(NSString*)ccOwner{
    self=[super init];
    if(self){
        CC_NUMBER=ccNumber;
        EXP_MONTH=expMonth;
        EXP_YEAR=expYear;
        CC_CVV=ccCvv;
        CC_OWNER=ccOwner;
    }
    if(CC_NUMBER.length>0&&EXP_MONTH.length>0&&EXP_YEAR.length>0&&CC_CVV.length>0&&CC_OWNER.length>0)
        return self;
    return nil;
}
-(id)initWithCC_TOKEN:(NSString*)ccToken CC_CVV:(NSString*)ccCvv{
    self=[super init];
    if(self){
        CC_CVV=ccCvv;
        CC_TOKEN=ccToken;
    }
    if(CC_CVV.length>0&&CC_TOKEN.length>0)
        return self;
    return nil;

    
}
@end

//
//  LU.h
//  alu
//
//  Created by Demjanko Denis on 01.04.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface LU : NSObject{
    NSString *SECRET_KEY;
}

-(id)initWithSecretKey:(NSString*)_SECRET_KEY;

-(NSMutableURLRequest*)getLURequst:(NSMutableDictionary*)orderDetails;

@end

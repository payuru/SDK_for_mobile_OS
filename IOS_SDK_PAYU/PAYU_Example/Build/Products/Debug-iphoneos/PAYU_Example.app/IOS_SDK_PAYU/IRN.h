//
//  IRN.h
//  alu
//
//  Created by Demjanko Denis on 01.04.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^IRNResult)(NSData *response, NSError *error);

@interface IRN : NSObject{
    IRNResult completionHandler;
    
    NSString *SECRET_KEY;
}

-(id)initWithSecretKey:(NSString*)_SECRET_KEY;

@property (copy) IRNResult completionHandler;

-(void)sendIRNRequest:(NSMutableDictionary*)orderDetails withResult:(IRNResult)result;

@end

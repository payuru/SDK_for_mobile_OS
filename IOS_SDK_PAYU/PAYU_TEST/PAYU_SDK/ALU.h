//
//  ALU.h
//  alu
//
//  Created by Denis Demjanko on 31.03.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^ALUResult)(NSData *response, NSError *error);

@interface ALU : NSObject{
    ALUResult completionHandler;

    NSString *SECRET_KEY;
}

-(id)initWithSecretKey:(NSString*)_SECRET_KEY;

@property (copy) ALUResult completionHandler;

-(void)sendALURequest:(NSMutableDictionary*)orderDetails withResult:(ALUResult)result;

@end

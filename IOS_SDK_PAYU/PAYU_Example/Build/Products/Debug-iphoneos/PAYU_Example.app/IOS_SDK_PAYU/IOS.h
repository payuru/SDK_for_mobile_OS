//
//  IOS.h
//  alu
//
//  Created by Demjanko Denis on 02.04.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^IOSResult)(NSData *response, NSError *error);

@interface IOS : NSObject{
    IOSResult completionHandler;
    
    NSString *SECRET_KEY;
}

-(id)initWithSecretKey:(NSString*)_SECRET_KEY;

@property (copy) IOSResult completionHandler;

-(void)sendIOSRequest:(NSMutableDictionary*)orderDetails withResult:(IOSResult)result;

@end


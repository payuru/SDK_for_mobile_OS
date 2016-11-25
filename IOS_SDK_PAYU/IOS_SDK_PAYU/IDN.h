//
//  IDN.h
//  alu
//
//  Created by Demjanko Denis on 02.04.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^IDNResult)(NSData *response, NSError *error);

@interface IDN : NSObject{
    IDNResult completionHandler;
    
    NSString *SECRET_KEY;
}

-(id)initWithSecretKey:(NSString*)_SECRET_KEY;

@property (copy) IDNResult completionHandler;

-(void)sendIDNRequest:(NSMutableDictionary*)orderDetails withResult:(IDNResult)result;

@end


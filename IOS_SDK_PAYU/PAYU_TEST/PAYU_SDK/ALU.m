//
//  ALU.m
//  alu
//
//  Created by Denis Demjanko on 31.03.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import "ALU.h"

#include <CommonCrypto/CommonDigest.h>
#include <CommonCrypto/CommonHMAC.h>

#include <sys/types.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

@implementation ALU

@synthesize completionHandler;

#pragma mark - init

-(id)initWithSecretKey:(NSString*)_SECRET_KEY{
    self  = [super init];
    
    SECRET_KEY = [[NSString alloc] initWithString:_SECRET_KEY];
    
    return self;
}

-(void)sendALURequest:(NSMutableDictionary*)orderDetails withResult:(ALUResult)result{
    self.completionHandler = result;
    
    NSArray * sortedKeys = [[orderDetails allKeys] sortedArrayUsingSelector: @selector(caseInsensitiveCompare:)];
    
    NSMutableArray *parts = [NSMutableArray new];
    NSMutableArray *hashs = [NSMutableArray new];

    for (NSString *key in sortedKeys) {
        NSString *encodedValue = [orderDetails objectForKey:key];
        NSString *encodedKey = key;
      
        NSString *part = [NSString stringWithFormat:@"%@=%@", encodedKey, encodedValue];
        [parts addObject:part];

        
        NSString *hashString = [NSString stringWithFormat:@"%d%@",encodedValue.length,encodedValue];
        [hashs addObject:hashString];
    }
    
    NSString *postString = [parts componentsJoinedByString:@"&"];
    NSString *hashString = [hashs componentsJoinedByString:@""];
    NSString *hmac = [self HMACWithSourceAndSecret:hashString secret:SECRET_KEY];
    
    postString = [postString stringByAppendingString:[NSString stringWithFormat:@"&ORDER_HASH=%@",hmac]];

    NSData *postData = [postString dataUsingEncoding:NSUTF8StringEncoding];
    
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:@"https://secure.payu.ru/order/alu.php"] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:30.0f];
    [request setHTTPMethod:@"POST"];
    [request setHTTPBody:postData];
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSError *error;
        NSData *data = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:&error];
        dispatch_async(dispatch_get_main_queue(), ^{
            if (error) {
                self.completionHandler(nil,error);
            }
            else{
                self.completionHandler(data,nil);
            }
        });
    });
}


-(NSString*)HMACWithSourceAndSecret:(NSString*)source  secret:(NSString*)secret{
    CCHmacContext    ctx;
    const char       *key = [secret UTF8String];
    const char       *str = [source UTF8String];
    unsigned char    mac[CC_MD5_DIGEST_LENGTH];
    char             hexmac[2 * CC_MD5_DIGEST_LENGTH + 1];
    char             *p;
    
    CCHmacInit(&ctx, kCCHmacAlgMD5, key, strlen( key ));
    CCHmacUpdate(&ctx, str, strlen(str) );
    CCHmacFinal(&ctx, mac );
    
    p = hexmac;
    for (int i = 0; i<CC_MD5_DIGEST_LENGTH; i++ ) {
        snprintf( p, 3, "%02x", mac[ i ] );
        p += 2;
    }
    
    return [NSString stringWithUTF8String:hexmac];
}

@end

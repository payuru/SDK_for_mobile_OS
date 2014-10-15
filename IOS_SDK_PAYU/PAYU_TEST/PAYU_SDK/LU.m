//
//  LU.m
//  alu
//
//  Created by Demjanko Denis on 01.04.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import "LU.h"
#include <CommonCrypto/CommonDigest.h>
#include <CommonCrypto/CommonHMAC.h>

#include <sys/types.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

@implementation LU

#pragma mark - init

-(id)initWithSecretKey:(NSString*)_SECRET_KEY{
    self  = [super init];
    
    SECRET_KEY = [[NSString alloc] initWithString:_SECRET_KEY];
    
    return self;
}

-(NSMutableURLRequest*)getLURequst:(NSMutableDictionary*)orderDetails{
    NSArray * sortedKeys = [[orderDetails allKeys] sortedArrayUsingSelector: @selector(caseInsensitiveCompare:)];
    
    NSMutableArray *parts = [NSMutableArray new];
    NSMutableArray *hashs = [NSMutableArray new];
    
    for (NSString *key in sortedKeys) {
        NSString *encodedValue = [orderDetails objectForKey:key];
        NSString *encodedKey = key;
        
        if ([key isEqualToString:@"PRODUCTS"]) {
            
            int index = 0;
			
            for (NSMutableDictionary *product in [orderDetails objectForKey:@"PRODUCTS"])
			{
				int offset = 0;
				
                for (NSString *_key in product.allKeys)
				{
                    NSString *_encodedValue = [product objectForKey:_key];
                    NSString *_encodedKey = _key;
                    NSString *part = [NSString stringWithFormat:@"%@=%@", _encodedKey, _encodedValue];
					
                    if (index>0)
					{
                        [parts insertObject:part atIndex: 10 + offset];
                        offset += index + 1;
                    }
					else
					{
                        [parts addObject:part];
                    }
                }
				
                index++;
            }
        }
        else
		{
            NSString *part = [NSString stringWithFormat:@"%@=%@", encodedKey, encodedValue];
            [parts addObject:part];
        }
        
        if ([key isEqualToString:@"AUTOMODE"] || [key isEqualToString:@"BACK_REF"] || [key isEqualToString:@"DEBUG"] || [key isEqualToString:@"BILL_FNAME"] || [key isEqualToString:@"BILL_LNAME"]
            || [key isEqualToString:@"BILL_EMAIL"] || [key isEqualToString:@"BILL_PHONE"] || [key isEqualToString:@"BILL_ADDRESS"] || [key isEqualToString:@"BILL_CITY"] || [key isEqualToString:@"DELIVERY_FNAME"] || [key isEqualToString:@"DELIVERY_LNAME"] || [key isEqualToString:@"DELIVERY_PHONE"] || [key isEqualToString:@"DELIVERY_ADDRESS"]
            || [key isEqualToString:@"DELIVERY_CITY"] || [key isEqualToString:@"LU_ENABLE_TOKEN"] || [key isEqualToString:@"LU_TOKEN_TYPE"] || [key isEqualToString:@"TESTORDER"]
            || [key isEqualToString:@"LANGUAGE"] || [key isEqualToString:@"MERCHANT"]
            || [key isEqualToString:@"ORDER_REF"]
            || [key isEqualToString:@"ORDER_DATE"]
            || [key isEqualToString:@"MERCHANT"]
            || [key isEqualToString:@"ORDER_PNAME[]"]
            || [key isEqualToString:@"ORDER_PCODE[]"]
            || [key isEqualToString:@"PRODUCTS"] || [key isEqualToString:@"BILL_COUNTRYCODE"]) {
            continue;
        }
        
        NSString *hashString = [NSString stringWithFormat:@"%d%@",encodedValue.length,encodedValue];
        [hashs addObject:hashString];
    }
    
    [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[orderDetails valueForKey:@"MERCHANT"] length],[orderDetails valueForKey:@"MERCHANT"]] atIndex:0];
    [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[orderDetails valueForKey:@"ORDER_REF"] length],[orderDetails valueForKey:@"ORDER_REF"]] atIndex:1];
    [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[orderDetails valueForKey:@"ORDER_DATE"] length],[orderDetails valueForKey:@"ORDER_DATE"]] atIndex:2];
    
    int index = 0;
    for (NSMutableDictionary *product in [orderDetails objectForKey:@"PRODUCTS"])
	{
		int offset = 0;
        NSString *name = [product valueForKey:@"ORDER_PNAME[]"];
        [hashs insertObject:[NSString stringWithFormat:@"%d%@",[name lengthOfBytesUsingEncoding:NSUTF8StringEncoding], name] atIndex:3+offset * index];
        if (index>0) offset++;
        [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[product valueForKey:@"ORDER_PCODE[]"] length],[product valueForKey:@"ORDER_PCODE[]"]] atIndex:4+offset * index];
        if (index>0) offset++;
        [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[product valueForKey:@"ORDER_PRICE[]"] length],[product valueForKey:@"ORDER_PRICE[]"]] atIndex:5+offset * index];
        if (index>0) offset++;
        [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[product valueForKey:@"ORDER_QTY[]"] length],[product valueForKey:@"ORDER_QTY[]"]] atIndex:6+offset * index];
        if (index>0) offset++;
        [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[product valueForKey:@"ORDER_VAT[]"] length],[product valueForKey:@"ORDER_VAT[]"]] atIndex:7+offset * index];
        if (index>0) offset++;
        [hashs insertObject:[NSString stringWithFormat:@"%d%@",[[product valueForKey:@"ORDER_SHIPPING[]"] length],[product valueForKey:@"ORDER_SHIPPING[]"]] atIndex:8+offset * index];
        
        index++;
    }
	
	
    NSString *postString = [parts componentsJoinedByString:@"&"];
    NSString *hashString = [hashs componentsJoinedByString:@""];
    NSString *hmac = [self HMACWithSourceAndSecret:hashString secret:SECRET_KEY];
    
    postString = [postString stringByAppendingString:[NSString stringWithFormat:@"&ORDER_HASH=%@",hmac]];
    
    NSData *postData = [postString dataUsingEncoding:NSUTF8StringEncoding];
    
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:@"https://secure.payu.ru/order/lu.php"] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:30.0f];
    [request setHTTPMethod:@"POST"];
    [request setHTTPBody:postData];
	
    return request;
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
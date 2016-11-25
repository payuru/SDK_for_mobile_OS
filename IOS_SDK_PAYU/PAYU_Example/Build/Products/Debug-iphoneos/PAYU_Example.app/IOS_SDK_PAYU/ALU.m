//
//  ALU.m
//  alu
//
//  Created by Denis Demjanko on 31.03.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import "ALU.h"
#import "MCMutableArray.h"

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

@synthesize products;
@synthesize SECRET_KEY;
@synthesize MERCHANT;
@synthesize ORDER_REF;
@synthesize ORDER_DATE;
@synthesize ORDER_HASH;

@synthesize ORDER_SHIPPING;
@synthesize PRICES_CURRENCY;

@synthesize PAY_METHOD;
@synthesize TESTORDER;
@synthesize LANGUAGE;
@synthesize BACK_REF;

@synthesize BIllCLIENTINFO;

@synthesize DELIVERYDATA;

@synthesize CARDINFO;
@synthesize CLIENT_IP;
@synthesize CLIENT_TIME;
@synthesize CC_OWNER_TIME;
@synthesize CC_NUMBER_TIME;

#pragma mark - init
-(id)initWithSecretKey:(NSString*)secretKey merchant:(NSString*)merchant orderRef:(NSString*)orderRef orderDate:(NSString*)orderDate{
    self = [super init];
    SECRET_KEY = [[NSString alloc] initWithString:secretKey];
    products=[[NSMutableArray alloc] init];
    MERCHANT=merchant;
    ORDER_REF=orderRef;
    ORDER_DATE=orderDate;
    TESTORDER=NO;
    BACK_REF=[NSString stringWithFormat:@""];
    return self;
    
}
-(BOOL)addProduct:(ALUProduct*)product error:(NSError**)error{
    if(product.name==nil||product.name.length==0){
        *error=  [NSError errorWithDomain:@"LUProductError" code:ProductErrorCodesEmptyName userInfo:nil];
        return NO;
    }else{
        if(product.name.length>155){
            *error=  [NSError errorWithDomain:@"LUProductError" code:ProductErrorCodesLongName userInfo:nil];
            return NO;
        }
    }
    if(product.code==nil||product.code.length==0){
        *error=  [NSError errorWithDomain:@"LUProductError" code:ProductErrorCodesEmptyCode userInfo:nil];
        return NO;
    }else{
        if(product.code.length>50){
            *error=  [NSError errorWithDomain:@"LUProductError" code:ProductErrorCodesLongCode userInfo:nil];
            return NO;
        }
    }
    if(product.price==nil){
        *error=  [NSError errorWithDomain:@"LUProductError" code:ProductErrorCodesEmptyPrice userInfo:nil];
        return NO;
    }
    [products addObject:product];
    return YES;
}

- (void)sendALURequstWithResult:(ALUResult)result{
    self.completionHandler = result;
    
    MCMutableArray *parts = [MCMutableArray new];
    MCMutableArray *hashs = [MCMutableArray new];
    
    [parts addObject:BACK_REF key:@"BACK_REF"];
    [hashs addHash:BACK_REF];
    
    [parts addObject:BIllCLIENTINFO.BILL_ADDRESS key:@"BILL_ADDRESS"];
    [hashs addHash:BIllCLIENTINFO.BILL_ADDRESS];
    [parts addObject:BIllCLIENTINFO.BILL_ADDRESS2 key:@"BILL_ADDRESS2"];
    [hashs addHash:BIllCLIENTINFO.BILL_ADDRESS2];
    [parts addObject:BIllCLIENTINFO.BILL_CITY key:@"BILL_CITY"];
    [hashs addHash:BIllCLIENTINFO.BILL_CITY];
    [parts addObject:BIllCLIENTINFO.BILL_COUNTRYCODE key:@"BILL_COUNTRYCODE"];
    [hashs addHash:BIllCLIENTINFO.BILL_COUNTRYCODE];
    [parts addObject:BIllCLIENTINFO.BILL_EMAIL key:@"BILL_EMAIL"];
    [hashs addHash:BIllCLIENTINFO.BILL_EMAIL];
    [parts addObject:BIllCLIENTINFO.BILL_FAX key:@"BILL_FAX"];
    [hashs addHash:BIllCLIENTINFO.BILL_FAX];
    [parts addObject:BIllCLIENTINFO.BILL_FNAME key:@"BILL_FNAME"];
    [hashs addHash:BIllCLIENTINFO.BILL_FNAME];
    [parts addObject:BIllCLIENTINFO.BILL_LNAME key:@"BILL_LNAME"];
    [hashs addHash:BIllCLIENTINFO.BILL_LNAME];
    [parts addObject:BIllCLIENTINFO.BILL_PHONE key:@"BILL_PHONE"];
    [hashs addHash:BIllCLIENTINFO.BILL_PHONE];
    [parts addObject:BIllCLIENTINFO.BILL_STATE key:@"BILL_STATE"];
    [hashs addHash:BIllCLIENTINFO.BILL_STATE];
    [parts addObject:BIllCLIENTINFO.BILL_ZIPCODE key:@"BILL_ZIPCODE"];
    [hashs addHash:BIllCLIENTINFO.BILL_ZIPCODE];
    
    
   
    
    
    
    [parts addObject:CARDINFO.CC_CVV key:@"CC_CVV"];
    [hashs addHash:CARDINFO.CC_CVV];
    [parts addObject:CARDINFO.CC_NUMBER key:@"CC_NUMBER"];
    [hashs addHash:CARDINFO.CC_NUMBER];
    [parts addObject:CARDINFO.CC_OWNER key:@"CC_OWNER"];
    [hashs addHash:CARDINFO.CC_OWNER];
    [parts addObject:CARDINFO.CC_TOKEN key:@"CC_TOKEN"];
    [hashs addHash:CARDINFO.CC_TOKEN];
    
    
    [parts addObject:DELIVERYDATA.DELIVERY_ADDRESS key:@"DELIVERY_ADDRESS"];
    [hashs addHash:DELIVERYDATA.DELIVERY_ADDRESS];
    
    [parts addObject:DELIVERYDATA.DELIVERY_CITY key:@"DELIVERY_CITY"];
    [hashs addHash:DELIVERYDATA.DELIVERY_CITY];
    [parts addObject:DELIVERYDATA.DELIVERY_COUNTRYCODE key:@"DELIVERY_COUNTRYCODE"];
    [hashs addHash:DELIVERYDATA.DELIVERY_COUNTRYCODE];
    [parts addObject:DELIVERYDATA.DELIVERY_FNAME key:@"DELIVERY_FNAME"];
    
    [parts addObject:CLIENT_IP key:@"CLIENT_IP"];
    [hashs addHash:CLIENT_IP];
    [parts addObject:CLIENT_TIME key:@"CLIENT_TIME"];
    [hashs addHash:CLIENT_TIME];
    [parts addObject:CLIENT_TIME key:@"CLIENT_TIME"];
    [hashs addHash:CLIENT_TIME];
    
    [hashs addHash:DELIVERYDATA.DELIVERY_FNAME];
    [parts addObject:DELIVERYDATA.DELIVERY_LNAME key:@"DELIVERY_LNAME"];
    [hashs addHash:DELIVERYDATA.DELIVERY_LNAME];
    [parts addObject:DELIVERYDATA.DELIVERY_PHONE key:@"DELIVERY_PHONE"];
    [hashs addHash:DELIVERYDATA.DELIVERY_PHONE];
    [parts addObject:DELIVERYDATA.DELIVERY_STATE key:@"DELIVERY_STATE"];
    [hashs addHash:DELIVERYDATA.DELIVERY_STATE];
    [parts addObject:DELIVERYDATA.DELIVERY_ZIPCODE key:@"DELIVERY_ZIPCODE"];
    [hashs addHash:DELIVERYDATA.DELIVERY_ZIPCODE];
    
    [parts addObject:CARDINFO.EXP_MONTH key:@"EXP_MONTH"];
    [hashs addHash:CARDINFO.EXP_MONTH];
    [parts addObject:CARDINFO.EXP_YEAR key:@"EXP_YEAR"];
    [hashs addHash:CARDINFO.EXP_YEAR];
    

    [parts addObject:LanguageTypeString(LANGUAGE) key:@"LANGUAGE"];
    [hashs addHash:LanguageTypeString(LANGUAGE)];
    
    [parts addObject:MERCHANT key:@"MERCHANT"];
    [hashs addHash:MERCHANT];

    [parts addObject:ORDER_DATE key:@"ORDER_DATE"];
    [hashs addHash:ORDER_DATE];
    
    //products
    for (ALUProduct *product in products) {
        [parts addObject:product.code key:@"ORDER_PCODE[]"];
        [hashs addHash:product.code];
    }
    
    for (ALUProduct *product in products) {
        [parts addObject:product.name key:@"ORDER_PNAME[]"];
        [hashs addHash:product.name];
    }
    
    for (ALUProduct *product in products) {
        [parts addObject:[product.price stringValue] key:@"ORDER_PRICE[]"];
        [hashs addHash:[product.price stringValue]];
    }
    
    for (ALUProduct *product in products) {
        [parts addObject:product.qtyString key:@"ORDER_QTY[]"];
        [hashs addHash:product.qtyString];
    }
   
    // for (ALUProduct *product in products) {
        // NSString *priceT=PriceCurrencyTypeString(product.currency);
     //    [parts addObject:priceT key:@"PRICES_CURRENCY"];
     //    [hashs addHash:priceT];
  //  }
   
    [parts addObject:ORDER_REF key:@"ORDER_REF"];
    [hashs addHash:ORDER_REF];
    
    [parts addObject:ALUPAY_METHODTypeString(PAY_METHOD)  key:@"PAY_METHOD"];
    [hashs addHash:ALUPAY_METHODTypeString(PAY_METHOD)];

    
    if(PRICES_CURRENCY!=NONE){
        [parts addObject:PriceCurrencyTypeString(PRICES_CURRENCY) key:@"PRICES_CURRENCY"];
        [hashs addHash:PriceCurrencyTypeString(PRICES_CURRENCY)];
    }
    
    [parts addObject:[ORDER_SHIPPING stringValue] key:@"ORDER_SHIPPING"];
    [hashs addHash:[ORDER_SHIPPING stringValue]];

   
    
    [parts addObject:CC_NUMBER_TIME key:@"CC_NUMBER_TIME"];
    [hashs addHash:CC_NUMBER_TIME];

    [parts addObject:CC_OWNER_TIME key:@"CC_OWNER_TIME"];
    [hashs addHash:CC_OWNER_TIME];

    [parts addObject:BoolToSTR( TESTORDER) key:@"TESTORDER"];
    [hashs addHash:BoolToSTR( TESTORDER)];
    
    
    NSString *postString =[parts componentsJoinedByString:@"&"];
    NSString *hashString = [hashs componentsJoinedByString:@""];
    NSString *hmac = [self HMACWithSourceAndSecret:hashString secret:SECRET_KEY];
    
   // NSLog(@"hashs\n%@",hashs);
   // NSLog(@"postString\n%@",postString);
   // NSLog(@"hashString\n%@",hashString);
   // NSLog(@"hmac\n%@",hmac);
    
    postString = [postString stringByAppendingString:[NSString stringWithFormat:@"&ORDER_HASH=%@", hmac]];
   // NSLog(@"%@",postString);

    NSData *postData =  [postString dataUsingEncoding:NSUTF8StringEncoding];
   // NSLog(@"%@",postData);
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:@"https://secure.payu.ru/order/alu/v3"] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:30.0f];
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

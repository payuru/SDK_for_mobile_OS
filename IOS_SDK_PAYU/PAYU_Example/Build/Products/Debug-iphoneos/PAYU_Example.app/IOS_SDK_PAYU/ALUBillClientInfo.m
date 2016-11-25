//
//  BillClientInfo.m
//  PAYU_Example
//
//  Created by Max on 25.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import "ALUBillClientInfo.h"

@implementation ALUBillClientInfo
@synthesize BILL_FNAME;
@synthesize BILL_LNAME;
@synthesize BILL_EMAIL;
@synthesize BILL_PHONE;
@synthesize BILL_COUNTRYCODE;

@synthesize BILL_CITY;
@synthesize BILL_FAX;
@synthesize BILL_ADDRESS;
@synthesize BILL_ADDRESS2;
@synthesize BILL_ZIPCODE;
@synthesize BILL_STATE;

-(id) initWithFNAME:(NSString*)fName LNAME:(NSString*)lName EMAIL:(NSString*)email PHONE:(NSString*)phone COUNTRYCODE:(NSString*)countryCode{
    self=[super init];
    if(self){
        BILL_FNAME=fName;
        BILL_LNAME=lName;
        BILL_EMAIL=email;
        BILL_PHONE=phone;
        BILL_COUNTRYCODE=countryCode;
    }
    return self;
}
@end

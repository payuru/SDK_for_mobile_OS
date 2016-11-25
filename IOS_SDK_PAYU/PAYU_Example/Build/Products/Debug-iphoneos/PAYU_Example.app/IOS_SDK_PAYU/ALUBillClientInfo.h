//
//  BillClientInfo.h
//  PAYU_Example
//
//  Created by Max on 25.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ALUBillClientInfo : NSObject{
    NSString *BILL_FNAME;
    NSString *BILL_LNAME;
    NSString *BILL_EMAIL;
    NSString *BILL_PHONE;
    NSString *BILL_COUNTRYCODE;
    
    
    // необязательные поля
    NSString *BILL_FAX;
    NSString *BILL_ADDRESS;
    NSString *BILL_ADDRESS2;
    NSString *BILL_ZIPCODE;
    NSString *BILL_CITY;
    NSString *BILL_STATE;
}

@property (nonatomic,readonly) NSString *BILL_FNAME;
@property (nonatomic,readonly) NSString *BILL_LNAME;
@property (nonatomic,readonly) NSString *BILL_EMAIL;
@property (nonatomic,readonly) NSString *BILL_PHONE;
@property (nonatomic,readonly) NSString *BILL_COUNTRYCODE;

// необязательные поля
@property (nonatomic,strong) NSString *BILL_FAX;
@property (nonatomic,strong) NSString *BILL_ADDRESS;
@property (nonatomic,strong) NSString *BILL_ADDRESS2;
@property (nonatomic,strong) NSString *BILL_ZIPCODE;
@property (nonatomic,strong) NSString *BILL_CITY;
@property (nonatomic,strong) NSString *BILL_STATE;



-(id) initWithFNAME:(NSString*)fName LNAME:(NSString*)lName EMAIL:(NSString*)email PHONE:(NSString*)phone COUNTRYCODE:(NSString*)countryCode;
@end

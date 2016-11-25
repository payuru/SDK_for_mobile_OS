//
//  DELIVERYData.h
//  PAYU_Example
//
//  Created by Max on 25.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ALUDELIVERYData : NSObject{
    NSString *DELIVERY_FNAME;
    NSString *DELIVERY_LNAME;
    NSString *DELIVERY_PHONE;
    NSString *DELIVERY_ADDRESS;
    NSString *DELIVERY_ZIPCODE;
    NSString *DELIVERY_CITY;
    NSString *DELIVERY_STATE;
    NSString *DELIVERY_COUNTRYCODE;
    NSString *DELIVERY_COMPANY;
    NSString *DELIVERY_ADDRESS2;
}
@property(nonatomic,strong) NSString *DELIVERY_FNAME;
@property(nonatomic,strong) NSString *DELIVERY_LNAME;
@property(nonatomic,strong) NSString *DELIVERY_PHONE;
@property(nonatomic,strong) NSString *DELIVERY_ADDRESS;
@property(nonatomic,strong) NSString *DELIVERY_ZIPCODE;
@property(nonatomic,strong) NSString *DELIVERY_CITY;
@property(nonatomic,strong) NSString *DELIVERY_STATE;
@property(nonatomic,strong) NSString *DELIVERY_COUNTRYCODE;
@property(nonatomic,strong) NSString *DELIVERY_COMPANY;
@property(nonatomic,strong) NSString *DELIVERY_ADDRESS2;
@end

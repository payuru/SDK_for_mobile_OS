//
//  ALUProduct.h
//  PAYU_Example
//
//  Created by Max on 25.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "MCHelper.h"

@interface ALUProduct:NSObject{
    NSString *name; // 155 знаков на одно название продукта
    NSString *code;  //Максимальная длина кода 1 товара: 50 символов.
    NSNumber *price; // проверка на «.» и положительную часть ORDER_PRICE[]
    NSUInteger qty;
    NSUInteger vat;
    PRICES_CURRENCYType currency;

    
}

@property (nonatomic,readonly) NSString *name;
@property (nonatomic,readonly) NSString *code;
@property (nonatomic,readonly) NSNumber *price;
@property (nonatomic,readonly) NSUInteger qty;
@property (nonatomic,readonly) PRICES_CURRENCYType currency;

-(id)initALUProductWithName:(NSString*)Name code:(NSString*)Code price:(NSNumber*)Price qty:(NSUInteger)Qty currency:(PRICES_CURRENCYType)Currency;
-(NSString*)qtyString;
-(NSString*)vatString;

@end


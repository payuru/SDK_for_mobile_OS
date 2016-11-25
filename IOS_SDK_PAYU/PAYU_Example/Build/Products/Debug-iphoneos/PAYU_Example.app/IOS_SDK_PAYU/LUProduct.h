//
//  Product.h
//  PAYU_Example
//
//  Created by Max on 24.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MCHelper.h"

@interface LUProduct:NSObject{
    NSString *name; // 155 знаков на одно название продукта
    NSString *code;  //Максимальная длина кода 1 товара: 50 символов.
    NSNumber *price; // проверка на «.» и положительную часть ORDER_PRICE[]
    NSUInteger qty;
    NSUInteger vat;
    
    //необязательные поля
    NSString *pgGroup;// ORDER_PGROUP[] //Массив данных с идентификаторами групп продуктов (услуг). Необязательное поле.
    NSString *pinfo;// ORDER_PINFO[] //Массив данных с дополнительной информацией о продукте. В случае оформления формы заказа услуги с абонентской платы, в этом поле могут указываться реквизиты. Например, в поле названия указывается тип платежа, а в этом поле - номер лицевого счета. В IPN-запросе при этом вернётся значения поля ORDER_PINFO, которое потом удобно записать сразу в базу данных.
    PriceType priceType;
    NSNumber *ORDER_SHIPPING;
    
    
}
-(id)initLUProductWithName:(NSString*)Name code:(NSString*)Code price:(NSNumber*)Price qty:(NSUInteger)Qty vat:(NSUInteger)Vat;
-(NSString*)qtyString;
-(NSString*)vatString;
@property (nonatomic,readonly) NSString *name;
@property (nonatomic,readonly) NSString *code;
@property (nonatomic,readonly) NSNumber *price;
@property (nonatomic,readonly) NSUInteger qty;
@property (nonatomic,readonly) NSUInteger vat;
@property (nonatomic,strong) NSString *pgGroup;
@property (nonatomic,strong) NSString *pinfo;
@property (nonatomic,readwrite) PriceType priceType;
@property (nonnull,strong) NSNumber* ORDER_SHIPPING;


@end





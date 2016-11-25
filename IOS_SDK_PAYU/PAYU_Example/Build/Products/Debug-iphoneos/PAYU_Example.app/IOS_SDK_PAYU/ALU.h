//
//  ALU.h
//  alu
//
//  Created by Denis Demjanko on 31.03.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MCHelper.h"
#import "ALUProduct.h"
#import "ALUBillClientInfo.h"
#import "ALUDELIVERYData.h"
#import "ALUCardInfo.h"


#define ALUPAY_METHODTypeString(PayMetodType) [@[@"CCVISAMC"] objectAtIndex:PayMetodType]

typedef enum{
    ALUPayMethodTypeCCVISAMC
}ALUPayMethodType;

typedef void (^ALUResult)(NSData *response, NSError *error);







@interface ALU : NSObject{
    ALUResult completionHandler;
                                //Необходимые параметры
    // Поля заказа
    NSString *MERCHANT; // Идентификатор ТСП, доступный на Панели управления
    NSString *ORDER_REF; //Идентификационный номер заказа в системе ТСП (для упрощения процедуры идентификации заказа)
    NSString *ORDER_DATE; //Дата начала обработки заказа в системе в формате ГГГГ-ММ-ДД ЧЧ:ММ:СС
    ALUPayMethodType PAY_METHOD;
    NSString *ORDER_HASH; //Подпись HMAC_MD5 для отправленных данных.
    NSString *BACK_REF;
    NSString *SECRET_KEY;
    
    // Реквизиты покупателя
    ALUBillClientInfo *BIllCLIENTINFO;

    //Массив с продуктами(Товарами)
    NSMutableArray *products;
    
    //Реквизиты карты
    ALUCardInfo *CARDINFO;
    
    //                                       Необязательные поля
    //данные доставки
    ALUDELIVERYData *DELIVERYDATA;
    
    //Прочее
    NSString *CLIENT_IP;
    NSString *CLIENT_TIME;
    NSString *CC_NUMBER_TIME;
    NSString *CC_OWNER_TIME;
    
    NSNumber *ORDER_SHIPPING; // Стоимость доставки заказа
    PRICES_CURRENCYType PRICES_CURRENCY;    //Валюта, в которой указаны цены, налоги, стоимость доставки и скидки.
    BOOL TESTORDER; //  Признак тестовой операции.
    LanguageType LANGUAGE;
}
@property (nonatomic,readonly) NSString *SECRET_KEY;
@property (nonatomic,readonly) NSString *MERCHANT;
@property (nonatomic,readonly) NSString *ORDER_REF;
@property (nonatomic,readonly) NSString *ORDER_DATE;
@property (nonatomic,strong) NSString *BACK_REF;
@property (nonatomic,readonly) NSString *ORDER_HASH;
@property (nonatomic,readonly) NSMutableArray *products;

@property (nonatomic,strong)  ALUBillClientInfo *BIllCLIENTINFO;
@property (nonatomic,strong) ALUCardInfo *CARDINFO;
@property (nonatomic,readwrite) PRICES_CURRENCYType PRICES_CURRENCY;

@property (nonatomic,strong) NSNumber *ORDER_SHIPPING;
@property (nonatomic,readwrite) ALUPayMethodType PAY_METHOD;

@property (nonatomic,strong) ALUDELIVERYData *DELIVERYDATA;

@property (nonatomic,strong) NSString *CLIENT_IP;
@property (nonatomic,strong) NSString *CLIENT_TIME;
@property (nonatomic,strong) NSString *CC_NUMBER_TIME;
@property (nonatomic,strong) NSString *CC_OWNER_TIME;
@property (nonatomic,readwrite) BOOL TESTORDER;
@property (nonatomic,readwrite) LanguageType LANGUAGE;





-(id)initWithSecretKey:(NSString*)secretKey merchant:(NSString*)merchant orderRef:(NSString*)orderRef orderDate:(NSString*)orderDate;
-(BOOL)addProduct:(ALUProduct*)product error:(NSError**)error;

- (void)sendALURequstWithResult:(ALUResult)result;








@property (copy) ALUResult completionHandler;

@end

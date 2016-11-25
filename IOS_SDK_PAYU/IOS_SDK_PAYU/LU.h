//
//  LU.h
//  alu
//
//  Created by Demjanko Denis on 01.04.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MCMutableArray.h"
#import "LUProduct.h"

#define LUPAY_METHODTypeString(PayMetodType) [@[@"CCVISAMC",@"WEBMONEY",@"QIWI",@"YANDEX",@"EUROSET_SVYAZNOI",@"ALFACLICK"] objectAtIndex:PayMetodType]

typedef enum{
    LUPayMethodTypeCCVISAMC,
    LUPayMethodTypeWEBMONEY,
    LUPayMethodTypeQIWI,
    LUPayMethodTypeYANDEX,
    LUPayMethodTypeEUROSET_SVYAZNOI,
    LUPayMethodTypeALFACLICK
}LUPayMethodType;

@interface LU : NSObject{
    // Данные покупателя
    NSString * BILL_FNAME;
    NSString * BILL_LNAME;
    NSString * BILL_EMAIL;
    NSString * BILL_PHONE;
    NSString * BILL_COUNTRYCODE;
    
    NSMutableArray *products;
    NSString *SECRET_KEY;
    //Необходимые параметры
    NSString *MERCHANT; // Идентификатор ТСП, доступный на Панели управления (Управление учетными записями > Настройки учетной записи)
    NSString *ORDER_REF; //Идентификационный номер заказа в системе ТСП (для упрощения процедуры идентификации заказа)
    NSString *ORDER_DATE; //Дата начала обработки заказа в системе в формате ГГГГ-ММ-ДД ЧЧ:ММ:СС (например: «2012-05-01 21:15:45»)

    NSString *ORDER_HASH; //Подпись HMAC_MD5 для отправленных данных. Значение формируется автоматически из данных других обязательных полей
    
    
    
    
    //необязательные поля
    NSNumber *ORDER_SHIPPING; //ORDER_SHIPPING Стоимость доставки заказа
    
    PRICES_CURRENCYType PRICES_CURRENCY;    //Валюта, в которой указаны цены, налоги, стоимость доставки и скидки.
                       //Допустимые значения: RUB, EUR, USD.
                       //Если параметр не задан, значение по умолчанию: RUB.
                       //Примечание: Для совершения сделок в валюте, отличной от той, в которой указаны цены, задайте значение поля CURRENCY.
    NSNumber *DISCOUNT; //DISCOUNT Значение скидки для заказа. Поддерживаются положительные числа с «.» в качестве десятичного разделителя.                           Необязательное поле.
    NSString *DESTINATION_CITY; //DESTINATION_CITY Город доставки. Необязательное поле.Если параметр задан, клиент не сможет изменить его значение на страницах оплаты PayU
    NSString *DESTINATION_STATE; //DESTINATION_STATE Страна доставки. Необязательное поле. Если параметр задан, клиент не сможет изменить его значение на страницах оплаты PayU. Возможные значения для проверки указаны в списке cтран, доступном из Панели управления.
   

    
    
    LUPayMethodType PAY_METHOD;
    
    BOOL TESTORDER; //  Признак тестовой операции.
              //Значения:
              //TRUE
              //FALSE
    BOOL  Debug;     //  Флаг режима отладки. Необязательное поле.
                //Возможные значения:
                //0-режим неактивен
                // 1-режим активен

    
    LanguageType LANGUAGE;
    NSNumber *ORDER_TIMEOUT; //ORDER_TIMEOUT Промежуток времени, в течение которого заказ может быть размещен.
                  //Необязательное поле, значение задается в секундах
    NSString *TIMEOUT_URL;   //TIMEOUT_URL URL, на который перенаправляется клиент по истечении ORDER_TIMEOUT.
}
@property (nonatomic,readonly) NSString *SECRET_KEY;
@property (nonatomic,readonly) NSString *MERCHANT;
@property (nonatomic,readonly) NSString *ORDER_REF;
@property (nonatomic,readonly) NSString *ORDER_DATE;
@property (nonatomic,readonly) NSString *ORDER_HASH;
@property (nonatomic,readonly) NSMutableArray *products;

@property (nonatomic,strong) NSNumber *ORDER_SHIPPING;
@property (nonatomic,readwrite) PRICES_CURRENCYType PRICES_CURRENCY;
@property (nonatomic,readwrite) NSNumber *DISCOUNT;
@property (nonatomic,strong) NSString *DESTINATION_CITY;
@property (nonatomic,strong) NSString *DESTINATION_STATE;
@property (nonatomic,readwrite) LUPayMethodType PAY_METHOD;
@property (nonatomic,readwrite) BOOL TESTORDER;
@property (nonatomic,readwrite) BOOL  Debug;
@property (nonatomic,readwrite) LanguageType LANGUAGE;
@property (nonatomic,readwrite) NSNumber *ORDER_TIMEOUT;
@property (nonatomic,strong) NSString *TIMEOUT_URL;


@property (nonatomic,strong) NSString * BILL_FNAME;
@property (nonatomic,strong) NSString * BILL_LNAME;
@property (nonatomic,strong) NSString * BILL_EMAIL;
@property (nonatomic,strong) NSString * BILL_PHONE;
@property (nonatomic,strong) NSString * BILL_COUNTRYCODE;

-(id)initWithSecretKey:(NSString*)secretKey merchant:(NSString*)merchant orderRef:(NSString*)orderRef orderDate:(NSString*)orderDate;
-(BOOL)addProduct:(LUProduct*)product error:(NSError**)error;

- (NSMutableURLRequest *)getLURequstWitherror:(NSError**)error;
@end

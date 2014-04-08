//
//  AppDelegate.m
//
//  Created by Denis Demjanko on 31.03.14.
//  Copyright (c) 2014 it-dimension.com. All rights reserved.
//

#import "AppDelegate.h"
#import "ALU.h"
#import "LU.h"
#import "IRN.h"
#import "IDN.h"
#import "IOS.h"
#import "MBProgressHUD.h"

#define SHOW_PORGRESS(x) [MBProgressHUD showHUDAddedTo:x animated:YES]
#define HIDE_PORGRESS(x) [MBProgressHUD hideHUDForView:x animated:YES]

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];

    //[self sendIOSRequest];
    //[self sendIRNRequest];
    //[self payWithALU];
    
    [self payWithLU];
    
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window makeKeyAndVisible];
    
    return YES;
}


#pragma mark - IOS

-(void)sendIOSRequest{
    SHOW_PORGRESS(self.window);
    //Instant Delivery Notification
    
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    
    //данные заказа
    [orderDetails setValue:@"Android" forKey:@"MERCHANT"];
    [orderDetails setValue:@"3886786" forKey:@"REFNOEXT"];
    
    //создаем объект с секретным ключем
    IOS *irn = [[IOS alloc] initWithSecretKey:@"8~Z4?t6[c~_o4)8=R4p2"];
    //отправляем запрос
    [irn sendIOSRequest:orderDetails withResult:^(NSData *response, NSError *error) {
        HIDE_PORGRESS(self.window);
        //результат запроса
        if (response) {
            NSString *result = [[NSString alloc] initWithData:response encoding:NSUTF8StringEncoding];
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:result delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",result);
        }
        else{
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:error.localizedDescription delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",error);
        }
    }];
}

#pragma mark  - Instant Delivery Notification

-(void)sendIDNRequest{
    SHOW_PORGRESS(self.window);
    //Instant Delivery Notification
    
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    
    //данные заказа
    [orderDetails setValue:@"Android" forKey:@"MERCHANT"];
    [orderDetails setValue:@"8939241" forKey:@"ORDER_REF"];
    if (payrefno) {
        [orderDetails setValue:payrefno forKey:@"ORDER_REF"];
    }
    [orderDetails setValue:@"1.18" forKey:@"ORDER_AMOUNT"];
    [orderDetails setValue:@"RUB" forKey:@"ORDER_CURRENCY"];
    [orderDetails setValue:@"2014-04-04 01:39:59" forKey:@"IDN_DATE"];
    
    //создаем объект с секретным ключем
    IDN *irn = [[IDN alloc] initWithSecretKey:@"8~Z4?t6[c~_o4)8=R4p2"];
    //отправляем запрос
    [irn sendIDNRequest:orderDetails withResult:^(NSData *response, NSError *error) {
        HIDE_PORGRESS(self.window);
        //результат запроса
        if (response) {
            NSString *result = [[NSString alloc] initWithData:response encoding:NSUTF8StringEncoding];
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:result delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",result);
        }
        else{
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:error.localizedDescription delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",error);
        }
    }];
}

#pragma mark  - Instant Refund Notification

-(void)sendIRNRequest{
    SHOW_PORGRESS(self.window);
    //Instant Refund Notification
    
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    
    //данные заказа
    [orderDetails setValue:@"Android" forKey:@"MERCHANT"];
    [orderDetails setValue:@"9024986" forKey:@"ORDER_REF"];
    if (payrefno) {
        [orderDetails setValue:payrefno forKey:@"ORDER_REF"];
    }
    [orderDetails setValue:@"1.18" forKey:@"ORDER_AMOUNT"];
    [orderDetails setValue:@"RUB" forKey:@"ORDER_CURRENCY"];
    [orderDetails setValue:@"2014-04-04 01:26:16" forKey:@"IRN_DATE"];
    
    //создаем объект с секретным ключем
    IRN *irn = [[IRN alloc] initWithSecretKey:@"8~Z4?t6[c~_o4)8=R4p2"];
    //отправляем запрос
    [irn sendIRNRequest:orderDetails withResult:^(NSData *response, NSError *error) {
        HIDE_PORGRESS(self.window);
        //результат запроса
        if (response) {
            NSString *result = [[NSString alloc] initWithData:response encoding:NSUTF8StringEncoding];
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:result delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",result);
        }
        else{
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:error.localizedDescription delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",error);
        }
    }];
}

#pragma mark - Live Update

-(void)payWithLU{
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    NSMutableArray *products = [NSMutableArray array];

    //данные заказа
    [orderDetails setValue:@"2014-04-02 10:51:58" forKey:@"ORDER_DATE"];
    [orderDetails setValue:@"Android" forKey:@"MERCHANT"];
    [orderDetails setValue:@"3886786" forKey:@"ORDER_REF"];
    [orderDetails setValue:@"1" forKey:@"AUTOMODE"];
    [orderDetails setValue:@"http://allok.com" forKey:@"BACK_REF"]; //передаем ссылку, куда мы перейдем после успешной оплаты

    //данные покупателя
    [orderDetails setValue:@"Doe" forKey:@"BILL_FNAME"];
    [orderDetails setValue:@"John" forKey:@"BILL_LNAME"];
    [orderDetails setValue:@"shopper@payu.ro" forKey:@"BILL_EMAIL"];
    [orderDetails setValue:@"1234567890" forKey:@"BILL_PHONE"];
    [orderDetails setValue:@"TR" forKey:@"BILL_COUNTRYCODE"];
    
    //данные продукта №1
    NSMutableDictionary *product_1 = [NSMutableDictionary new];
    [product_1 setValue:@"test" forKey:@"ORDER_PNAME[]"];
    [product_1 setValue:@"112" forKey:@"ORDER_PCODE[]"];
    [product_1 setValue:@"1" forKey:@"ORDER_PRICE[]"];
    [product_1 setValue:@"1" forKey:@"ORDER_QTY[]"];
    [product_1 setValue:@"1" forKey:@"ORDER_VAT[]"];
    [product_1 setValue:@"1" forKey:@"ORDER_SHIPPING[]"];
    
    
    //задаем продукты заказу
    [products addObject:product_1];
    [orderDetails setObject:products forKey:@"PRODUCTS"];
    
    LU *lu = [[LU alloc] initWithSecretKey:@"8~Z4?t6[c~_o4)8=R4p2"];
    
    NSMutableURLRequest *request = [lu getLURequst:orderDetails];
    
    UIWebView *web = [[UIWebView alloc] initWithFrame:[UIScreen mainScreen].bounds];
    [web setDelegate:self];
    [web loadRequest:request];
    [self.window addSubview:web];
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    NSLog(@"%@",request.URL.absoluteString);
    
    NSString *string = request.URL.absoluteString;
    //отлавливаем переход на нашу ссылку
    if ([string rangeOfString:@"http://allok.com/?result=0"].location != NSNotFound) {
        [webView removeFromSuperview];
       
        NSArray *array = [request.URL.absoluteString componentsSeparatedByString:@"&"];

        //сохраняем номер заказа, он нам понадобится для "отмены транзакции" и для "подтверждения транзакци"
        payrefno = [[array objectAtIndex:3] stringByReplacingOccurrencesOfString:@"payrefno=" withString:@""];
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"Оплата прошла успешно" delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alert show];
        
        UIButton *btn_1 = [UIButton buttonWithType:UIButtonTypeSystem];
        btn_1.frame = CGRectMake(10, 10, 300, 40);
        [btn_1 setTitle:@"Отмена транзакции" forState:UIControlStateNormal];
        [btn_1 addTarget:self action:@selector(sendIRNRequest) forControlEvents:UIControlEventTouchUpInside];
        [self.window addSubview:btn_1];
        
        UIButton *btn_2 = [UIButton buttonWithType:UIButtonTypeSystem];
        btn_2.frame = CGRectMake(10, 60, 300, 40);
        [btn_2 setTitle:@"Подтверждение транзакци" forState:UIControlStateNormal];
        [btn_2 addTarget:self action:@selector(sendIDNRequest) forControlEvents:UIControlEventTouchUpInside];
        [self.window addSubview:btn_2];
        
        UIButton *btn_3 = [UIButton buttonWithType:UIButtonTypeSystem];
        btn_3.frame = CGRectMake(10, 100, 300, 40);
        [btn_3 setTitle:@"Проверка статуса" forState:UIControlStateNormal];
        [btn_3 addTarget:self action:@selector(sendIOSRequest) forControlEvents:UIControlEventTouchUpInside];
        [self.window addSubview:btn_3];
    }
        
    return YES;
}

#pragma mark - Automatic Live Update

-(void)payWithALU{
    //оплата с помошью протокола Automatic Live Update
    
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    
    //данные заказа
    [orderDetails setValue:@"2014-04-03 21:20:57" forKey:@"ORDER_DATE"];
    [orderDetails setValue:@"Android" forKey:@"MERCHANT"];
    [orderDetails setValue:@"3548968" forKey:@"ORDER_REF"];
    [orderDetails setValue:@"CCVISAMC" forKey:@"PAY_METHOD"];
    [orderDetails setValue:@"" forKey:@"BACK_REF"];
    [orderDetails setValue:@"RU" forKey:@"LANGUAGE"];
    [orderDetails setValue:@"RUB" forKey:@"PRICES_CURRENCY"];
    
    //данные карты
    [orderDetails setValue:@"523167987200335624" forKey:@"CC_NUMBER"];
    [orderDetails setValue:@"12" forKey:@"EXP_MONTH"];
    [orderDetails setValue:@"2016" forKey:@"EXP_YEAR"];
    [orderDetails setValue:@"125" forKey:@"CC_CVV"];
    [orderDetails setValue:@"FirstName" forKey:@"CC_OWNER"];
    
    //данные покупателя
    [orderDetails setValue:@"Doe" forKey:@"BILL_FNAME"];
    [orderDetails setValue:@"John" forKey:@"BILL_LNAME"];
    [orderDetails setValue:@"shopper@payu.ro" forKey:@"BILL_EMAIL"];
    [orderDetails setValue:@"1234567890" forKey:@"BILL_PHONE"];
    [orderDetails setValue:@"TR" forKey:@"BILL_COUNTRYCODE"];
    
    //данные доставки
    [orderDetails setValue:@"John" forKey:@"DELIVERY_FNAME"];
    [orderDetails setValue:@"Smith" forKey:@"DELIVERY_LNAME"];
    [orderDetails setValue:@"0729581297" forKey:@"DELIVERY_PHONE"];
    [orderDetails setValue:@"3256 Epiphenomenal Avenue" forKey:@"DELIVERY_ADDRESS"];
    [orderDetails setValue:@"55416" forKey:@"DELIVERY_ZIPCODE"];
    [orderDetails setValue:@"Minneapolis" forKey:@"DELIVERY_CITY"];
    [orderDetails setValue:@"Minnesota" forKey:@"DELIVERY_STATE"];
    [orderDetails setValue:@"MN" forKey:@"DELIVERY_COUNTRYCODE"];
    
    //данные продукта №1
    [orderDetails setValue:@"Phone" forKey:@"ORDER_PNAME[]"];
    [orderDetails setValue:@"1" forKey:@"ORDER_PCODE[]"];
    [orderDetails setValue:@"1" forKey:@"ORDER_PRICE[]"];
    [orderDetails setValue:@"1" forKey:@"ORDER_QTY[]"];
    
//    //данные продукта №2
//    [orderDetails setValue:@"test2" forKey:@"ORDER_PNAME[]"];
//    [orderDetails setValue:@"113" forKey:@"ORDER_PCODE[]"];
//    [orderDetails setValue:@"1" forKey:@"ORDER_PRICE[]"];
//    [orderDetails setValue:@"1" forKey:@"ORDER_QTY[]"];
    
    //делаем инициализацию сервиса ALU и задаем секретный ключ
    ALU *alu = [[ALU alloc] initWithSecretKey:@"8~Z4?t6[c~_o4)8=R4p2"];
    //отправляем созданный заказ
    [alu sendALURequest:orderDetails withResult:^(NSData *response, NSError *error) {
        //результат запроса
        if (response) {
            NSString *result = [[NSString alloc] initWithData:response encoding:NSUTF8StringEncoding];
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:result delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",result);
        }
        else{
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:error.localizedDescription delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alert show];
            
            NSLog(@"%@",error);
        }
    }];
}

@end

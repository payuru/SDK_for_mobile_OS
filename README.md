IOS-SDK
=======

Содежримое SDK находится в папке PAYU_SDK.
Для интеграции PAYU просто перетащите папку в свой проект.

Описание классов. Все классы работают асинхронно, через блоки.

1. ALU - класс для работы с протоколом Automatic Live Update
2. LU - класс для работы с протоколом Live Update
3. IRN - класс для работы с протоколом Instant Refund Notification
4. IDN - класс для работы с протоколом Instant Delivery Notification
5. IOS - класс для проверки статуса платежа

Подробное описание всех классов и вызов методов можно найти в тестовом проекте.

Основной принцип работы на примере ALU (Automatic Live Update)

1. Формируем заказа и передаем туда все необходимые нам данные.

    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    [orderDetails setValue:@"2014-04-01 19:30:57" forKey:@"ORDER_DATE"];
    [orderDetails setValue:@"Android" forKey:@"MERCHANT"];
    [orderDetails setValue:@"3886786" forKey:@"ORDER_REF"];
    [orderDetails setValue:@"CCVISAMC" forKey:@"PAY_METHOD"];
    [orderDetails setValue:@"" forKey:@"BACK_REF"];
    [orderDetails setValue:@"RU" forKey:@"LANGUAGE"];
    [orderDetails setValue:@"RUB" forKey:@"PRICES_CURRENCY"];

2.   Делаем инициализацию сервиса ALU и задаем секретный ключ
   
      ALU *alu = [[ALU alloc] initWithSecretKey:@"8~Z4?t6[c~_o4)8=R4p2"];

3.  Отправляем созданный заказ в пункте #1 и выводим результат
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

//
//  MainTableViewController.m
//  PAYU_Example
//
//  Created by Max on 12.11.16.
//  Copyright © 2016 IPOL OOO. All rights reserved.
//

#import "MainTableViewController.h"
#import "orderLUViewController.h"

#import "PAYU_SDK.h"



@interface MainTableViewController (){
    NSArray *menuTitleArr;
    
    NSString *date;
    NSString *payrefno;
}
@end

@implementation MainTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self showLogoNavBar];
    menuTitleArr=@[@"LU (Live Update)",@"ALU (Automatic Live Update)",@"Отмена/возврат (IRN)",@"Проверка статуса платежа (IOS)"];

}
-(void)showLogoNavBar{
        UIImageView *titleImage=[[UIImageView alloc] initWithImage:[UIImage imageNamed:@"PAYU_Logo.png"]];
        UINavigationItem *item = self.navigationController.topViewController.navigationItem;
        UIView *backView =[[UIView alloc] initWithFrame:CGRectMake(0, 0, 0, 40)];
        [titleImage setFrame:CGRectMake(-40, 0, 80, 40)];
        [backView addSubview:titleImage];
        item.titleView = backView;
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];

}

#pragma mark - Table view data source
-(double)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    return 60;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {

    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {

    return 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"MainTableViewControllerCell" forIndexPath:indexPath];
    cell.textLabel.text=[menuTitleArr objectAtIndex:indexPath.row];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    switch (indexPath.row) {
        case 0:
            [self performSegueWithIdentifier:@"segue1" sender:self];

            break;
        case 1:
            [self payWithALU];
            break;
        case 2:
            [self sendIRNRequest];
            break;
        case 3:
            [self sendIOSRequest];
            break;
            
        default:
            break;
    }
}


#pragma mark - Live Update
#pragma mark - Automatic Live Update

-(void)payWithALU{
    //инициализация сервиса ALU и задаем секретный ключ MERCHANT,orderRef и orderDate
    NSString *dateNow=[[NSDate date] description];
    NSString *dateNowString = [dateNow substringToIndex:[dateNow length]-6];
     ALU *alu = [[ALU alloc] initWithSecretKey:@"e5|S|X~0@l10_?R4b8|1" merchant:@"ipolhtst" orderRef:@"3886786" orderDate:dateNowString];
    
    alu.LANGUAGE=RU;
    // alu.TESTORDER=YES;
    alu.PAY_METHOD=LUPayMethodTypeCCVISAMC;
    
    // создаем продукты обязательне поля
    ALUProduct *product=[[ALUProduct alloc] initALUProductWithName:@"Phone" code:@"12" price:[NSNumber numberWithInt:13] qty:3 currency:NONE];
    ALUProduct *product2=[[ALUProduct alloc] initALUProductWithName:@"Phone2" code:@"12" price:[NSNumber numberWithInt:13] qty:3 currency:NONE];;

    //продукты
    [alu addProduct:product error:nil];
    [alu addProduct:product2 error:nil];
    
    //данные карты
    alu.CARDINFO=[[ALUCardInfo alloc] initWithCC_NUMBER:@"523167987200335624" EXP_MONTH:@"12" EXP_YEAR:@"2016" CC_CVV:@"125" CC_OWNER:@"FirstName"];
    
    //данные доставки
    alu.DELIVERYDATA.DELIVERY_FNAME=@"John";
    alu.DELIVERYDATA.DELIVERY_LNAME=@"Smith";
    alu.DELIVERYDATA.DELIVERY_PHONE=@"0729581297";
    alu.DELIVERYDATA.DELIVERY_ADDRESS=@"3256 Epiphenomenal Avenue";
    alu.DELIVERYDATA.DELIVERY_ZIPCODE=@"55416";
    alu.DELIVERYDATA.DELIVERY_CITY=@"Minneapolis";
    alu.DELIVERYDATA.DELIVERY_STATE=@"Minnesota";
    alu.DELIVERYDATA.DELIVERY_COUNTRYCODE=@"MN";
    alu.CLIENT_IP=@"122.22.12.23";
    // данные покупателя
    alu.BIllCLIENTINFO=[[ALUBillClientInfo alloc] initWithFNAME:@"Doe" LNAME:@"John" EMAIL:@"shopper@payu.ro" PHONE:@"1234567890" COUNTRYCODE:@"TR"];
    //alu.ORDER_SHIPPING=[NSNumber numberWithInt:1200];
    alu.PRICES_CURRENCY=RUB;
    
     //отправляем созданный заказ
     [alu sendALURequstWithResult:^(NSData *response, NSError *error) {
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
    SHOW_PORGRESS(self.view);
    //Instant Refund Notification
    
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    
    //данные заказа
    [orderDetails setValue:@"TEST" forKey:@"MERCHANT"];
    [orderDetails setValue:@"100500" forKey:@"ORDER_REF"];
    if (payrefno) {
        [orderDetails setValue:payrefno forKey:@"ORDER_REF"];
    }
    [orderDetails setValue:@"1234" forKey:@"ORDER_AMOUNT"];
    [orderDetails setValue:@"RUB" forKey:@"ORDER_CURRENCY"];
    [orderDetails setValue:@"2014-04-04 01:26:16" forKey:@"IRN_DATE"];
    
    //создаем объект с секретным ключем
    IRN *irn = [[IRN alloc] initWithSecretKey:@"AABBCCDDEEFF"];
    //отправляем запрос
    [irn sendIRNRequest:orderDetails withResult:^(NSData *response, NSError *error) {
        HIDE_PORGRESS(self.view);
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



#pragma mark - IOS

-(void)sendIOSRequest{
    SHOW_PORGRESS(self.view);
    //Instant Delivery Notification
    
    //формируем заказ
    NSMutableDictionary *orderDetails = [NSMutableDictionary new];
    
    //данные заказа
    [orderDetails setValue:@"EPAYMENT" forKey:@"MERCHANT"];
    [orderDetails setValue:@"EPAY10425" forKey:@"REFNOEXT"];
    
    //создаем объект с секретным ключем
    IOS *irn = [[IOS alloc] initWithSecretKey:@"AABBCCDDEEFF"];
    //отправляем запрос
    [irn sendIOSRequest:orderDetails withResult:^(NSData *response, NSError *error) {
        HIDE_PORGRESS(self.view);
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




/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end

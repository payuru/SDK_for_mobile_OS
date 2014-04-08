Задачи

1.	Отправить заказ по протоколу ALU

  а) Создаем объект ALUPurchaseBuilder, наполняем его данными с помощью соответствующих методов.
  
  б) Создаем объект PayU, выполняем showPurchaseFragment().

2.	 Отправить заказ по протоколу LU
  а) Создаем объект LUPurchaseBuilder, наполняем его данными с помощью соответствующих методов.

  б) Программно или с помощью разметки добавляем объект PurchaseView. Есть возможность наследоваться от него и переопределить onPageStarted() и onPageFinished().
  
  в) Создаем объект PayU, выполняем loadPurchaseView().

3. 	Проверить статус заказа

  а) Создаем объект HttpRequest, при необходимости передаем Callback
  
  б) Выполняем checkOrder()
  
  в) Выполняем execute()

4.   Отправить сообщение по протоколу IDN

  а) Создаем объект NotificationBuilder с параметром NOTIFICATION_TYPE_DELIVERY, наполняем его данными с помощью соответствующих методов
  
  б) Создаем объект HttpRequest, при необходимости передаем Callback
  
  в) Выполняем sendDeliveryNotification ()
  
  г) Выполняем execute()

5. 	Отправить сообщение по протоколу IRN

  а) Создаем объект NotificationBuilder с параметром NOTIFICATION_TYPE_REFUND, наполняем его данными с помощью соответствующих методов
  
  б) Создаем объект HttpRequest, при необходимости передаем Callback
  
  в) Выполняем sendRefundNotification ()
  
  г) Выполняем execute()

HttpRequest
Полный ответ на запрос можно получить с getResponse(). Статус код ответа и расшифровку  кода можно получить с getResponseStatus() и getResponseMessage() соответственно. 

PayUSdkTest
Максимальная сумма платежа на тестовом аккаунте – 2 рубля.



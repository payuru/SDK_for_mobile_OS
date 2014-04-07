package com.payu.sdktest;

import com.payu.payusdk.controller.HttpRequest;
import com.payu.payusdk.controller.NotificationBuilder;
import com.payu.payusdk.controller.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BrowserDemoActivity extends FragmentActivity implements
		OnClickListener {

	HttpRequest request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.clickBtn).setOnClickListener(this);
		findViewById(R.id.checkBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.checkBtn).setOnClickListener(this);
		findViewById(R.id.deliveryNotificationBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.deliveryNotificationBtn).setOnClickListener(this);
		findViewById(R.id.refundNotificationBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.refundNotificationBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		NotificationBuilder builder;

		switch (v.getId()) {
		case R.id.clickBtn:

			startActivity(new Intent(this, BrowserActivity.class));
			break;
		case R.id.checkBtn:
			request = new HttpRequest(this, new HttpRequest.Callback() {

				@Override
				public void onSuccess() {
					Toast.makeText(BrowserDemoActivity.this,
							request.getResponseStatus(), Toast.LENGTH_SHORT)
							.show();
				}

				@Override
				public void onError() {
					Toast.makeText(BrowserDemoActivity.this,
							request.getResponseStatus(), Toast.LENGTH_SHORT)
							.show();
				}
			}).checkOrder("Merchant", "127", MainActivity.SECRET_KEY);
			request.execute();
			break;
		case R.id.deliveryNotificationBtn:
			builder = new NotificationBuilder(this,
					NotificationBuilder.NOTIFICATION_TYPE_DELIVERY);
			builder.setMerchantId("Merchant");
			builder.setOrderNumber("8162953");
			builder.setOrderAmount("1.18");
			builder.setOrderCurrency("RUB");
			request = new HttpRequest(this, new HttpRequest.Callback() {

				@Override
				public void onSuccess() {
					helper.WriteDebug(request.getResponseStatus() + ":"
							+ request.getResponseMessage());
				}

				@Override
				public void onError() {
					helper.WriteDebug(request.getResponseStatus() + ":"
							+ request.getResponseMessage());
				}
			}).sendDeliveryNotification(builder, MainActivity.SECRET_KEY);
			request.execute();
			break;
		case R.id.refundNotificationBtn:
			builder = new NotificationBuilder(this,
					NotificationBuilder.NOTIFICATION_TYPE_REFUND);
			builder.setMerchantId("Merchant");
			builder.setOrderNumber("8162953");
			builder.setOrderAmount("1.18");
			builder.setOrderCurrency("RUB");
			request = new HttpRequest(this, new HttpRequest.Callback() {

				@Override
				public void onSuccess() {
					helper.WriteDebug(request.getResponseStatus() + ":"
							+ request.getResponseMessage());
				}

				@Override
				public void onError() {
					helper.WriteDebug(request.getResponseStatus() + ":"
							+ request.getResponseMessage());
				}
			}).sendRefundNotification(builder, MainActivity.SECRET_KEY);
			request.execute();
			break;
		}
	}

}

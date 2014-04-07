package com.payu.sdktest;

import com.payu.payusdk.controller.LUPurchaseBuilder;
import com.payu.payusdk.controller.PayU;
import com.payu.payusdk.view.PurchaseView;

import android.app.Activity;
import android.os.Bundle;

public class BrowserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LUPurchaseBuilder builder = new LUPurchaseBuilder(this);
		builder.setMerchantId("Merchant");
		builder.setOrderExternalNumber("127");
		builder.setPriceCurrency("RUB");
		builder.setPaymentMethod("CCVISAMC");
		builder.setReturnURL("");
		builder.setShopperFirstName("Petr");
		builder.setShopperLastName("Pupkin");
		builder.setShopperEmail("petr@pupkin.com");
		builder.setShopperPhoneNumber("+78439");
		builder.setShopperCountryCode("TR");
		builder.setIsTestOrder(String.valueOf(Boolean.TRUE));
		builder.addProduct("table", "12", "1", "", "1", "19", "", "", "");

		setContentView(R.layout.browser_layout);

		new PayU(this).loadPurchaseView(
				(PurchaseView) findViewById(R.id.webView), builder,
				MainActivity.SECRET_KEY);
	}
}

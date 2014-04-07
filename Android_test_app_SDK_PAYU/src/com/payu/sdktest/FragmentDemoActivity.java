package com.payu.sdktest;

import com.payu.payusdk.controller.ALUPurchaseBuilder;
import com.payu.payusdk.controller.PayU;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class FragmentDemoActivity extends FragmentActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.clickBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.clickBtn:
			ALUPurchaseBuilder builder = new ALUPurchaseBuilder(this);
			builder.setMerchantId("Merchant");
			builder.setOrderExternalNumber("126");
			builder.setPriceCurrency("RUB");
			builder.setPaymentMethod("CCVISAMC");
			builder.setReturnURL("");
			builder.setShopperFirstName("Petr");
			builder.setShopperLastName("Pupkin");
			builder.setShopperEmail("qewr@mail.com");
			builder.setShopperPhoneNumber("+78439");
			builder.setShopperCountryCode("TR");
			builder.setCardNumber("4355084355084358");
			builder.setCardExpiresMonth("01");
			builder.setCardExpiresYear("2016");
			builder.setCardCVV("112");
			builder.setCardOwner("Ivan Ivanov");
			builder.addProduct("table", "2", "2", "1", "", "");
			new PayU(this).showPurchaseFragment(builder,
					MainActivity.SECRET_KEY);
			break;
		}

	}
}

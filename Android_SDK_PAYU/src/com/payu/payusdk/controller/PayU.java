package com.payu.payusdk.controller;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.payu.payusdk.R;
import com.payu.payusdk.view.PurchaseDialog;
import com.payu.payusdk.view.PurchaseView;

/**
 * Класс для взаимодействия с возможностями PayU
 */
public class PayU {

	private Activity activity;

	public PayU(FragmentActivity activity) {
		this.activity = activity;
	}

	public PayU(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Отображает фрагмент для покупки по протоколу ALU
	 * 
	 * @param purchase
	 *            Объект, содержащий данные о покупке
	 * @param secretKey
	 *            Секретный ключ продавца
	 */
	public void showPurchaseFragment(ALUPurchaseBuilder purchase,
			String secretKey) {
		PurchaseDialog newFragment = new PurchaseDialog();
		Bundle bundle = new Bundle();
		bundle.putParcelable(PurchaseDialog.DATA, purchase);
		bundle.putString(PurchaseDialog.SECRET_KEY, secretKey);
		newFragment.setArguments(bundle);
		newFragment.show(
				((FragmentActivity) activity).getSupportFragmentManager(),
				activity.getString(R.string.tag));
	}

	/**
	 * Загружает форму покупки по протоколу LU
	 * 
	 * @param view
	 *            Представление браузера, используемого для отображения формы
	 * @param purchase
	 *            Объект, содержащий данные о покупке
	 * @param secretKey
	 *            Секретный ключ продавца
	 */
	public PurchaseView loadPurchaseView(PurchaseView view,
			LUPurchaseBuilder purchase, String secretKey) {
		view.postUrl(HttpRequest.PAYU_LU_URL,
				EncodingUtils.getBytes(purchase.build(secretKey), "BASE64"));
		return view;
	}
}

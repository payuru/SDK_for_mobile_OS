package com.payu.payusdk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.payu.payusdk.R;
import com.payu.payusdk.model.RequestColumns;

/**
 * Класс, хранящий данные о заказе для передачи по протоколу ALU
 */
public class ALUPurchaseBuilder implements RequestColumns, Parcelable {

	private TreeMap<String, String> data;
	private Context context;

	private static final String DATA = "DATA";

	private int purchaseCount;

	public ALUPurchaseBuilder(Context context) {
		data = new TreeMap<String, String>();
		this.context = context;
		purchaseCount = -1;
	}

	@SuppressWarnings("unchecked")
	public ALUPurchaseBuilder(Parcel in) {
		Bundle bundle = in.readBundle();
		data = (TreeMap<String, String>) bundle.getSerializable(DATA);
		purchaseCount = in.readInt();
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public static Creator<ALUPurchaseBuilder> CREATOR = new Creator<ALUPurchaseBuilder>() {

		@Override
		public ALUPurchaseBuilder createFromParcel(Parcel arg0) {
			new ALUPurchaseBuilder(arg0);
			return null;
		}

		@Override
		public ALUPurchaseBuilder[] newArray(int arg0) {
			return null;
		}

	};

	public TreeMap<String, String> getData() {
		return data;
	}

	/**
	 * @return строку, состоящую из строк данных и их длин
	 */
	@SuppressLint("SimpleDateFormat")
	public String build() {

		SimpleDateFormat sdf = new SimpleDateFormat(
				context.getString(R.string.dateFormat));
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		data.put(ORDER_DATE, sdf.format(new Date()));

		StringBuilder sb = new StringBuilder(data.size() * 2);

		for (String value : data.values()) {
			sb.append(value.length());
			sb.append(value);
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return data.toString();
	}

	public void setMerchantId(String merchant) {
		data.put(MERCHANT, merchant);
	}

	public String getMerchantId() {
		return data.get(MERCHANT);
	}

	public void setOrderExternalNumber(String number) {
		data.put(ORDER_REF, number);
	}

	public void setPaymentMethod(String method) {
		data.put(PAY_METHOD, method);
	}

	public void setLanguage(String language) {
		data.put(LANGUAGE, language);
	}

	public void setReturnURL(String url) {
		data.put(BACK_REF, url);
	}

	public void setPriceCurrency(String currency) {
		data.put(PRICES_CURRENCY, currency);
	}

	public void setShopperFirstName(String firstName) {
		data.put(BILL_FNAME, firstName);
	}

	public void setShopperLastName(String lastName) {
		data.put(BILL_LNAME, lastName);
	}

	public void setShopperEmail(String email) {
		data.put(BILL_EMAIL, email);
	}

	public void setShopperPhoneNumber(String phone) {
		data.put(BILL_PHONE, phone);
	}

	public void setShopperCountryCode(String countryCode) {
		data.put(BILL_COUNTRYCODE, countryCode);
	}

	public void setShopperFaxNumber(String number) {
		data.put(BILL_FAX, number);
	}

	public void setShopperAddress(String address) {
		data.put(BILL_ADDRESS, address);
	}

	public void setShopperAddress2(String address) {
		data.put(BILL_ADDRESS2, address);
	}

	public void setShopperZipcode(String code) {
		data.put(BILL_ZIPCODE, code);
	}

	public void setShopperCity(String city) {
		data.put(BILL_CITY, city);
	}

	public void setShopperState(String state) {
		data.put(BILL_STATE, state);
	}

	public void setCardNumber(String number) {
		data.put(CC_NUMBER, number);
	}

	public void setCardCVV(String cvv) {
		data.put(CC_CVV, cvv);
	}

	public void setCardOwner(String owner) {
		data.put(CC_OWNER, owner);
	}

	public void setCardExpiresYear(String year) {
		data.put(EXP_YEAR, year);
	}

	public void setCardExpiresMonth(String month) {
		data.put(EXP_MONTH, month);
	}

	public void setInstallmentsNumber(String number) {
		data.put(SELECTED_INSTALLMENTS_NUMBER, number);
	}

	public void setCardProgramName(String programName) {
		data.put(CARD_PROGRAM_NAME, programName);
	}

	public void setOrderTimeout(String timeout) {
		data.put(ORDER_TIMEOUT, timeout);
	}

	public void setClientIp(String ip) {
		data.put(CLIENT_IP, ip);
	}

	public void setClientTime(String time) {
		data.put(CLIENT_TIME, time);
	}

	public void setDeliveryFirstName(String firstName) {
		data.put(DELIVERY_FNAME, firstName);
	}

	public void setDeliveryLastName(String lastName) {
		data.put(DELIVERY_LNAME, lastName);
	}

	public void setDeliveryEmail(String email) {
		data.put(DELIVERY_EMAIL, email);
	}

	public void setDeliveryPhoneNumber(String phone) {
		data.put(DELIVERY_PHONE, phone);
	}

	public void setDeliveryCountryCode(String countryCode) {
		data.put(DELIVERY_COUNTRYCODE, countryCode);
	}

	public void setIsTestOrder(String flag) {
		data.put(TESTORDER, flag);
	}

	public void setDeliveryCompany(String company) {
		data.put(DELIVERY_COMPANY, company);
	}

	public void setDeliveryAddress(String address) {
		data.put(DELIVERY_ADDRESS, address);
	}

	public void setDeliveryAddress2(String address) {
		data.put(DELIVERY_ADDRESS2, address);
	}

	public void setDeliveryZipcode(String code) {
		data.put(DELIVERY_ZIPCODE, code);
	}

	public void setDeliveryCity(String city) {
		data.put(DELIVERY_CITY, city);
	}

	public void setDeliveryState(String state) {
		data.put(DELIVERY_STATE, state);
	}

	public void addProduct(String name, String code, String price,
			String quantity, String info, String version) {

		++purchaseCount;

		if (!helper.isNullOrEmpty(name)) {
			data.put(String.format(ORDER_PNAME_$, purchaseCount), name);
		}

		if (!helper.isNullOrEmpty(code)) {
			data.put(String.format(ORDER_PCODE_$, purchaseCount), code);
		}

		if (!helper.isNullOrEmpty(price)) {
			data.put(String.format(ORDER_PRICE_$, purchaseCount), price);
		}

		if (!helper.isNullOrEmpty(quantity)) {
			data.put(String.format(ORDER_QTY_$, purchaseCount), quantity);
		}

		if (!helper.isNullOrEmpty(info)) {
			data.put(String.format(ORDER_PINFO_$, purchaseCount), info);
		}

		if (!helper.isNullOrEmpty(version)) {
			data.put(String.format(ORDER_VER_$, purchaseCount), version);
		}
	}

	/**
	 * @return итоговую стоимость покупки
	 */
	public String getPurchasePrice() {
		int sum = 0;

		for (int i = 0; i < purchaseCount + 1; ++i) {
			sum += Integer.valueOf(data.get(String.format(ORDER_PRICE_$, i)));
		}

		return String.valueOf(sum);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(DATA, data);
		dest.writeBundle(bundle);
		dest.writeInt(purchaseCount);
	}
}

package com.payu.payusdk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map.Entry;
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
 * Класс, хранящий данные о заказе для передачи по протоколу LU
 */
public class LUPurchaseBuilder implements RequestColumns, Parcelable {

	private TreeMap<String, String> data;
	private LinkedList<Item> items;
	private Context context;

	private static final String DATA = "DATA";
	private static final String ITEMS = "ITEMS";

	public LUPurchaseBuilder(Context context) {
		data = new TreeMap<String, String>();
		items = new LinkedList<Item>();
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public LUPurchaseBuilder(Parcel in) {
		Bundle bundle = in.readBundle();
		data = (TreeMap<String, String>) bundle.getSerializable(DATA);
		items = (LinkedList<Item>) bundle.getSerializable(ITEMS);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	class Item {
		private TreeMap<String, String> properties;

		public Item() {
			properties = new TreeMap<String, String>();
		}
	}

	public static Creator<LUPurchaseBuilder> CREATOR = new Creator<LUPurchaseBuilder>() {

		@Override
		public LUPurchaseBuilder createFromParcel(Parcel arg0) {
			new LUPurchaseBuilder(arg0);
			return null;
		}

		@Override
		public LUPurchaseBuilder[] newArray(int arg0) {
			return null;
		}

	};

	public TreeMap<String, String> getData() {
		return data;
	}

	/**
	 * 
	 * @param secretKey
	 *            секретный ключ продавца
	 * @return строку с закодированными ключом данными
	 */
	@SuppressLint("SimpleDateFormat")
	public String build(String secretKey) {

		SimpleDateFormat sdf = new SimpleDateFormat(
				context.getString(R.string.dateFormat));
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		data.put(ORDER_DATE, sdf.format(new Date()));

		data.put(ORDER_HASH,
				HttpRequest.encodeDataString(buildForHash(), secretKey));

		StringBuilder sb = new StringBuilder(data.size() * 2);

		TreeMap<String, String> temp = new TreeMap<String, String>();
		temp.putAll(data);
		temp.putAll(items.getFirst().properties);

		boolean was = false;
		for (Entry<String, String> value : temp.entrySet()) {
			if (was) {
				sb.append("&");
			}
			sb.append(value.getKey());
			sb.append("=");
			sb.append(value.getValue());
			was = true;
		}

		return sb.toString();
	}

	/**
	 * @return строку, состоящую из строк данных и их длин
	 */
	public String buildForHash() {

		LinkedList<String> temp = new LinkedList<String>();

		for (String req : HASH_REQUIRED) {
			if (data.containsKey(req)) {
				temp.add(data.get(req));
			} else if (items.getFirst().properties.containsKey(req)) {
				temp.add(items.getFirst().properties.get(req));
			}
		}

		StringBuilder sb = new StringBuilder(data.size() * 2);
		for (String value : temp) {
			sb.append(value.length());
			sb.append(value);
		}

		return sb.toString();
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

	public void setIsTestOrder(String flag) {
		data.put(TESTORDER, flag);
	}

	public void setDebug(String debug) {
		data.put(DEBUG, debug);
	}

	public void setLanguage(String language) {
		data.put(LANGUAGE, language);
	}

	public void setReturnURL(String url) {
		data.put(BACK_REF, url);
	}

	public void setReturnURLResult(String result) {
		data.put(BACK_REF_RESULT, result);
	}

	public void setReturnURL3DSECURE(String secure) {
		data.put(BACK_REF_3DSECURE, secure);
	}

	public void setReturnURLDate(String date) {
		data.put(BACK_REF_DATE, date);
	}

	public void setReturnURLCTRL(String ctrl) {
		data.put(BACK_REF_CTRL, ctrl);
	}

	public void setPriceCurrency(String currency) {
		data.put(PRICES_CURRENCY, currency);
	}

	public void setOrderShipping(String shipping) {
		data.put(ORDER_SHIPPING, shipping);
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

	public void setDestinationCity(String city) {
		data.put(DESTINATION_CITY, city);
	}

	public void setDestinationState(String state) {
		data.put(DESTINATION_STATE, state);
	}

	public void setDestinationCountry(String country) {
		data.put(DESTINATION_COUNTRY, country);
	}

	public void setDeliveryFirstName(String firstName) {
		data.put(DELIVERY_FNAME, firstName);
	}

	public void setDeliveryLastName(String lastName) {
		data.put(DELIVERY_LNAME, lastName);
	}

	public void setDeliveryPhoneNumber(String phone) {
		data.put(DELIVERY_PHONE, phone);
	}

	public void setDeliveryCountryCode(String countryCode) {
		data.put(DELIVERY_COUNTRYCODE, countryCode);
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
			String priceType, String quantity, String tax, String group,
			String info, String version) {

		Item item = new Item();

		if (!helper.isNullOrEmpty(name)) {
			item.properties.put(ORDER_PNAME, name);
		}

		if (!helper.isNullOrEmpty(code)) {
			item.properties.put(ORDER_PCODE, code);
		}

		if (!helper.isNullOrEmpty(price)) {
			item.properties.put(ORDER_PRICE, price);
		}

		if (!helper.isNullOrEmpty(priceType)) {
			item.properties.put(ORDER_PRICE_TYPE, priceType);
		}

		if (!helper.isNullOrEmpty(quantity)) {
			item.properties.put(ORDER_QTY, quantity);
		}

		if (!helper.isNullOrEmpty(tax)) {
			item.properties.put(ORDER_VAT, tax);
		}

		if (!helper.isNullOrEmpty(group)) {
			item.properties.put(ORDER_PGROUP, group);
		}

		if (!helper.isNullOrEmpty(info)) {
			item.properties.put(ORDER_PINFO, info);
		}

		if (!helper.isNullOrEmpty(version)) {
			item.properties.put(ORDER_VER, version);
		}

		items.add(item);
	}

	/**
	 * @return итоговую стоимость покупки
	 */
	public String getPurchasePrice() {
		int sum = 0;

		for (Item item : items) {
			sum += Integer.valueOf(item.properties.get(ORDER_PRICE));
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
		bundle.putSerializable(ITEMS, items);
		dest.writeBundle(bundle);
	}
}

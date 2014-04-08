package com.payu.payusdk.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
 * Класс, хранящий данные об уведомлении на сервер
 */
public class NotificationBuilder implements RequestColumns, Parcelable {

	private TreeMap<String, String> data;
	private LinkedList<String> productsIDs;
	private LinkedList<String> productsQTY;
	private Context context;
	private String notificationType;

	private static final String DATA = "DATA";
	private static final String IDS = "IDS";
	private static final String QTY = "QTY";

	public static final String NOTIFICATION_TYPE_DELIVERY = "DELIVERY";
	public static final String NOTIFICATION_TYPE_REFUND = "REFUND";

	public NotificationBuilder(Context context, String notificationType) {
		data = new TreeMap<String, String>();
		if (notificationType.equals(NOTIFICATION_TYPE_REFUND)) {
			productsIDs = new LinkedList<String>();
			productsQTY = new LinkedList<String>();
		}
		this.context = context;
		this.notificationType = notificationType;
	}

	public static Creator<NotificationBuilder> CREATOR = new Creator<NotificationBuilder>() {

		@Override
		public NotificationBuilder createFromParcel(Parcel arg0) {
			new ALUPurchaseBuilder(arg0);
			return null;
		}

		@Override
		public NotificationBuilder[] newArray(int arg0) {
			return null;
		}

	};

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return data.toString();
	}

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

		StringBuilder sb = new StringBuilder(data.size() * 2);

		if (notificationType.equals(NOTIFICATION_TYPE_DELIVERY)) {
			data.put(IDN_DATE, sdf.format(new Date()));

			String value;
			for (String tag : NOTIFICATION_DELIVERY_ORDER) {
				if (data.containsKey(tag)) {
					value = data.get(tag);
					sb.append(value.length());
					sb.append(value);
				}
			}

		} else if (notificationType.equals(NOTIFICATION_TYPE_REFUND)) {
			data.put(IRN_DATE, sdf.format(new Date()));

			if (productsIDs.size() > 0) {
				data.put(PRODUCTS_IDS, productsIDs.toString());
				data.put(PRODUCTS_QTY, productsQTY.toString());
			}

			String value;
			for (String tag : NOTIFICATION_REFUND_ORDER) {
				if (data.containsKey(tag)) {
					value = data.get(tag);
					sb.append(value.length());
					sb.append(value);
				}
			}
		}

		helper.WriteDebug(sb.toString());

		return sb.toString();
	}

	public void setMerchantId(String merchant) {
		data.put(MERCHANT, merchant);
	}

	public String getMerchantId() {
		return data.get(MERCHANT);
	}

	public void setOrderNumber(String number) {
		data.put(ORDER_REF, number);
	}

	public void setOrderAmount(String amount) {
		data.put(ORDER_AMOUNT, amount);
	}

	public void setOrderCurrency(String currency) {
		data.put(ORDER_CURRENCY, currency);
	}

	public void addProduct(String id, String qty) {
		if (productsIDs != null) {
			productsIDs.add(id);
			productsQTY.add(qty);
		}
	}

	@SuppressWarnings("unchecked")
	public NotificationBuilder(Parcel in) {
		Bundle bundle = in.readBundle();
		data = (TreeMap<String, String>) bundle.getSerializable(DATA);
		if (notificationType.equals(NOTIFICATION_TYPE_REFUND)) {
			productsIDs = (LinkedList<String>) bundle.getSerializable(IDS);
			productsQTY = (LinkedList<String>) bundle.getSerializable(QTY);
		}
		notificationType = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(DATA, data);
		if (notificationType.equals(NOTIFICATION_TYPE_REFUND)) {
			bundle.putSerializable(IDS, productsIDs);
			bundle.putSerializable(QTY, productsQTY);
		}

		dest.writeBundle(bundle);
		dest.writeString(notificationType);
	}

}

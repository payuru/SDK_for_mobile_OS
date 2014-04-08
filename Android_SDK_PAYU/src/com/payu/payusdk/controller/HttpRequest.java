package com.payu.payusdk.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.payu.payusdk.R;
import com.payu.payusdk.model.RequestColumns;

/**
 * Класс, используемый для обмена информацией с сервером
 */
@SuppressWarnings("unused")
public class HttpRequest extends AsyncTask<Void, Boolean, Boolean> implements
		RequestColumns {

	private static final String PAYU_URL = "https://secure.payu.ru";
	public static final String PAYU_LU_URL = "https://secure.payu.ru/order/lu.php";

	static final int POST_POST_ORDER_REQ = 1;
	private static final String POST_POST_ORDER_URL = "/order/alu.php";
	static final int POST_CHECK_ORDER_REQ = 2;
	private static final String POST_CHECK_ORDER_URL = "/order/ios.php";
	static final int POST_DELIVERY_NOTIFICATION_REQ = 3;
	private static final String POST_DELIVERY_NOTIFICATION_URL = "/order/idn.php";
	static final int POST_REFUND_NOTIFICATION_REQ = 4;
	private static final String POST_REFUND_NOTIFICATION_URL = "/order/irn.php";

	private static final int STATUS_SUCCESS_CODE = 200;

	private static final String STATUS_SUCCESS = "SUCCESS";
	private static final String STATUS_FAILED = "FAILED";
	private static final String STATUS_INPUT_ERROR = "INPUT_ERROR";

	private static final String RETURN_CODE_AUTHORIZED = "AUTHORIZED";
	private static final String RETURN_CODE_3DS_ENROLLED = "3DS_ENROLLED";
	private static final String RETURN_CODE_ALREADY_AUTHORIZED = "ALREADY_AUTHORIZED";
	private static final String RETURN_CODE_AUTHORIZATION_FAILED = "AUTHORIZATION_FAILED";
	private static final String RETURN_CODE_INVALID_CUSTOMER_INFO = "INVALID_CUSTOMER_INFO";
	private static final String RETURN_CODE_INVALID_PAYMENT_INFO = "INVALID_PAYMENT_INFO";
	private static final String RETURN_CODE_INVALID_ACCOUNT = "INVALID_ACCOUNT";
	private static final String INVALID_PAYMENT_METHOD_CODE = "INVALID_PAYMENT_METHOD_CODE";
	private static final String RETURN_CODE_INVALID_CURRENCY = "INVALID_CURRENCY";
	private static final String RETURN_CODE_REQUEST_EXPIRED = "REQUEST_EXPIRED";
	private static final String RETURN_CODE_HASH_MISMATCH = "HASH_MISMATCH";

	private static final String ORDERSTATUS_NOT_FOUND = "NOT_FOUND";
	private static final String ORDERSTATUS_WAITING_PAYMENT = "WAITING_PAYMENT";
	private static final String ORDERSTATUS_CARD_NOTAUTHORIZED = "CARD_NOTAUTHORIZED";
	private static final String ORDERSTATUS_IN_PROGRESS = "IN_PROGRESS";
	private static final String ORDERSTATUS_PAYMENT_AUTHORIZED = "PAYMENT_AUTHORIZED";
	private static final String ORDERSTATUS_COMPLETE = "COMPLETE";
	private static final String ORDERSTATUS_FRAUD = "FRAUD";
	private static final String ORDERSTATUS_INVALID = "INVALID";
	private static final String ORDERSTATUS_TEST = "TEST";
	private static final String ORDERSTATUS_CASH = "CASH";
	private static final String ORDERSTATUS_REVERSED = "REVERSED";
	private static final String ORDERSTATUS_REFUND = "REFUND";

	private static final String NOTIFICATION_RESPONSE_OK = "1";
	private static final String NOTIFICATION_RESPONSE_ALREADY_DONE = "7";

	private static final String ENCODING_TYPE = "HmacMD5";

	private static final Integer REQ_TYPE_POST = 1;
	private static final Integer REQ_TYPE_GET = 0;

	private final Context mCtx;
	private String response;
	private Toast toast;
	private Callback callback;
	private RequestCallback<Void, Boolean> requestCallback;

	private String status;
	private String returnMessage;

	public HttpRequest(Context ctx, Callback callback) {
		mCtx = ctx;
		this.callback = callback;
	}

	public HttpRequest(Fragment fragment, Callback callback) {
		this(fragment.getActivity(), callback);
	}

	public HttpRequest(Context ctx) {
		mCtx = ctx;
	}

	public HttpRequest(Fragment fragment) {
		this(fragment.getActivity());
	}

	private String makeRequest(int typeOfRequest, Object data) {

		try {
			switch (typeOfRequest) {
			case POST_POST_ORDER_REQ:
				response = GetJSONString(POST_POST_ORDER_URL, REQ_TYPE_POST,
						data);
				break;
			case POST_CHECK_ORDER_REQ:
				response = GetJSONString(POST_CHECK_ORDER_URL, REQ_TYPE_POST,
						data);
				break;
			case POST_DELIVERY_NOTIFICATION_REQ:
				response = GetJSONString(POST_DELIVERY_NOTIFICATION_URL,
						REQ_TYPE_POST, data);
				break;
			case POST_REFUND_NOTIFICATION_REQ:
				response = GetJSONString(POST_REFUND_NOTIFICATION_URL,
						REQ_TYPE_POST, data);
				break;
			}

			helper.WriteDebug("response: " + response);

		} catch (Exception e) {
			helper.WriteDebug(e.toString());
			response = null;
		}

		return response;
	}

	/**
	 * Посылает данные о заказе по протоколу ALU
	 * 
	 * @param data
	 *            Данные о покупке
	 * @param secretKey
	 *            Секретный ключ продавца
	 * @return запрос для выполнения методом {@link AsyncTask#execute}
	 */
	public HttpRequest postOrder(final ALUPurchaseBuilder data,
			final String secretKey) {

		requestCallback = new RequestCallback<Void, Boolean>() {

			@Override
			public Boolean doInBackground(Void... args) {

				MultipartEntityBuilder builder = MultipartEntityBuilder
						.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addTextBody(ORDER_HASH,
						encodeDataString(data.build(), secretKey));

				helper.WriteDebug(data.toString());

				for (Entry<String, String> entry : data.getData().entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue());
				}

				try {

					makeRequest(POST_POST_ORDER_REQ, builder.build());
					if (response == null) {
						return false;
					}

					status = getXMLFieldValue(response, STATUS);
					returnMessage = getXMLFieldValue(response, RETURN_MESSAGE);

					if (status != null) {
						if (!status.equals(STATUS_SUCCESS)) {
							return false;
						}
					} else {
						return false;
					}

				} catch (Exception e) {
					helper.WriteDebug(e.toString());
					return false;
				}

				return true;
			}
		};

		return this;
	}

	/**
	 * Посылает уведомление о наличии товара
	 * 
	 * @param data
	 *            Данные о заказе
	 * @param secretKey
	 *            Секретный ключ продавца
	 * @return запрос для выполнения методом {@link AsyncTask#execute}
	 */
	public HttpRequest sendDeliveryNotification(final NotificationBuilder data,
			final String secretKey) {

		requestCallback = new RequestCallback<Void, Boolean>() {

			@Override
			public Boolean doInBackground(Void... args) {

				MultipartEntityBuilder builder = MultipartEntityBuilder
						.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addTextBody(ORDER_HASH,
						encodeDataString(data.build(), secretKey));

				helper.WriteDebug(data.toString());

				for (Entry<String, String> entry : data.getData().entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue());
				}

				try {

					makeRequest(POST_DELIVERY_NOTIFICATION_REQ, builder.build());

					if (response == null) {
						return false;
					}

					status = getNotificationResponseCode(response);
					returnMessage = getNotificationResponseMessage(response);

					if (status != null) {
						if (!status.equals(NOTIFICATION_RESPONSE_OK)
								&& !status
										.equals(NOTIFICATION_RESPONSE_ALREADY_DONE)) {
							return false;
						}
					} else {
						return false;
					}

				} catch (Exception e) {
					helper.WriteDebug(e.toString());
					return false;
				}

				return true;
			}
		};

		return this;
	}

	/**
	 * Посылает уведомление о возврате денег
	 * 
	 * @param data
	 *            Данные о заказе
	 * @param secretKey
	 *            Секретный ключ продавца
	 * @return запрос для выполнения методом {@link AsyncTask#execute}
	 */
	public HttpRequest sendRefundNotification(final NotificationBuilder data,
			final String secretKey) {

		requestCallback = new RequestCallback<Void, Boolean>() {

			@Override
			public Boolean doInBackground(Void... args) {

				MultipartEntityBuilder builder = MultipartEntityBuilder
						.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addTextBody(ORDER_HASH,
						encodeDataString(data.build(), secretKey));

				helper.WriteDebug(data.toString());

				for (Entry<String, String> entry : data.getData().entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue());
				}

				try {

					makeRequest(POST_REFUND_NOTIFICATION_REQ, builder.build());

					if (response == null) {
						return false;
					}

					status = getNotificationResponseCode(response);
					returnMessage = getNotificationResponseMessage(response);

					if (status != null) {
						if (!status.equals(NOTIFICATION_RESPONSE_OK)
								&& !status
										.equals(NOTIFICATION_RESPONSE_ALREADY_DONE)) {
							return false;
						}
					} else {
						return false;
					}

				} catch (Exception e) {
					helper.WriteDebug(e.toString());
					return false;
				}

				return true;
			}
		};

		return this;
	}

	/**
	 * Посылает запрос на проверку статуса заказа
	 * 
	 * @param merchant
	 *            Идентификатор продавца
	 * @param orderReference
	 *            номер заказа в системе
	 * @param secretKey
	 *            Секретный ключ продавца
	 * @return запрос для выполнения методом {@link AsyncTask#execute}
	 */
	public HttpRequest checkOrder(final String merchant,
			final String orderReference, final String secretKey) {

		requestCallback = new RequestCallback<Void, Boolean>() {

			@Override
			public Boolean doInBackground(Void... args) {

				MultipartEntityBuilder builder = MultipartEntityBuilder
						.create();
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

				builder.addTextBody(MERCHANT, merchant);
				builder.addTextBody(REFNOEXT, orderReference);

				StringBuilder sb = new StringBuilder();
				sb.append(merchant.length());
				sb.append(merchant);
				sb.append(orderReference.length());
				sb.append(orderReference);
				builder.addTextBody(HASH,
						encodeDataString(sb.toString(), secretKey));

				try {

					makeRequest(POST_CHECK_ORDER_REQ, builder.build());
					if (response == null) {
						return false;
					}

					status = getXMLFieldValue(response, ORDER_STATUS);
					returnMessage = getXMLFieldValue(response, REFNO);

					if (status != null) {
						if (!status.equals(ORDERSTATUS_COMPLETE)
								&& !status.equals(ORDERSTATUS_CASH)) {
							return false;
						}
					} else {
						return false;
					}

				} catch (Exception e) {
					helper.WriteDebug(e.toString());
					return false;
				}

				return true;
			}
		};

		return this;
	}

	/**
	 * @return полный текст ответа на запрос к серверу
	 */
	public String getResponseStatus() {
		return status;
	}

	/**
	 * @return подробное сообщение о результате запроса
	 */
	public String getResponseMessage() {
		return returnMessage;
	}

	/**
	 * @return код или статус ответа на запрос
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * 
	 * @param data
	 *            данные для шифрования
	 * @param secretKey
	 *            ключ продавца
	 * @return закодированную ключом строку
	 */
	public static String encodeDataString(String data, String secretKey) {

		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(),
				ENCODING_TYPE);

		Mac mac = null;
		try {
			mac = Mac.getInstance(ENCODING_TYPE);
			mac.init(keySpec);
		} catch (NoSuchAlgorithmException e) {
			helper.WriteError(e.toString());
		} catch (InvalidKeyException e) {
			helper.WriteError(e.toString());
		}

		byte[] result = mac.doFinal(data.getBytes());
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < result.length; ++i) {
			hexString.append(Integer.toHexString(0xFF & result[i] | 0x100)
					.substring(1, 3));
		}
		return hexString.toString();
	}

	private String GetJSONString(String url, int type, Object data)
			throws IOException {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		url = PAYU_URL + url;

		if (type == REQ_TYPE_POST) {

			HttpPost httpPost = new HttpPost(url);

			try {
				httpPost.setEntity((HttpEntity) data);
				HttpResponse response = client.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == STATUS_SUCCESS_CODE) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					helper.WriteInfo("Can't make HTTPPost: " + url
							+ "\nstatuscode = " + statusCode);
				}
			} catch (IOException e) {
				helper.WriteError(e.toString());
				status = mCtx.getString(R.string.error);
				returnMessage = mCtx.getString(R.string.cannotConnectServer);
				return null;
			}

		} else {

			HttpGet httpGet = new HttpGet(url);
			if (data != null) {
				url += data;
			}

			try {
				HttpResponse response = client.execute(httpGet);
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == STATUS_SUCCESS_CODE) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					helper.WriteInfo("Can't make HTTPGet: " + url
							+ "\nstatuscode = " + statusCode);
				}
			} catch (IOException e) {
				helper.WriteError(e.toString());
				status = mCtx.getString(R.string.error);
				returnMessage = mCtx.getString(R.string.cannotConnectServer);
				return null;
			}

		}

		return builder.toString();
	}

	/**
	 * @param data
	 *            текст xml-данных
	 * @param field
	 *            тег, значение которого надо найти
	 * @return значение первого встретившегося тега XML-файла
	 */
	private String getXMLFieldValue(String data, String field) {

		if (data.indexOf(field) != -1) {
			int start = data.indexOf(field) + field.length() + 1;
			int end = data.indexOf(field, start) - 2;

			return data.substring(start, end);
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param response
	 *            ответ сервера на запрос
	 *            {@link HttpRequest#sendDeliveryNotification} или
	 *            {@link HttpRequest#sendRefundNotification}
	 * @return код ответа
	 */
	private String getNotificationResponseCode(String response) {
		if (response != null) {
			int start = response.indexOf("|") + 1;
			int end = response.indexOf("|", start + 1);

			return response.substring(start, end);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param response
	 *            ответ сервера на запрос
	 *            {@link HttpRequest#sendDeliveryNotification} или
	 *            {@link HttpRequest#sendRefundNotification}
	 * @return расшифровку кода ответа
	 */
	private String getNotificationResponseMessage(String response) {
		if (response != null) {
			int start = response.indexOf("|") + 1;
			start = response.indexOf("|", start + 1) + 1;
			int end = response.indexOf("|", start + 1);

			return response.substring(start, end);
		} else {
			return null;
		}
	}

	private void showToast(final String message) {

		((Activity) mCtx).runOnUiThread(new Runnable() {
			@Override
			public void run() {

				if (toast != null) {
					toast.setText(message);
					toast.show();
				} else {
					toast = Toast.makeText(mCtx, message, Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
	}

	/**
	 * Интерфейс, описывающий методы, вызываемые при выполнении запроса
	 */
	public interface Callback {

		public void onSuccess();

		public void onError();
	}

	private interface RequestCallback<A extends Object, B extends Object> {

		public B doInBackground(A... args);
	}

	@Override
	protected void onPreExecute() {
		((Activity) mCtx).setProgressBarIndeterminateVisibility(true);
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if (requestCallback != null) {
			return (Boolean) requestCallback.doInBackground(params);
		} else {
			throw new NoRequestCallbackException();
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		((Activity) mCtx).setProgressBarIndeterminateVisibility(false);
		try {
			if (callback != null) {
				if (result) {
					callback.onSuccess();
				} else {
					callback.onError();
				}
			}
		} catch (IllegalStateException e) {
			helper.WriteDebug(e.getMessage());
		}

		super.onPostExecute(result);
	}

	/**
	 * Класс ошибки, выкидываемой при выполнении пустого запроса
	 */
	class NoRequestCallbackException extends RuntimeException {
		private static final long serialVersionUID = 618062735722934925L;
	}
}

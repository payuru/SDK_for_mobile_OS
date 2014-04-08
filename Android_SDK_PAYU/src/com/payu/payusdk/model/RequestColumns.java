package com.payu.payusdk.model;

/**
 * Интерфейс, описывающий строки для обмена информацией с сервером.
 */
public interface RequestColumns {

	static final String MERCHANT = "MERCHANT";
	static final String ORDER_REF = "ORDER_REF";
	static final String ORDER_DATE = "ORDER_DATE";
	static final String PAY_METHOD = "PAY_METHOD";
	static final String LANGUAGE = "LANGUAGE";
	static final String ORDER_HASH = "ORDER_HASH";
	static final String BACK_REF = "BACK_REF";

	static final String ORDER_PNAME_$ = "ORDER_PNAME[%1$d]";
	static final String ORDER_PCODE_$ = "ORDER_PCODE[%1$d]";
	static final String ORDER_PRICE_$ = "ORDER_PRICE[%1$d]";
	static final String ORDER_QTY_$ = "ORDER_QTY[%1$d]";
	static final String PRICES_CURRENCY = "PRICES_CURRENCY";

	static final String BILL_LNAME = "BILL_LNAME";
	static final String BILL_FNAME = "BILL_FNAME";
	static final String BILL_EMAIL = "BILL_EMAIL";
	static final String BILL_PHONE = "BILL_PHONE";
	static final String BILL_COUNTRYCODE = "BILL_COUNTRYCODE";

	static final String CC_NUMBER = "CC_NUMBER";
	static final String EXP_MONTH = "EXP_MONTH";
	static final String EXP_YEAR = "EXP_YEAR";
	static final String CC_CVV = "CC_CVV";
	static final String CC_OWNER = "CC_OWNER";

	static final String ORDER_PINFO_$ = "ORDER_PINFO[%1$d]";
	static final String ORDER_VER_$ = "ORDER_VER[%1$d]";
	static final String SELECTED_INSTALLMENTS_NUMBER = "SELECTED_INSTALLMENTS_NUMBER";
	static final String CARD_PROGRAM_NAME = "CARD_PROGRAM_NAME";
	static final String ORDER_TIMEOUT = "ORDER_TIMEOUT";

	static final String BILL_FAX = "BILL_FAX";
	static final String BILL_ADDRESS = "BILL_ADDRESS";
	static final String BILL_ADDRESS2 = "BILL_ADDRESS2";
	static final String BILL_ZIPCODE = "BILL_ZIPCODE";
	static final String BILL_CITY = "BILL_CITY";
	static final String BILL_STATE = "BILL_STATE";

	static final String DELIVERY_LNAME = "DELIVERY_LNAME";
	static final String DELIVERY_FNAME = "DELIVERY_FNAME";
	static final String DELIVERY_EMAIL = "DELIVERY_EMAIL";
	static final String DELIVERY_PHONE = "DELIVERY_PHONE";
	static final String DELIVERY_COMPANY = "DELIVERY_COMPANY";
	static final String DELIVERY_ADDRESS = "DELIVERY_ADDRESS";
	static final String DELIVERY_ADDRESS2 = "DELIVERY_ADDRESS2";
	static final String DELIVERY_ZIPCODE = "DELIVERY_ZIPCODE";
	static final String DELIVERY_CITY = "DELIVERY_CITY";
	static final String DELIVERY_STATE = "DELIVERY_STATE";
	static final String DELIVERY_COUNTRYCODE = "DELIVERY_COUNTRYCODE";

	static final String CLIENT_IP = "CLIENT_IP";
	static final String CLIENT_TIME = "CLIENT_TIME";
	static final String TESTORDER = "TESTORDER";

	static final String REFNO = "REFNO";
	static final String ORDER_AMOUNT = "ORDER_AMOUNT";
	static final String ORDER_CURRENCY = "ORDER_CURRENCY";
	static final String IDN_DATE = "IDN_DATE";
	static final String IRN_DATE = "IRN_DATE";
	static final String IDN_PRN = "IDN_PRN";
	static final String RESPONSE_CODE = "RESPONSE_CODE";
	static final String RESPONSE_MSG = "RESPONSE_MSG";
	static final String PRODUCTS_IDS = "PRODUCTS_IDS";
	static final String PRODUCTS_QTY = "PRODUCTS_QTY";
	static final String ALIAS = "ALIAS";
	static final String STATUS = "STATUS";
	static final String ORDERSTATUS = "ORDERSTATUS";
	static final String ORDER_STATUS = "ORDER_STATUS";
	static final String RETURN_CODE = "RETURN_CODE";
	static final String RETURN_MESSAGE = "RETURN_MESSAGE";
	static final String DATE = "DATE";
	static final String URL_3DS = "URL_3DS";
	static final String HASH = "HASH";
	static final String REFNOEXT = "REFNOEXT";

	static final String DEBUG = "DEBUG";
	static final String ORDER_PNAME = "ORDER_PNAME[]";
	static final String ORDER_PCODE = "ORDER_PCODE[]";
	static final String ORDER_PRICE = "ORDER_PRICE[]";
	static final String ORDER_PRICE_TYPE = "ORDER_PRICE_TYPE[]";
	static final String ORDER_QTY = "ORDER_QTY[]";
	static final String ORDER_VAT = "ORDER_VAT[]";
	static final String ORDER_PGROUP = "ORDER_PGROUP[]";
	static final String DISCOUNT = "DISCOUNT";
	static final String DESTINATION_CITY = "DESTINATION_CITY";
	static final String DESTINATION_STATE = "DESTINATION_STATE";
	static final String DESTINATION_COUNTRY = "DESTINATION_COUNTRY";
	static final String BACK_REF_RESULT = "RESULT";
	static final String BACK_REF_3DSECURE = "3DSECURE";
	static final String BACK_REF_DATE = "DATE";
	static final String BACK_REF_CTRL = "CTRL";
	static final String ORDER_PINFO = "ORDER_PINFO[]";
	static final String ORDER_VER = "ORDER_VER[]";
	static final String ORDER_SHIPPING = "ORDER_SHIPPING";

	static final String[] HASH_REQUIRED = { MERCHANT, ORDER_REF, ORDER_DATE,
			ORDER_PNAME, ORDER_PCODE, ORDER_PINFO, ORDER_PRICE, ORDER_QTY,
			ORDER_VAT, ORDER_SHIPPING, PRICES_CURRENCY, PAY_METHOD };

	static final String[] NOTIFICATION_DELIVERY_ORDER = { MERCHANT, ORDER_REF,
			ORDER_AMOUNT, ORDER_CURRENCY, IDN_DATE };

	static final String[] NOTIFICATION_REFUND_ORDER = { MERCHANT, ORDER_REF,
			ORDER_AMOUNT, ORDER_CURRENCY, IRN_DATE };
}

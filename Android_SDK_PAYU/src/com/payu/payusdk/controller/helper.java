package com.payu.payusdk.controller;

import android.util.Log;

public class helper {
	static String LogTag = "PayU";

	public static void WriteLog(String text) {
		Log.d(LogTag, text);
	}

	public static void WriteDebug(String text) {
		Log.d(LogTag, text);
	}

	public static void WriteInfo(String text) {
		Log.i(LogTag, text);
	}

	public static void WriteError(String text) {
		Log.e(LogTag, text);
	}

	public static void WriteWarn(String text) {
		Log.w(LogTag, text);
	}

	public static boolean isNullOrEmpty(final String str) {
		if (str == null) {
			return true;
		}

		if (str.trim().equals("")) {
			return true;
		}
		return false;
	}
}

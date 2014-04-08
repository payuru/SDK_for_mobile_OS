package com.payu.payusdk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PurchaseView extends WebView {

	private PurchaseView purchaseView;

	public PurchaseView(Context context) {
		super(context);
		init(context);
	}

	public PurchaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init(Context context) {

		purchaseView = this;

		getSettings().setJavaScriptEnabled(true);
		getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		getSettings().setDomStorageEnabled(true);

		setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				purchaseView.onPageFinished(view, url);
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				purchaseView.onPageStarted(view, url, favicon);
				super.onPageStarted(view, url, favicon);
			}
		});
	}

	/**
	 * Метод, вызываемый когда страница загружена
	 * 
	 * @see WebViewClient#onPageFinished
	 */
	public void onPageFinished(WebView view, String url) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && canGoBack()) {
			goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Метод, вызываемый когда страница начинает загружаться
	 * 
	 * @see WebViewClient#onPageStarted
	 */
	public void onPageStarted(WebView view, String url, Bitmap favicon) {

	}
}

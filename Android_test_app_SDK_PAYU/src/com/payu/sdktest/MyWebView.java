package com.payu.sdktest;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.Toast;

import com.payu.payusdk.view.PurchaseView;

public class MyWebView extends PurchaseView {

	Context context;

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public MyWebView(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		Toast.makeText(context, "Finished", Toast.LENGTH_SHORT).show();
	}

}

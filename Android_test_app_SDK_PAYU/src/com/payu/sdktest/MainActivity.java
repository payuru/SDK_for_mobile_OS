package com.payu.sdktest;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.content.Intent;

public class MainActivity extends ListActivity {

	public static final String SECRET_KEY = "key";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] arr = new String[] { "Фрагмент", "Браузер" };
		getListView().setAdapter(
				new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, arr));
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					startActivity(new Intent(MainActivity.this,
							FragmentDemoActivity.class));
					break;
				case 1:
					startActivity(new Intent(MainActivity.this,
							BrowserDemoActivity.class));
					break;
				}
			}
		});
	}

}

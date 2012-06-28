package com.myorg.webviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebviewDemoActivity extends Activity {
	EditText address;
	Button go;
	WebView webview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		address = (EditText) findViewById(R.id.etAddress);
		go = (Button) findViewById(R.id.btnGo);
		webview = (WebView) findViewById(R.id.webview);
		
		go.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String uriStr=address.getText().toString();
				if(!uriStr.equals(""))
				{
					webview.loadUrl(uriStr);
				}
			}
		});
		
		webview.setWebViewClient(webViewClient);
	}
	
	WebViewClient webViewClient=new WebViewClient()
	{
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return false;
		}
		
	};
	
}
package com.android.example;



import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;


public class Educational_Content extends Activity{
	WebView browser;
	public void onCreate(Bundle save)
	{
		super.onCreate(save);
            setContentView(R.layout.htmlcontent);
		
		browser = (WebView)findViewById(R.id.webview);
		if (new File("/mnt/extsd/Instructions/educational.html").exists())
		{
			browser.loadUrl("file:\\mnt\\extsd\\Instructions\\educational.html");
		}
		else if (new File("/mnt/sdcard/Instructions/educational.html").exists())
			{
				browser.loadUrl("file:\\mnt\\sdcard\\Instructions\\educational.html");
	
			}
		else
		{
			browser.loadUrl("file:///android_asset/educational.html");	
		}
	}
	public void Proceed(View v)
	{
		 Intent myIntent = new Intent(Educational_Content.this, EducationContentDemo.class);
			//myIntent.putExtra("selectedItem", item);
                        //myIntent.putExtra("completePath",Path+item+"/");
                       //System.out.println("selectedItem"+item);
             		startActivity(myIntent);
	}
}

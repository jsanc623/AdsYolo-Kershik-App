package com.jsanc623.adsyolo.kerbeta;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
    	MainActivity.this.startActivity(myIntent);
	}
}
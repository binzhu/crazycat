package com.example.crasycat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(app.sUncaughtExceptionHandler);
		setContentView(new playground(this));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Thread.setDefaultUncaughtExceptionHandler(app.sUncaughtExceptionHandler);
	}

	

}

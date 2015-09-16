package com.example.crasycat;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;

public class app extends Application{
	public static final UncaughtExceptionHandler sUncaughtExceptionHandler = Thread
			.getDefaultUncaughtExceptionHandler();

}

package com.serkovn.clevermouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.serkovn.clevermouse.CleverMouse;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CleverMouse(), config);
		SharedPreferences sharedPreferences = this.getSharedPreferences("record", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
	}
}


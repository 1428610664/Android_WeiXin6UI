package com.gzc.weixin6ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * 
 * @author gzc
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		this.setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}

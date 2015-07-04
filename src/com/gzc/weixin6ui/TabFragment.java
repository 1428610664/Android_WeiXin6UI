package com.gzc.weixin6ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author gzc
 *
 */
public class TabFragment extends Fragment {

	private String mTitle = "Default";	
	public static final String TITLE = "title"; 
	
	// 一旦Fragment已经被创建，要创建它自己的用户界面时调用该方法。
	// 一般Fragment的UI初始化放在这个函数里，设置UI布局。
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(null != this.getArguments()){
			this.mTitle = this.getArguments().getString(TITLE);			
		}
		
		// 选项卡Fragment里，现在暂时只有一个TextView
		TextView tv = new TextView(this.getActivity());
		tv.setTextSize(20);
		tv.setBackgroundColor(Color.parseColor("#ffffffff"));
		tv.setText(mTitle);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
	
}

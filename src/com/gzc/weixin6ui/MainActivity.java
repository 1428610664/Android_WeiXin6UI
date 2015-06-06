package com.gzc.weixin6ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

/**
 * 要通过Android支持包来使用Fragment，必须保证Activity是继承自FragmentActivity类。
 * @author gzc
 *
 */
public class MainActivity extends FragmentActivity {
	
	private ViewPager mViewPager = null;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private String[] mTitles = new String[]{
			"第1个Fragment",
			"第2个Fragment",
			"第3个Fragment",
			"第4个Fragment"
	};
	private FragmentPagerAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		this.setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		
		initView();
		this.initDatas();
		mViewPager.setAdapter(mAdapter);
	}
	
	// 菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//==========================
	
	void initView(){
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
	}
	
	void initDatas(){
		for(String title : this.mTitles){
			TabFragment tabFragment = new TabFragment();
			Bundle bundle = new Bundle();
			bundle.putString(TabFragment.TITLE, title);
			// 设置TabFragment的标题
			tabFragment.setArguments(bundle);
			this.mTabs.add(tabFragment);
		}
		
		// 设置Fragment的设配器
		mAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()){
			@Override
			public int getCount(){
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position)	{
				return mTabs.get(position);
			}
		};
	}
	
	
}

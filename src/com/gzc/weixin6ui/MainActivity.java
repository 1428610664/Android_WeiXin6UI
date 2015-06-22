package com.gzc.weixin6ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 要通过Android支持包来使用Fragment，必须保证Activity是继承自FragmentActivity类。
 * @author gzc
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {
	
	private ViewPager mViewPager = null;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private String[] mTitles = new String[]{
			"第1个Fragment",
			"第2个Fragment",
			"第3个Fragment",
			"第4个Fragment"
	};
	private FragmentPagerAdapter mAdapter = null;
	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		this.setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		
		initView();
		this.initDatas();
		initEvent();
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
		
		ChangeColorIconWithText one = (ChangeColorIconWithText)findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText)findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText)findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);
		ChangeColorIconWithText four = (ChangeColorIconWithText)findViewById(R.id.id_indicator_four);
		mTabIndicators.add(four);		
		
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		// 初始显示第一个
		one.setIconAlpha(1.0f);
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
	
	private void initEvent(){
		this.mViewPager.setOnPageChangeListener(this);
	}
	
	//===========重写OnClickListener函数==========

	@Override
	public void onClick(View v) {
		clickTab(v);		
	}
	
	//===========重写OnClickListener函数==========
	
	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	private void clickTab(View v)	{
		resetOtherTabs();

		switch (v.getId()){
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicators.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}
	}	
	
	/**
	 * 重置其他的TabIndicator的颜色
	 */
	private void resetOtherTabs()	{
		for (int i = 0; i < mTabIndicators.size(); i++){
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

	//======重写OnPageChangeListener接口函数==============
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
		if(positionOffset > 0){
			ChangeColorIconWithText left = this.mTabIndicators.get(position);
			ChangeColorIconWithText right = this.mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		
	}
	//======重写OnPageChangeListener接口函数==============
	
}

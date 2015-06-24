package com.gzc.weixin6ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

/**
 * Ҫͨ��Android֧�ְ���ʹ��Fragment�����뱣֤Activity�Ǽ̳���FragmentActivity�ࡣ
 * 
 * @author gzc
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {

	private ViewPager mViewPager = null;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private String[] mTitles = new String[] { "��1��Fragment", "��2��Fragment",
			"��3��Fragment", "��4��Fragment" };
	private FragmentPagerAdapter mAdapter = null;
	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		setOverflowButtonAlways();
		getActionBar().setDisplayShowHomeEnabled(false);

		initView();
		this.initDatas();
		initEvent();
		mViewPager.setAdapter(mAdapter);
	}

	// �˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * �ڲ˵���ʱ�ص�������menu��ʾicon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu){
		if (featureId == Window.FEATURE_ACTION_BAR && null != menu){
			try {
				Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
				m.setAccessible(true);
				m.invoke(menu, true);
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}		
		
		return super.onMenuOpened(featureId, menu);
	}

	// ==========================
	
	/*	
�õģ��������䶼��Ϊ���޸�Ĭ��ActionBar��һЩ��ʾ��
��һ��sHasPermanentMenuKey��Ҫ��Ϊ�ˣ�
ǿ����ʾ��overflowButton��������ʵ��menu�������޷���ʾ����
�ڶ�����Ϊ������menuItem������ʾ��icon��

����һ�����д��룺
menuKey.setAccessible(true);//menuKey����һ�����ԣ����д�����ǿ�����ÿ��Է��ʣ�����˽�������޷�����
menuKey.setBoolean(config, false);//����Ϊconfig��ViewConfiguration���󣩵�menuKey���Ը�ֵ��
m.invoke(menu, true);//m�Ǹ�������������˼���ǵ���menu��MenuBuilder���󣩵�setOptionalIconsVisible�������������Ĳ���Ϊtrue.
*/

	/** ��ʾ�ӺŰ�ť */
	void setOverflowButtonAlways() {
		ViewConfiguration config = ViewConfiguration.get(this);
		try {
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if(null != menuKey){
				menuKey.setAccessible(true);
				menuKey.setBoolean(config, false);
			}else{
				Log.e("MainActivity->setOverflowButtonAlways( )", "menuKey is null !!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);
		ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
		mTabIndicators.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		// ��ʼ��ʾ��һ��
		one.setIconAlpha(1.0f);
	}

	void initDatas() {
		for (String title : this.mTitles) {
			TabFragment tabFragment = new TabFragment();
			Bundle bundle = new Bundle();
			bundle.putString(TabFragment.TITLE, title);
			// ����TabFragment�ı���
			tabFragment.setArguments(bundle);
			this.mTabs.add(tabFragment);
		}

		// ����Fragment��������
		mAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabs.get(position);
			}
		};
	}

	private void initEvent() {
		this.mViewPager.setOnPageChangeListener(this);
	}

	// ===========��дOnClickListener����==========

	@Override
	public void onClick(View v) {
		clickTab(v);
	}

	// ===========��дOnClickListener����==========

	/**
	 * ���Tab��ť
	 * 
	 * @param v
	 */
	private void clickTab(View v) {
		resetOtherTabs();

		switch (v.getId()) {
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
	 * ����������TabIndicator����ɫ
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

	// ======��дOnPageChangeListener�ӿں���==============

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

		if (positionOffset > 0) {
			ChangeColorIconWithText left = this.mTabIndicators.get(position);
			ChangeColorIconWithText right = this.mTabIndicators
					.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
	}

	@Override
	public void onPageSelected(int arg0) {

	}
	// ======��дOnPageChangeListener�ӿں���==============

}

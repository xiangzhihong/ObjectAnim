package com.xzh.objectanimator.apdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xzh.objectanimator.fragment.FirstAnimFragment;
import com.xzh.objectanimator.fragment.SecondAnimFragment;
import com.xzh.objectanimator.fragment.ThirdAnimFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragmentList;
	
	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		if(null == fragmentList){
			fragmentList = new ArrayList<Fragment>();
			fragmentList.add(new FirstAnimFragment());
			fragmentList.add(new SecondAnimFragment());
			fragmentList.add(new ThirdAnimFragment());
		}
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

}

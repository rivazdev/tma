package rivazdev.com.tma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import rivazdev.com.tma.ui.OpenTasksFragment;
import rivazdev.com.tma.ui.StartFragment;


public class TabsPagerAdapter extends FragmentStatePagerAdapter {
	public static int NUM_FRAGS = 3;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch(index)
		{
		case 0:
			
			return StartFragment.newInstance();
		case 1:

			return OpenTasksFragment.newInstance();
		case 2:
			return StartFragment.newInstance();
					
		}
		return null;
	}

	@Override
	public int getCount() {
		return NUM_FRAGS;
	}

}

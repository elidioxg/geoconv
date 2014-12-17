package geoapp.coordinateconversor.implementation;

import geoapp.coordinateconversor.R;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class tabListener implements TabListener {
	
	Fragment fragment;
	
	public tabListener(Fragment fragment) {
		this.fragment = fragment;
	}
	
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}
	
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
	
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// nothing done here
	}

}

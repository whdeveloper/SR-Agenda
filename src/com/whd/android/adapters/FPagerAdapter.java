package com.whd.android.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.whd.sragenda.fragments.AgendaFragment;
import com.whd.sragenda.fragments.SettingsFragment;

public class FPagerAdapter extends FragmentPagerAdapter {
	String[] pages = { "Settings", "Aankomend" };

	public FPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public int getCount() {
		return pages.length;
	}

	public Fragment getItem(int page) {
		if (page > getCount()) return null;
		if (page == 0) {
			SettingsFragment sf = new SettingsFragment();
			Bundle localBundle = new Bundle();
			localBundle.putString("page_title", pages[page]);
			sf.setArguments(localBundle);
			return sf;
		}
		if (page == 1) {
			AgendaFragment af = new AgendaFragment();
			Bundle localBundle = new Bundle();
			localBundle.putString("page_title", pages[page]);
			af.setArguments(localBundle);
			return af;
		}
		return null;
	}

	public CharSequence getPageTitle(int pos) {
		return pages[pos];
	}
}

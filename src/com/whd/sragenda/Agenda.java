package com.whd.sragenda;

import com.whd.sragenda.adapters.FPagerAdapter;
import com.whd.sragenda.tasks.GetUpdate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerTabStrip;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class Agenda extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_agenda);
        
        ViewPager 		pager     = (ViewPager)     findViewById(R.id.pager);
        PagerTabStrip   pagertabs = (PagerTabStrip) findViewById(R.id.titles);
        FragmentManager fm        = getSupportFragmentManager();
        
        pager.setAdapter(new FPagerAdapter(fm));

        pagertabs.setDrawFullUnderline(true);
        SharedPreferences sp = getSharedPreferences( Constants.prefs, 0);
        pagertabs.setBackgroundColor    ( sp.getInt( Constants.children[1][0], 0xFF545454 ));
        pagertabs.setTextColor          ( sp.getInt( Constants.children[0][0], 0xBB4CBB17 ));
        pagertabs.setTabIndicatorColor  ( sp.getInt( Constants.children[0][1], 0xBB4CBB17 ));
        pager.setBackgroundColor		( sp.getInt( Constants.children[1][0], 0xFF535353 ));
        
        pager.setCurrentItem(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agenda, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.action_update:
    			new GetUpdate(this).execute();
    			break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
}

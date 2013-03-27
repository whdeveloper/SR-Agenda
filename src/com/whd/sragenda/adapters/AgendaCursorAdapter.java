package com.whd.sragenda.adapters;

import com.whd.sragenda.Constants;
import com.whd.sragenda.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AgendaCursorAdapter extends SimpleCursorAdapter {
	
	private Cursor mCursor;
	private Context mContext;
	private SharedPreferences sp;
	
	@SuppressWarnings("deprecation")
	public AgendaCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to) {
		super(context, layout, cursor, from, to);
		
		mCursor = cursor;
		mContext = context;
		sp = mContext.getSharedPreferences( Constants.prefs, 0);
	}

	public View getView(int pos, View v, ViewGroup parent) {
	    mCursor.moveToPosition(pos);
	    String locatie = mCursor.getString(mCursor.getColumnIndex("locatie"));
	    String datum   = mCursor.getString(mCursor.getColumnIndex("datum"));
	    String tijd    = mCursor.getString(mCursor.getColumnIndex("tijd"));
	    String afstand = mCursor.getString(mCursor.getColumnIndex("afstand"));
	    
	    if (v == null){
	    	v = ((LayoutInflater) mContext.getSystemService("layout_inflater")).inflate(R.layout.listview_agenda_item, null);
	    	TextView loc = ((TextView) v.findViewById(R.id.locatie));
	    			loc.setText(locatie);
	    			loc.setTextColor( sp.getInt( Constants.children[1][1], 0xFF000000 ));
	    	TextView dat = ((TextView) v.findViewById(R.id.datum));
	    			dat.setText(datum);
	    			loc.setTextColor( sp.getInt( Constants.children[1][1], 0xFF000000 ));
	    	TextView tij = ((TextView) v.findViewById(R.id.tijd));
	    			tij.setText(tijd);
	    			loc.setTextColor( sp.getInt( Constants.children[1][1], 0xFF000000 ));
	    	TextView afs = ((TextView) v.findViewById(R.id.afstand));
	    			afs.setText(afstand);
	    			loc.setTextColor( sp.getInt( Constants.children[1][1], 0xFF000000 ));
	    	return v;
	    } else {
	    	return v;
	    }
	}
}
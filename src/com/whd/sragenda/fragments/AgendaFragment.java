package com.whd.sragenda.fragments;

import com.whd.sragenda.R;
import com.whd.sragenda.adapters.AgendaCursorAdapter;
import com.whd.sragenda.adapters.AgendaSQLAdapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

interface FragmentReloader {
	public void reload();
}

public class AgendaFragment extends Fragment implements FragmentReloader{
	
	private AgendaCursorAdapter a;
	private AgendaSQLAdapter mSQL;
	
	private Context context;
	private ListView lv;
	
	private int[]    to   = { R.id.locatie, R.id.datum, R.id.tijd, R.id.afstand };
	private String[] from = { "locatie"   , "datum"   , "tijd"   , "afstand"    };

	public void onCreate(Bundle sIS) {
	    super.onCreate(sIS);
	    
	    context = getActivity();
	    mSQL    = new AgendaSQLAdapter(context);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		mSQL.openToRead();
	    Cursor cursor = new CustomCursorLoader(context, mSQL.queueAll()).loadInBackground();
	    
	    a  = new AgendaCursorAdapter(context, R.layout.listview_agenda_item, cursor, from, to);
	    
	    lv = new ListView(this.context);
	    lv.setAdapter(a);
	    
	    mSQL.close();
	    return lv;
	}
	
	public class CustomCursorLoader extends CursorLoader {
		private Cursor  CCLcursor;
	    public CustomCursorLoader(Context context, Cursor cursor) {
			super(context);
			CCLcursor  = cursor;
		}

		private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

	    @Override
	    public Cursor loadInBackground() {
	        Cursor cursor = CCLcursor;

	        if (cursor != null) {
	            // Ensure the cursor window is filled
	            cursor.getCount();
	            cursor.registerContentObserver(mObserver);
	        }

	        return cursor;
	    }
	}

	@Override
	public void reload() {
		onCreate(null);
	};
}

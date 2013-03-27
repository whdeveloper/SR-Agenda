package com.whd.sragenda.fragments;

import com.whd.sragenda.Constants;
import com.whd.sragenda.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class SettingsFragment extends Fragment {
	
	private Context context;
	private String[] settings = Constants.settings;
	private String[][] children = Constants.children;
	private SharedPreferences sp;
	private SharedPreferences.Editor e;
	
	public void onCreate(Bundle sIS) {
	    super.onCreate(sIS);
	    
	    context = getActivity();
	    sp = context.getSharedPreferences(Constants.prefs, 0);
	    e = sp.edit();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle bundle) {
		View v = inflater.inflate(R.layout.settings, null);
		
		final ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.ExpandableListView1);
		
		ExpandableListAdapter elva = new ExpandableListAdapter() {
			
	        public void unregisterDataSetObserver(DataSetObserver observer) {}
	        public void registerDataSetObserver(DataSetObserver observer) {}
	        public void onGroupExpanded(int groupPosition) {}
	        public void onGroupCollapsed(int groupPosition) {}

	        public boolean isChildSelectable  (int groupPosition, int childPosition) { return true;                                   }
	        public boolean hasStableIds       ()                                     { return true;                                   }
	        public boolean areAllItemsEnabled ()                                     { return false;                                  }
	        public boolean isEmpty            ()                                     { return false;                                  }
	        public int     getGroupCount      ()                                     { return settings.length;                        }
	        public int     getChildrenCount   (int groupPosition                   ) { return children[groupPosition].length;         }
	        public long    getGroupId         (int groupPosition                   ) { return groupPosition;                          }
	        public long    getCombinedGroupId (long groupId                        ) { return 0;                                      }
	        public long    getCombinedChildId (long groupId,      long childId     ) { return 0;                                      }
	        public long    getChildId         (int groupPosition, int childPosition) { return childPosition;                          }
	        public Object  getGroup           (int groupPosition                   ) { return settings[groupPosition];                }
	        public Object  getChild           (int groupPosition, int childPosition) { return children[groupPosition][childPosition]; }

	        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	        	TextView textView = getGenericView();
		        textView.setText(getChild(groupPosition, childPosition).toString());
		        return textView;
		    }
	        
	        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	            TextView textView = getGenericView();
	            textView.setText(getGroup(groupPosition).toString());
	            return textView;
	        }
	        
	        public TextView getGenericView() {
	            // Layout parameters for the ExpandableListView
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, 64);

	            TextView textView = new TextView(context);
	            textView.setLayoutParams(lp);
	            // Center the text vertically
	            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
	            // Set the text starting position
	            textView.setPadding(60, 0, 0, 0);

	            textView.setTextColor( sp.getInt( Constants.children[1][1], 0xFF000000 ));
	            return textView;
	        }
		};
		
		
	    elv.setAdapter( elva );
		
	    elv.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, final int groupPos, final int childPos, long id) {
				if ((groupPos == 0) || (groupPos == 1)){
					AlertDialog.Builder alert = new AlertDialog.Builder(context);

					alert.setTitle(children[groupPos][childPos]);
					alert.setMessage("Kies een kleur (Hexadecimaal)");

					final EditText input = new EditText(context);
					input.setHint("#112233, #44556677, RED");
					alert.setView(input);

					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							String value = input.getText().toString();
							try {
								e.putInt(children[groupPos][childPos], Color.parseColor(value));
								e.commit();
							} catch (IllegalArgumentException e){
								Toast.makeText(context, "Wrong color", Toast.LENGTH_SHORT).show();
							}
						}
					});

					alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {}
					});

					alert.show();
					return true;
				} else {
					return false;
				}
			}
	    });
	    
	    return v;
	}
}

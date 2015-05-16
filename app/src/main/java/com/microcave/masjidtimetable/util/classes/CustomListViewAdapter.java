package com.microcave.masjidtimetable.util.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microcave.masjidtimetable.R;


public class CustomListViewAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<CustomListView> CustomListViewItems;
	
	public CustomListViewAdapter(Context context, ArrayList<CustomListView> CustomListViewItems){
		this.context = context;
		this.CustomListViewItems = CustomListViewItems;
	}

	@Override
	public int getCount() {
		return CustomListViewItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return CustomListViewItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_list_view, null);
        }
         
        TextView MasjidName = (TextView) convertView.findViewById(R.id.masjid_name);
        TextView LocalArea = (TextView) convertView.findViewById(R.id.local_area);
        TextView LargerAreaCountry = (TextView) convertView.findViewById(R.id.larger_area_country);
        ImageView LocalAreaIcon=(ImageView) convertView.findViewById(R.id.local_area_icon);
        ImageView LargerAreaIcon=(ImageView) convertView.findViewById(R.id.larger_area_icon);
        ImageView ArrowIcon=(ImageView) convertView.findViewById(R.id.arrow);


		MasjidName.setText(CustomListViewItems.get(position).getMasjidName());
		LocalArea.setText(CustomListViewItems.get(position).getloc1());
		LargerAreaCountry.setText(CustomListViewItems.get(position).getloc2());
		LocalAreaIcon.setImageResource(CustomListViewItems.get(position).getIcon());
		LargerAreaIcon.setImageResource(CustomListViewItems.get(position).getIcon1());
		ArrowIcon.setImageResource(CustomListViewItems.get(position).getIcon2());
                
        return convertView;
	}

}

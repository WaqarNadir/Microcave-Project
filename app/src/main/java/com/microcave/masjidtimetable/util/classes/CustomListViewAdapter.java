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
	private ArrayList<CustomListView> navDrawerItems;
	
	public CustomListViewAdapter(Context context, ArrayList<CustomListView> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
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
         
        TextView txtTitle = (TextView) convertView.findViewById(R.id.textView1);
        TextView txtTitle1 = (TextView) convertView.findViewById(R.id.textView2);
        TextView txtTitle2 = (TextView) convertView.findViewById(R.id.textView3);
        ImageView img=(ImageView) convertView.findViewById(R.id.add1);
        ImageView img1=(ImageView) convertView.findViewById(R.id.add2);
        ImageView img2=(ImageView) convertView.findViewById(R.id.arrow);
         
            
        txtTitle.setText(navDrawerItems.get(position).getMasjidName());
        txtTitle1.setText(navDrawerItems.get(position).getloc1());
        txtTitle2.setText(navDrawerItems.get(position).getloc2());
        img.setImageResource(navDrawerItems.get(position).getIcon());
        img1.setImageResource(navDrawerItems.get(position).getIcon1());
        img2.setImageResource(navDrawerItems.get(position).getIcon2());
                
        return convertView;
	}

}

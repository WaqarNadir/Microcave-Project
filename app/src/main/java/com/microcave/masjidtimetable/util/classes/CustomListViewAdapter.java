package com.microcave.masjidtimetable.util.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microcave.masjidtimetable.R;


public class CustomListViewAdapter extends BaseAdapter {
	
	private Context context;
	private int Primary= -1;
	private int Secondary = -1;
	private int Ternary = -1;
	private int Quatary = -1;

	private int Primarypos= -333	;
	private int Secondarypos = -333;
	private int Ternarypos = -333;
	private int Quatarypos = -333;

	int color=Color.WHITE;
	public   boolean already_clicked=false;
	public   boolean S_already_clicked=false;
	public   boolean T_already_clicked=false;
	public   boolean Q_already_clicked=false;
	int type;

	SharedPreferences pref ;

	public void setSelectedPosition(int pos){
		Primary = pos;
		notifyDataSetChanged();
	}

	private ArrayList<CustomListView> CustomListViewItems;
	
	public CustomListViewAdapter(Context context, ArrayList<CustomListView> CustomListViewItems){
		this.context = context;
		this.CustomListViewItems = CustomListViewItems;
		pref= context.getSharedPreferences("ListValues",Context.MODE_PRIVATE);
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
		//Primarypos=pref.getInt("Primary_pos",-909);


		if (Primary == position  ) {
		//	Log.e("In primary",""+Primarypos);
			if(!already_clicked) {
				//convertView.setBackgroundColor(Color.GREEN);
				CustomListViewItems.get(position).setColor(Color.GREEN);

				pref.edit().putInt("Primary_pos",position).apply();
				pref.edit().commit();

				Primarypos=position;
			}if(already_clicked)
			{
				//convertView.setBackgroundColor(Color.WHITE);
				CustomListViewItems.get(position).setColor(Color.WHITE);
				Primarypos=-909;
			}
		}
		if (Secondary == position ) {
		//	Log.e("In Secondary","");
			if(!S_already_clicked) {
				//convertView.setBackgroundColor(Color.GRAY);
				CustomListViewItems.get(position).setColor(Color.GRAY);
				Secondarypos=position;
			}if(S_already_clicked)
			{
				//convertView.setBackgroundColor(Color.WHITE);
				CustomListViewItems.get(position).setColor(Color.WHITE);
				Secondarypos=-333;
			}
		}
		if (Ternary == position ) {
		//	Log.e("In ternary","");
			if(!T_already_clicked) {
				//convertView.setBackgroundColor(Color.YELLOW);
				CustomListViewItems.get(position).setColor(Color.YELLOW);
				Ternarypos=position;
			}if(T_already_clicked)
			{
				convertView.setBackgroundColor(Color.WHITE);
				CustomListViewItems.get(position).setColor(Color.WHITE);
				Ternarypos=-333;
			}
		}
		if (Quatary == position ) {
		//	Log.e("In Quantry", "");
			if(!Q_already_clicked) {
				//convertView.setBackgroundColor(Color.BLUE);
				CustomListViewItems.get(position).setColor(Color.BLUE);
				Quatarypos=position;
			}if(Q_already_clicked)
			{
				//convertView.setBackgroundColor(Color.WHITE);
				CustomListViewItems.get(position).setColor(Color.WHITE);
				Quatarypos=-333;
			}
		}
		if(Quatary != position  && Ternary != position && Secondary != position && Primary!= position)
		{
			convertView.setBackgroundColor(Color.WHITE);
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
		convertView.setBackgroundColor(CustomListViewItems.get(position).getColor());
        return convertView;
	}

	public void setSecondary(int secondary) {
		Secondary = secondary;
		notifyDataSetChanged();
	}

	public void setTernary(int ternary) {
		Ternary = ternary;
		notifyDataSetChanged();
	}


	public void setQuatary(int quatary) {
		Quatary = quatary;
		notifyDataSetChanged();
	}
}

package com.microcave.masjidtimetable.util.classes;

import android.graphics.Color;

public class CustomListView  implements Comparable<CustomListView>{
	
	private String m_name;
	private String loc1;
	private String loc2;
	private int icon;
	private int icon1;
	private int icon2;
	private int color= Color.WHITE;
	public int counter=0;
		
	public CustomListView(){}

	public CustomListView(String title, String l, String l1,int icon,int icon1,int icon2){
	counter++;
		this.m_name = title;
		this.loc1=l;
		this.loc2=l1;
		this.icon = icon;
		this.icon1 = icon1;
		this.icon2 = icon2;
	}

	public String getMasjidName(){
		return this.m_name;
	}
	
	public String getloc1(){
		return this.loc1;
	}
	
	public String getloc2(){
		return this.loc2;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public int getIcon1(){
		return this.icon1;
	}
	
	public int getIcon2(){
		return this.icon2;
	}
	
	public void setMasjidName(String title){
		this.m_name = title;
	}
	
	public void setloc1(String loc){
		this.loc1 = loc;
	}
	
	public void setloc2(String loc1){
		this.loc2 = loc1;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setIcon1(int icon1){
		this.icon1 = icon1;
	}
	
	public void setIcon2(int icon2){
		this.icon2 = icon2;
	}

	@Override
	public int compareTo(CustomListView another) {
	//	int _count=(int)another.counter;
 int num=this.getMasjidName().compareToIgnoreCase((String )another.getMasjidName());
		if(num!=0)
		{
			return num;
		}
		return 0;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}

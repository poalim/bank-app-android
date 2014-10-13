package com.bnhp.api.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bnhp.api.R;
import com.bnhp.api.entities.CreditCardTransaction;

public class CategoryAdapter extends BaseAdapter
{
	private List<String> list;
	private static LayoutInflater inflater=null;
	private Activity activity;
	
	public CategoryAdapter(List<String> list, Activity a)
	{
		activity = a;
		this.list = list;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View vi = convertView;
		if(convertView==null)
            vi = inflater.inflate(R.layout.category_row, null);
		
		TextView categoryName = (TextView)vi.findViewById(R.id.categoryName);
		
		categoryName.setText(list.get(position));
		
		return vi;
	}
	

}

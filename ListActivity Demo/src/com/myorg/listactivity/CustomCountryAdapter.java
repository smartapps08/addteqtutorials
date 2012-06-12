package com.myorg.listactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCountryAdapter extends ArrayAdapter<CountryProfile> {
	Activity mContext;
	ArrayList<CountryProfile> listItems;

	public CustomCountryAdapter(Activity context,
			ArrayList<CountryProfile> countriesList) {
		super(context, R.layout.items, countriesList);
		mContext = context;
		listItems = countriesList;

	}

	static class ViewHolder {
		public TextView nameView;
		public TextView capitalView;
		public ImageView flagView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		View v = convertView;
		if (v == null) {
			LayoutInflater inflator = mContext.getLayoutInflater();
			v = inflator.inflate(R.layout.items, null, false);

			holder = new ViewHolder();

			holder.nameView = (TextView) v.findViewById(R.id.txtCountryName);
			holder.capitalView = (TextView) v.findViewById(R.id.txtCapital);
			holder.flagView = (ImageView) v.findViewById(R.id.imageView1);

			holder.nameView.setText(listItems.get(position).getCountryName());
			holder.capitalView
					.setText(listItems.get(position).getCapitalName());
			holder.flagView.setImageResource(listItems.get(position)
					.getFlagImageRes());

		} else {
			v = convertView;
		}

		return v;
	}

}

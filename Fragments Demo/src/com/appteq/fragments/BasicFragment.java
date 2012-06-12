package com.appteq.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BasicFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_basic, container, false);
		Button button = (Button) view.findViewById(R.id.fragment_button);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				

			}
		});

		return view;
	}
}

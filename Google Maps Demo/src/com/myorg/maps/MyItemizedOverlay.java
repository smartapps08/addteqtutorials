package com.myorg.maps;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	Context mContext;
	ArrayList<OverlayItem> mOverlays=new ArrayList<OverlayItem>();
	

	public MyItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext=context;
	}
	
	public MyItemizedOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem item)
	{
		mOverlays.add(item);
		populate();
	}
	
	
	@Override
	protected boolean onTap(int index) {
		OverlayItem item=mOverlays.get(index);
		
		AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

}

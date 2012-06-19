package com.myorg.maps;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GoogleMapsDemoActivity extends MapActivity {
	MapView mapview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapview=(MapView)findViewById(R.id.mapview);
        mapview.setBuiltInZoomControls(true);
        
        // gathered all overlays already there in the mapview
        List<Overlay> mapoverlays=mapview.getOverlays();
        
        //creating the marker drawable
        Drawable marker=getResources().getDrawable(R.drawable.ic_launcher);
        
        MyItemizedOverlay myItemizedOverlay=new MyItemizedOverlay(marker, this);
        
        //create geopoint
        double lat=23.567678;
        double lon=90.568787;
        int latitude= (int) (lat*1000000);
        int longitude=(int) (lon*1000000);
        GeoPoint gp=new GeoPoint(latitude, longitude);
        GeoPoint gp1=new GeoPoint((int)(24.456747*1000000), (int)(91.456747*1000000));
        
        mapview.getController().animateTo(gp);
        mapview.getController().setZoom(12);
        
        
        //create an OverlayItem
        OverlayItem item=new OverlayItem(gp, "My Position", "I live here");
        myItemizedOverlay.addOverlay(item);
        
        item=new OverlayItem(gp1, "Another", "I dont live here");
        myItemizedOverlay.addOverlay(item);
        
        mapoverlays.add(myItemizedOverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
package com.example.mapdemo;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MainActivity extends MapActivity {
    MapView mapView;

    MapController mMapController;
    GeoPoint p1 = new GeoPoint(37423021,-122083739);
    GeoPoint p2 = new GeoPoint(37523021,-122183739);
    GeoPoint p3 =new GeoPoint(37623021, -122283739);
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);

        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.add(mapOverlay);

        mMapController = mapView.getController();
        mMapController.setZoom(12);
        mMapController.animateTo(p1);
    }

    private class MapOverlay extends com.google.android.maps.Overlay
    {

        @Override
        public boolean draw(Canvas canvas, MapView mapView,
        boolean shadow, long when)
        {
        	
        	//System.out.println("in my fuction");
            super.draw(canvas, mapView, shadow);

            Bitmap bmp = BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_launcher);
            geoPointToDrawRoute(canvas, p1,p2, bmp);

            return true;
        }
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public void geoPointToDrawRoute(Canvas canvas,GeoPoint point1,GeoPoint point2,Bitmap imageName){
		Paint paint = new Paint();

        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeJoin(Join.ROUND);
        paint.setStrokeWidth(3.0f);
        paint.setAntiAlias(true);

        //---translate the GeoPoint to screen pixels---
        Point screenPts1 = new Point();
        mapView.getProjection().toPixels(p1, screenPts1);
        //---translate the GeoPoint to screen pixels---
        Point screenPts2 = new Point();
        mapView.getProjection().toPixels(p2, screenPts2);
        
      //---add the first marker---
        canvas.drawBitmap(imageName, screenPts1.x - imageName.getWidth()/2,
                               screenPts1.y - imageName.getHeight()/2, null);
      //---add the second marker---
        canvas.drawBitmap(imageName, screenPts2.x - imageName.getWidth()/2,
                               screenPts2.y - imageName.getHeight()/2, null);
        final Path myownpath1=new Path();
        //draw mid points between 2 points---
      
        final float xmid1=(screenPts1.x+screenPts2.x);
        final float ymid1=(screenPts1.y+screenPts2.y);
        //path1
        myownpath1.moveTo(screenPts1.x, screenPts1.y);
        myownpath1.quadTo(xmid1, ymid1, screenPts2.x,screenPts2.y);
      
        //---draws a line connecting the 2 points---
        canvas.drawPath(myownpath1, paint);
      
	}
}
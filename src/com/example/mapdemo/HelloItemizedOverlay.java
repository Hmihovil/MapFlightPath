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

public class HelloItemizedOverlay extends MapActivity {
    MapView mapView;

    MapController mc;
    GeoPoint p1 = new GeoPoint(37423021,-122083739);
    GeoPoint p2 = new GeoPoint(37523021,-122183739);

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

        mc = mapView.getController();
        mc.setZoom(12);
        mc.animateTo(p1);
    }

    private class MapOverlay extends com.google.android.maps.Overlay
    {

        @Override
        public boolean draw(Canvas canvas, MapView mapView,
        boolean shadow, long when)
        {
            super.draw(canvas, mapView, shadow);

            Bitmap bmp = BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_launcher);

            //---translate the GeoPoint to screen pixels---
            Point screenPts1 = new Point();
            mapView.getProjection().toPixels(p1, screenPts1);

            //---translate the GeoPoint to screen pixels---
            Point screenPts2 = new Point();
            mapView.getProjection().toPixels(p2, screenPts2);

            //---add the first marker---
            canvas.drawBitmap(bmp, screenPts1.x - bmp.getWidth()/2,
                                   screenPts1.y - bmp.getHeight()/2, null);

            //---add the second marker---
            canvas.drawBitmap(bmp, screenPts2.x - bmp.getWidth()/2,
                                   screenPts2.y - bmp.getHeight()/2, null);

            Paint paint = new Paint();
//            paint.setStyle(Style.STROKE);
//            paint.setColor(0xFFffffff);
//            paint.setAntiAlias(true);
//            paint.setStrokeWidth(5);
            
            paint.setStyle(Style.STROKE);
            paint.setStrokeCap(Cap.ROUND);
            paint.setStrokeJoin(Join.ROUND);
            paint.setStrokeWidth(3.0f);
            paint.setAntiAlias(true);
/*
 Paint paint = new Paint() {
    {
        setStyle(Paint.Style.STROKE);
        setStrokeCap(Paint.Cap.ROUND);
        setStrokeWidth(3.0f);
        setAntiAlias(true);
    }
};
final Path path = new Path();
path.moveTo(x1, y1);

final float x2 = (x3 + x1) / 2;
final float y2 = (y3 + y1) / 2;
        path.quadTo(x2, y2, x3, y3);
canvas.drawPath(path, paint);
*/
            
            final Path myownpath=new Path();
            myownpath.moveTo(screenPts1.x, screenPts1.y);
            final float xmid=(screenPts1.x+screenPts2.x);
            final float ymid=(screenPts1.y+screenPts2.y);
            myownpath.quadTo(xmid, ymid, screenPts2.x,screenPts2.y);
            
            //---draws a line connecting the 2 points---
            canvas.drawPath(myownpath, paint);

            return true;
        }
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
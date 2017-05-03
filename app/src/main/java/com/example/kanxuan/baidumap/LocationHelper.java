package com.example.kanxuan.baidumap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by kanxuan on 2017/5/3.
 */



public class LocationHelper implements LocationListener {

    private static final String TAG = "LocationHelper";

    private LocationManager mLocationManager;

    private double mLongitude;

    private double mLatitude;

    private float mBearing;

    private float mAccuracy;

    public double getLongitude(){
        return  mLongitude;
    }

    public double getLatitude(){
        return  mLatitude;
    }

    public float getBearing(){
        return mBearing;
    }

    public float getAccuracy() {
        return mAccuracy;
    }

    public LocationHelper(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        mBearing = location.getBearing();
        mAccuracy = location.getAccuracy();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

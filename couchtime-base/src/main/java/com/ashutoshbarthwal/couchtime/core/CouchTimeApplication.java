package com.ashutoshbarthwal.couchtime.core;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Ashutosh on 03-03-2017.
 */

public class CouchTimeApplication extends Application {

    public static final String TAG = CouchTimeApplication.class.getSimpleName();

    private static CouchTimeApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        _instance = this;
    }

    public static CouchTimeApplication getInstance(){
        return _instance;
    }
}

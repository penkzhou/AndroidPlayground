package com.penkzhou.projects.android.playground;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class PlaygroundApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        PlaygroundApplication.context = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getAppContext() {
        return PlaygroundApplication.context;
    }
}

package com.robusta;

import android.app.Application;

/**
 * Created by Eslam Hussein on 10/27/17.
 */

public class App extends Application {


    private static App instance;
    public static App get() {
        if (instance == null)
            throw new IllegalStateException("Something went horribly wrong!!, no application attached!");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}

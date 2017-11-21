package com.ofp.sagesample;

/**
 * Created by johncederholm on 11/20/17.
 */

import com.parse.Parse;
import android.app.Application;
import android.util.Log;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }
}
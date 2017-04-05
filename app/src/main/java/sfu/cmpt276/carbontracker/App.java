package sfu.cmpt276.carbontracker;

import android.app.Application;
import android.content.Context;

/**
 * used to get Context from a non activity
 */

public class App extends Application {
    private static Context myContext;

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
    }

    public static Context getContext(){
        return myContext;
    }
}

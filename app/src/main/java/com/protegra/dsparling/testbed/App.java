package com.protegra.dsparling.testbed;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.protegra.dsparling.testbed.time.TimeModule;

import dagger.ObjectGraph;

public class App extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(modules());
    }

    private static Object[] modules() {
        return new Object[] {new TimeModule() };
    }

    public static void inject(Context context, Object any) {
        ((App) context.getApplicationContext()).inject(any);
    }

    public static void inject(Activity activity) {
        inject(activity, activity);
    }

    private void inject(Object any) {
        objectGraph.inject(any);
    }
}

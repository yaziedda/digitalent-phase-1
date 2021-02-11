package io.github.yaziedda.submission2;


import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by yaziedda on 2019-11-12.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }

}

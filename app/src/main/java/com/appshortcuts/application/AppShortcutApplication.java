package com.appshortcuts.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by daamjad on 3/29/2017.
 */

public class AppShortcutApplication extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("GeoFence")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}

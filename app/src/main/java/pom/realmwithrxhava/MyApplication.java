package pom.realmwithrxhava;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Roy.Leung on 26/1/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

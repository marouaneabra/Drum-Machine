package oberlin.drummachinehackv2;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lukas on 9/24/17.
 */

public class MainApplication extends Application {
    private Realm realmDrumSequence;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realmDrumSequence = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmDrumSequence.close();
    }

    public Realm getRealmTodo() {
        return realmDrumSequence;
    }
}

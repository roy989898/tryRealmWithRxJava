package pom.realmwithrxhava.presenter;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import pom.realmwithrxhava.Models.User;
import pom.realmwithrxhava.view.MainView;

/**
 * Created by Roy.Leung on 26/1/17.
 */

public class MainPresenterIpm implements MainPresenter {

    private final Realm realm;
    private MainView view;
    private RealmResults<User> result;


    public static MainPresenter bind(MainView view) {
        return new MainPresenterIpm(view);
    }

    public MainPresenterIpm(MainView view) {
        this.view = view;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void createOrUpdateUser(final String name, final String age) {
        Realm.Transaction transaction = new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {

                User user = new User(name, age);
                bgrealm.copyToRealmOrUpdate(user);

            }
        };

        realm.executeTransactionAsync(transaction, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("Realm", "success");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("Realm", "error");
                error.printStackTrace();


            }
        });

    }

    @Override
    public void queryAllUser() {
        result = realm.where(User.class).findAll();
        view.showAllTheUserinUI(result);

        result.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
            @Override
            public void onChange(RealmResults<User> element) {
                view.showAllTheUserinUI(element);
            }
        });
    }

    @Override
    public void deleteUser(final int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = result.get(position);
                user.deleteFromRealm();
            }
        });

    }


    @Override
    public void unbind() {
        result.removeChangeListeners();
        realm.close();
        view = null;

    }
}

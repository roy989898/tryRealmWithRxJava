package pom.realmwithrxhava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import pom.realmwithrxhava.Adapter.UserAdapter;
import pom.realmwithrxhava.Models.User;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_old)
    EditText edOld;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_get_all)
    Button btGetAll;
    @BindView(R.id.lv_user)
    ListView lvUser;
    private Realm realm;
    private RealmResults<User> result;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        adapter = new UserAdapter(this, null);
        lvUser.setAdapter(adapter);
        lvUser.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @OnClick({R.id.bt_save, R.id.bt_get_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                final String name = edName.getText().toString();
                final String old = edOld.getText().toString();

                Realm.Transaction transaction = new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgrealm) {

                        User user = new User(name, old);
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
                break;
            case R.id.bt_get_all:
                getAllUser();

                break;
        }
    }

    private void getAllUser() {
        result = realm.where(User.class).findAll();
        useResultToSetTheUi(result);

        result.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
            @Override
            public void onChange(RealmResults<User> element) {
                useResultToSetTheUi(element);
            }
        });


    }

    private void useResultToSetTheUi(RealmResults<User> result) {
        if (result.size() >= 0) {
            List<User> userList = result.subList(0, result.size());
            adapter.setUserList(userList);
        }


    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = (User) adapterView.getItemAtPosition(i);
                user.deleteFromRealm();
            }
        });


    }
}

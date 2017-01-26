package pom.realmwithrxhava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import pom.realmwithrxhava.Models.User;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_old)
    EditText edOld;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_latest)
    Button btLatest;
    @BindView(R.id.tv_latest)
    TextView tvLatest;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @OnClick({R.id.bt_save, R.id.bt_latest})
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
            case R.id.bt_latest:
                User user = getLatestUser();
                tvLatest.setText(user.getName() + " " + user.getAge());


                break;
        }
    }

    private User getLatestUser() {
        RealmResults<User> result = realm.where(User.class).findAll();
        return result.get(result.size() - 1);

    }
}

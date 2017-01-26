package pom.realmwithrxhava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import pom.realmwithrxhava.Models.User;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_old)
    EditText edOld;
    @BindView(R.id.bt_save)
    Button btSave;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.bt_save)
    public void onClick() {
        final String name = edName.getText().toString();
        final String old = edOld.getText().toString();

        Realm.Transaction transaction = new Realm.Transaction() {
            @Override
            public void execute(Realm bgrealm) {
                User user = bgrealm.createObject(User.class);
                user.setName(name);
                user.setAge(old);
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
}

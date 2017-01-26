package pom.realmwithrxhava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.realmwithrxhava.Adapter.UserAdapter;
import pom.realmwithrxhava.Models.User;
import pom.realmwithrxhava.presenter.MainPresenter;
import pom.realmwithrxhava.presenter.MainPresenterIpm;
import pom.realmwithrxhava.view.MainView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, MainView {

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
    private UserAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        adapter = new UserAdapter(this, null);
        lvUser.setAdapter(adapter);
        lvUser.setOnItemClickListener(this);
        presenter = MainPresenterIpm.bind(this);
    }


    @Override
    protected void onStop() {
        presenter.unbind();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.bt_save, R.id.bt_get_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                String name = edName.getText().toString();
                String old = edOld.getText().toString();
                presenter.createOrUpdateUser(name, old);

                break;
            case R.id.bt_get_all:
                presenter.queryAllUser();

                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.deleteUser(i);


    }

    @Override
    public void showAllTheUserinUI(List<User> userList) {
        adapter.setUserList(userList);
    }
}
